package cn.zhengyk.canal;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableScheduling
@MapperScan(value = { "com.zhengyk.canal.dao"})
@EnableTransactionManagement
@EnableAsync
public class CanalSyncApplication {

    public static void main(String[] args) {
        SpringApplication.run(CanalSyncApplication.class, args);
    }
}