package cn.zhengyk.consumer.controller;

import cn.zhengyk.api.DemoFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/15 17:16
 */
@RestController
public class ConsumerController {

    @Autowired
    private DemoFeignClient demoFeignClient;

    @RequestMapping("test1")
    public String test1() {
        return demoFeignClient.test1();
    }
 }
