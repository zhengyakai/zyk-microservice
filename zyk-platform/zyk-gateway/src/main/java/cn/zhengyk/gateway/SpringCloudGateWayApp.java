package cn.zhengyk.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author zyk
 */
@EnableFeignClients("cn.zhengyk")
@SpringBootApplication
@EnableDiscoveryClient
public class SpringCloudGateWayApp {
    public static void main(String[] args) {
        SpringApplication.run(SpringCloudGateWayApp.class, args);
    }
}
