package cn.zhengyk.log.config;

import cn.zhengyk.core.constants.CommonConstants;
import cn.zhengyk.log.filter.LogFilter;
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

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yakai.zheng
 */
@Configuration
@ComponentScan("cn.zhengyk.log")
@ConditionalOnBean(LogFilter.class)
public class LogAutoConfiguration {


    protected List<String> requestHeaders = new ArrayList<>();

    @PostConstruct
    public void initialize() {
        // 自己根据业务向里面添加 header
        requestHeaders.add(CommonConstants.USER_ID_HEADER);
    }

    @Autowired
    private LogFilter logFilter;

    @Bean
    public FilterRegistrationBean<LogFilter> logFilterRegistration() {
        FilterRegistrationBean<LogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(logFilter);
        // 拦截路径
        registration.addUrlPatterns("/*");
        // 拦截器名称
        registration.setName("logFilter");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }


    /**
     * 使用feign client访问别的微服务时，将上游传过来的 traceId 放入 header 传递给下一个服务
     */
    @Bean
    public RequestInterceptor logFeignInterceptor() {
        return template -> {
            //传递日志traceId
            String traceId = MDC.get(CommonConstants.USER_ID);
            if (!StringUtils.isEmpty(traceId)) {
                template.header(CommonConstants.USER_ID_HEADER, traceId);
            }
        };
    }

}
