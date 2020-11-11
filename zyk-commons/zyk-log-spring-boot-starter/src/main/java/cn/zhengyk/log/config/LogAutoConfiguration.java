package cn.zhengyk.log.config;

import cn.zhengyk.log.filter.LogFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author yakai.zheng
 */
@Configuration
@ComponentScan("cn.zhengyk.log")
@ConditionalOnBean(LogFilter.class)
public class LogAutoConfiguration {

    @Autowired
    private LogFilter logFilter;

    @Bean
    public FilterRegistrationBean<LogFilter> myOncePerRequestFilterRegistration() {
        FilterRegistrationBean<LogFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(logFilter);
        // 拦截路径
        registration.addUrlPatterns("/*");
        // 拦截器名称
        registration.setName("logFilter");
        registration.setOrder(1);
        return registration;
    }

}
