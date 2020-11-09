package cn.zhengyk.canal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @author yakai.zheng
 */
@SpringBootApplication
@EnableScheduling
@EnableAsync
public class CanalSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalSyncApplication.class, args);
    }
}