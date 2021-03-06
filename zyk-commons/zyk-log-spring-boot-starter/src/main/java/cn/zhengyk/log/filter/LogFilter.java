package cn.zhengyk.log.filter;

import cn.zhengyk.core.constants.CommonConstants;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author yakai.zheng
 * 向 MDC 里添加自定义一些信息，便于打印日志
 */

@Slf4j
@Component
@ConditionalOnBean(Filter.class)
public class LogFilter extends OncePerRequestFilter {
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String userId = request.getHeader(CommonConstants.USER_ID_HEADER);
        if (!StringUtils.isEmpty(userId)) {
            MDC.put(CommonConstants.USER_ID, userId);
        }
        filterChain.doFilter(request, response);
        MDC.clear();
    }
}
