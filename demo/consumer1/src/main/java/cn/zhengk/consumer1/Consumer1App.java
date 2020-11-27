package cn.zhengk.consumer1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/15 17:15
 */
@EnableFeignClients("cn.zhengyk")
@EnableDiscoveryClient
@SpringBootApplication
public class Consumer1App {

    public static void main(String[] args) {
        SpringApplication.run(Consumer1App.class, args);
    }
}
