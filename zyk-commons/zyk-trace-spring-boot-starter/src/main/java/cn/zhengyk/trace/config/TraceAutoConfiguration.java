package cn.zhengyk.trace.config;

import cn.zhengyk.core.constants.CommonConstants;
import cn.zhengyk.trace.filter.TraceFilter;
import feign.RequestInterceptor;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.util.StringUtils;

/**
 * @author yakai.zheng
 */
@Configuration
@ComponentScan("cn.zhengyk.trace")
@ConditionalOnBean(TraceFilter.class)
public class TraceAutoConfiguration {

    @Autowired
    private TraceFilter traceFilter;

    @Bean
    public FilterRegistrationBean<TraceFilter> traceFilterRegistration() {
        FilterRegistrationBean<TraceFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(traceFilter);
        // 拦截路径
        registration.addUrlPatterns("/*");
        // 拦截器名称
        registration.setName("traceFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }


    /**
     * 使用feign client访问别的微服务时，将上游传过来的 traceId 放入 header 传递给下一个服务
     */
    @Bean
    public RequestInterceptor traceFeignInterceptor() {
        return template -> {
            //传递日志traceId
            String traceId = MDC.get(CommonConstants.TRACE_ID);
            if (!StringUtils.isEmpty(traceId)) {
                template.header(CommonConstants.TRACE_ID_HEADER, traceId);
            }
        };
    }

}
