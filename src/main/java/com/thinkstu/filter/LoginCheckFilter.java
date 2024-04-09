package com.thinkstu.filter;

import com.alibaba.fastjson2.JSON;
import com.thinkstu.common.BaseContext;
import com.thinkstu.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @author : Asher
 * @since : 2024-04/12, 2:28 PM, 周四
 **/
@Slf4j
@Component
@WebFilter(value = "loginCheckFilter", urlPatterns = "/**")
public class LoginCheckFilter implements Filter {
    // Spring路径比较工具类，支持通配符写法
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 强转为 Http，获得更多方法
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        HttpSession session = req.getSession();

        // 获取本次请求的地址
        String requestURI = req.getRequestURI();
        // 排除判断路径
        String[] excludeUrls = new String[]{
                // 登录相关
                "/employee/login",
                "/employee/logout",
                // 前端其他
                "/backend/**",
                "/front/**",
                "/common/**",
                "/user/sendMsg",
                "/user/login",
                "/user/loginout",
                // Swagger插件过滤
                "/doc.html",
                "/webjars/*/**",
                "/swagger-resources",
                "/v2/api-docs"
        };
        for (String excludeUrl : excludeUrls) {
            if (PATH_MATCHER.match(excludeUrl, requestURI)) {
                chain.doFilter(req, resp);
                return;
            }
        }
        // 判断后台是否登录
        Long employeeId = (Long) session.getAttribute("employee");

        if (employeeId != null) {
            log.info("用户已登录，employeeId============》{}", employeeId);
            BaseContext.setCurrentId(employeeId);
            chain.doFilter(req, resp);
            return;
        }

        // 判断用户是否登录
        Long userId = (Long) session.getAttribute("user");

        if (userId != null) {
            log.info("用户已登录，userId============》{}", userId);
            BaseContext.setCurrentId(userId);
            chain.doFilter(req, resp);
            return;
        }

        // 未登录，控制台打印 + 返回错误提示
        log.info("拦截到请求============》{}", requestURI);
        resp.getWriter().write(JSON.toJSONString(R.error("请先登录！")));
    }
}
