package cn.zhengyk.exception.config;

import cn.zhengyk.exception.properties.ExceptionEmailProperties;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/26 16:45
 */
@EnableConfigurationProperties(ExceptionEmailProperties.class)
@ConditionalOnProperty(prefix = "exception-email", name = "enable", havingValue = "true")
public class GlobalExceptionAutoConfig {
}
