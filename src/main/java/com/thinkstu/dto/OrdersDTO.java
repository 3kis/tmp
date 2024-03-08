package com.thinkstu.dto;

import com.thinkstu.entity.OrderDetail;
import com.thinkstu.entity.Orders;
import lombok.Data;

import java.util.List;

/**
 * @author : Asher
 * @since : 2023-04/26, 3:15 PM, 周四
 **/
@Data
public class OrdersDTO extends Orders {
    List<OrderDetail> orderDetails;
    String userName;
}
