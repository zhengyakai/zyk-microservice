package cn.zhengyk.api;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/15 17:38
 */
@FeignClient("producer")
public interface DemoFeignClient {

    @RequestMapping("test1")
    String test1();

}
