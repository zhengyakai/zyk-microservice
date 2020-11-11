package cn.zhengyk.log.filter;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yakai.zheng
 * 向 MDC 里添加自定义一些信息，便于打印日志
 */

@Order(1)
@Slf4j
@Component
public class LogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 这里可以设置当前登录用户的相关信息，信息可以在网关里 set 到 session 或者 请求头里
        MDC.put("userId", "U1234");
        filterChain.doFilter(request, response);
    }
}
