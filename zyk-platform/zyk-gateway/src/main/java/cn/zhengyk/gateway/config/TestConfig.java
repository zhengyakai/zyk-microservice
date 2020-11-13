package cn.zhengyk.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @author zyk
 */
@Configuration
public class TestConfig {

    @Bean
    public RouterFunction<ServerResponse> testRouterFunc() {
        RouterFunction<ServerResponse> route =  RouterFunctions.route(
                RequestPredicates.path ("/test") ,
        request -> ServerResponse.ok().body(BodyInserters.fromValue("hello ")));
        return route;
    }
}
