package cn.zhengyk.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/27 11:28
 */
@EnableFeignClients("cn.zhengyk.mail")
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan("cn.zhengyk")
public class MailApp {

    public static void main(String[] args) {
        SpringApplication.run(MailApp.class, args);
    }
}
