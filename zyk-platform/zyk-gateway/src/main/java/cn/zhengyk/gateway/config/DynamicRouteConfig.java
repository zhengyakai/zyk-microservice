package cn.zhengyk.gateway.config;

import com.alibaba.cloud.nacos.NacosConfigManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Configuration;

/**
 * 动态路由配置
 * @author 郑亚凯
 */
@Configuration
@ConditionalOnProperty(prefix = "zlt.gateway.dynamicRoute", name = "enabled", havingValue = "true")
public class DynamicRouteConfig {
    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private NacosConfigManager nacosConfigProperties;



}
