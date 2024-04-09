package com.thinkstu.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.thinkstu.common.BaseContext;
import com.thinkstu.common.MyCheckException;
import com.thinkstu.dto.OrdersDTO;
import com.thinkstu.entity.*;
import com.thinkstu.mapper.OrdersMapper;
import com.thinkstu.service.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 订单表(Orders)表服务实现类
 *
 * @author asher
 * @since 2024-04-26 13:40:52
 */
@Service
@Slf4j
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements OrdersService {
    @Autowired
    OrderDetailService orderDetailService;
    @Autowired
    ShoppingCartService cartService;
    @Autowired
    UserService userService;
    @Autowired
    AddressBookService addressBookService;
    @Autowired
    OrderDetailService detailService;
    @Autowired
    EmployeeServiceImpl employeeService;

    @Override
    @Transactional
    public void order(Orders orders) {
        // 1. 获得当前用户 id
        Long userId = BaseContext.getCurrentId();
        // 2. 查询当前用户的：1）购物车数据、2）用户数据、3）地址数据
        LambdaQueryWrapper<ShoppingCart> cartWrapper = new LambdaQueryWrapper<>();
        cartWrapper.eq(ShoppingCart::getUserId, userId);
        List<ShoppingCart> carts = cartService.list(cartWrapper);
        if (carts == null || carts.size() == 0) {
            throw new MyCheckException("购物车不能为空！");
        }
        User user = userService.getById(userId);
        log.info(" user==>{}", user);
        AddressBook addressBook = addressBookService.getById(orders.getAddressBookId());
        if (addressBook == null) {
            throw new MyCheckException("地址信息有误！");
        }
        // MybatisPlus自动生成订单号
        long orderId = IdWorker.getId();
        AtomicInteger amount = new AtomicInteger(0);
        // 3. 向订单表插入数据，一条数据
        List<OrderDetail> orderDetails = new ArrayList<>();

        for (ShoppingCart cart : carts) {
            OrderDetail orderDetail = new OrderDetail();
            orderDetail.setOrderId(orderId);
            orderDetail.setNumber(cart.getNumber());
            orderDetail.setDishFlavor(cart.getDishFlavor());
            orderDetail.setDishId(cart.getDishId());
            orderDetail.setSetmealId(cart.getSetmealId());
            orderDetail.setName(cart.getName());
            orderDetail.setImage(cart.getImage());
            orderDetail.setAmount(cart.getAmount());
            amount.addAndGet(cart.getAmount().multiply(new BigDecimal(cart.getNumber())).intValue());
            orderDetails.add(orderDetail);
        }

        orders.setId(orderId);
        orders.setOrderTime(LocalDateTime.now());
        orders.setCheckoutTime(LocalDateTime.now());
        orders.setStatus(2);
        orders.setAmount(new BigDecimal(amount.get()));//总金额
        orders.setUserId(userId);
        orders.setNumber(String.valueOf(orderId));
        orders.setUserName(user.getName());
        orders.setConsignee(addressBook.getConsignee());
        orders.setPhone(addressBook.getPhone());
        orders.setAddress((addressBook.getProvinceName() == null ? "" : addressBook.getProvinceName())
                + (addressBook.getCityName() == null ? "" : addressBook.getCityName())
                + (addressBook.getDistrictName() == null ? "" : addressBook.getDistrictName())
                + (addressBook.getDetail() == null ? "" : addressBook.getDetail()));

        this.save(orders);

        // 4. 向订单明细表插入数据，多条数据
        orderDetailService.saveBatch(orderDetails);

        // 5. 清空购物车数据
        cartService.remove(cartWrapper);
    }

    /**
     * 在这个方法当中，存在前后端的情况
     */
    @Override
    public Page<OrdersDTO> myPages(Long page, Long pageSize, Long number, String beginTime, String endTime) {
        Page<Orders> ordersPage = new Page<>(page, pageSize);
        Page<OrdersDTO> dtoPage = new Page<>();
        // 自定义 LocalDateTime模式
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime bTime = null;
        LocalDateTime eTime = null;
        Long id = BaseContext.getCurrentId();
        Employee employee = employeeService.getById(id);
        if (StringUtils.isNotEmpty(beginTime) && StringUtils.isNotEmpty(endTime)) {
            bTime = LocalDateTime.parse(beginTime, dateTimeFormatter);
            eTime = LocalDateTime.parse(endTime, dateTimeFormatter);
        }
        LambdaQueryWrapper<Orders> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(number != null, Orders::getId, number)
                .eq(id != null && employee == null, Orders::getUserId, id)
                .between(bTime != null && eTime != null, Orders::getOrderTime, bTime, eTime)
                .orderByDesc(Orders::getOrderTime);
        page(ordersPage, wrapper);
        BeanUtils.copyProperties(ordersPage, dtoPage, "records");

        List<Orders> records = ordersPage.getRecords();
        List<OrdersDTO> dtoList = new ArrayList<>();
        OrdersDTO ordersDto;
        for (Orders order : records) {
            ordersDto = new OrdersDTO();
            // 查询用户名(本身无名字，这里无需设置)
/*            LambdaQueryWrapper<User> userWrapper = new LambdaQueryWrapper<>();
            userWrapper.eq(User::getId,order.getUserId());
            User user = userService.getOne(userWrapper);
            order.setUserName(user.getName());*/
            // 拷贝属性
            BeanUtils.copyProperties(order, ordersDto);
            Long orderId = order.getId();
            LambdaQueryWrapper<OrderDetail> detailWrapper = new LambdaQueryWrapper<>();
            detailWrapper.eq(OrderDetail::getOrderId, orderId);
            List<OrderDetail> orderDetailList = detailService.list(detailWrapper);
            if (orderDetailList != null) {
                ordersDto.setOrderDetails(orderDetailList);
            }
            dtoList.add(ordersDto);
        }
        dtoPage.setRecords(dtoList);
        return dtoPage;
    }

    @Override
    @Transactional
    public void again(Orders id) {
        Long userId = BaseContext.getCurrentId();
        LambdaQueryWrapper<OrderDetail> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(id.getId() != null, OrderDetail::getOrderId, id.getId());
        List<OrderDetail> orderDetails = detailService.list(wrapper);
        List<ShoppingCart> carts = new ArrayList<>();
        ShoppingCart cart;
        //
        for (OrderDetail orderDetail : orderDetails) {
            cart = new ShoppingCart();
            BeanUtils.copyProperties(orderDetail, cart);
            cart.setUserId(userId);
            carts.add(cart);
        }
        LambdaQueryWrapper<ShoppingCart> cartWrapper = new LambdaQueryWrapper<>();
        cartWrapper.eq(ShoppingCart::getUserId, userId);
        // 先删除再添加
        cartService.remove(cartWrapper);
        cartService.saveBatch(carts);
    }
}