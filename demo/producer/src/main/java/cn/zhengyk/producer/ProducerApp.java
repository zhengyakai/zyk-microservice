package cn.zhengyk.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/15 17:15
 */
@EnableFeignClients("cn.zhengyk")
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("cn.zhengyk")
public class ProducerApp {

    public static void main(String[] args) {
        SpringApplication.run(ProducerApp.class, args);
    }
}
