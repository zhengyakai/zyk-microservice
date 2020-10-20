package cn.zhengyk.producer.controller;

import cn.zhengyk.api.DemoFeignClient;
import cn.zhengyk.redis.lock.Lock;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/15 17:16
 */

@RestController
public class ProducerController implements DemoFeignClient{

    @Autowired
    private RedissonClient redissonClient;

    @Override
    @Lock(key = "bbb", leaseTime = 15000)
    @RequestMapping("test1")
    public String test1() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "test1";
    }

    @Lock(key = "bbb", leaseTime = 15000)
    @RequestMapping("test2")
    public String test2() {
        return "test2";
    }
 }
