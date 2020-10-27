package cn.zhengyk.mail.api;

import cn.zhengyk.core.constants.ApplicationNameConstants;
import cn.zhengyk.mail.fallback.MailFeignClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/15 17:38
 */
@FeignClient(value = ApplicationNameConstants.MAIL, fallbackFactory = MailFeignClientFallback.class)
public interface MailFeignClient {

    @PostMapping("sendExceptionEmail")
    void sendExceptionEmail();
}
