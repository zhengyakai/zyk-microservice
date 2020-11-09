package cn.zhengyk.mail.api;

import cn.zhengyk.core.beans.R;
import cn.zhengyk.core.constants.ApplicationNameConstants;
import cn.zhengyk.mail.fallback.MailFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/15 17:38
 */
@FeignClient(value = "zyk-mail", fallbackFactory = MailFeignClientFallback.class)
public interface MailFeignClient {

    @PostMapping("sendExceptionEmail")
    R<?> sendExceptionEmail(@RequestParam("subject") String subject, @RequestParam("content")String content);
}
