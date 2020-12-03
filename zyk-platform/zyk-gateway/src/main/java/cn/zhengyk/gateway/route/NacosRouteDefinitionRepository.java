package cn.zhengyk.gateway.route;

import com.alibaba.cloud.nacos.NacosConfigManager;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.concurrent.Executor;

/**
 * @author 郑亚凯
 */
@Slf4j
@Component
public class NacosRouteDefinitionRepository implements RouteDefinitionRepository {
    private static final String SPRING_CLOUD_GATEWAY_DATA_ID = "spring-cloud-gateway-routes";
    private static final String SPRING_CLOUD_GATEWAY_GROUP_ID = "spring-cloud-gateway";

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private NacosConfigManager nacosConfigManager;

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        try {
            // 这里 nacos 上的路由配置采用 json 形式
            String content = nacosConfigManager.getConfigService().getConfig(SPRING_CLOUD_GATEWAY_DATA_ID, SPRING_CLOUD_GATEWAY_GROUP_ID,5000);
            List<RouteDefinition> routeDefinitions = JSONObject.parseArray(content, RouteDefinition.class);
            return Flux.fromIterable(routeDefinitions);
        } catch (NacosException e) {
            log.error("getRouteDefinitions by nacos error", e);
        }
        return Flux.fromIterable(Lists.newArrayList());
    }

    /**
     * 添加Nacos监听
     */
    @PostConstruct
    private void addListener() {
        try {
            nacosConfigManager.getConfigService().addListener(SPRING_CLOUD_GATEWAY_DATA_ID, SPRING_CLOUD_GATEWAY_GROUP_ID, new Listener() {
                @Override
                public Executor getExecutor() {
                    return null;
                }

                @Override
                public void receiveConfigInfo(String configInfo) {
                    publisher.publishEvent(new RefreshRoutesEvent(this));
                }
            });
        } catch (NacosException e) {
            log.error("nacos-addListener-error", e);
        }
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

}