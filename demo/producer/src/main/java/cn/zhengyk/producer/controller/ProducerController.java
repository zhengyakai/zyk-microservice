package cn.zhengyk.producer.controller;

import cn.zhengyk.api.DemoFeignClient;
import cn.zhengyk.log.annotation.HistoryLog;
import cn.zhengyk.producer.beans.Person;
import cn.zhengyk.redis.lock.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/15 17:16
 */

@Slf4j
@Api("测试controller")
@RestController
public class ProducerController implements DemoFeignClient{

    @Autowired
    private RedissonClient redissonClient;

    @Override
    @Lock(key = "bbb", leaseTime = 15000)
    @GetMapping("test1")
    @ApiOperation("test1")
    public String test1() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "test1";
    }

    @Lock(key = "bbb", leaseTime = 15000)
    @GetMapping("test2")
    public String test2() {
        return "test2";
    }



    @Lock(key = "#username", leaseTime = 15000)
    @GetMapping("test3")
    public String test3(String username) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "test3";
    }

    @Lock(key = "#person.username", leaseTime = 15000)
    @GetMapping("test4")
    public String test4(Person person) {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "test4";
    }


    @GetMapping("test5")
    public String test5(Person person) {
        int a = 1/0;
        return "test5";
    }

    @GetMapping("test6")
    @HistoryLog(businessModule = "user",operation = "'测试用户:' + #person.username")
    public String test6(Person person) {
        log.error(MDC.get("userId"));
        return "test6";
    }


 }
