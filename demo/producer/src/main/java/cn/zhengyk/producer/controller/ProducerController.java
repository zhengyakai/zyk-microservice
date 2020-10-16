package cn.zhengyk.producer.controller;

import cn.zhengyk.api.DemoFeignClient;
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
    @RequestMapping("test1")
    public String test1() {
        RLock aaaa = redissonClient.getLock("aaaa");
        aaaa.lock(100, TimeUnit.MINUTES);
        return "testaaa";
    }
 }
