package cn.zhengyk.exception.service;

import cn.zhengyk.mail.api.MailFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/27 14:30
 */

@Service
public class SendExceptionMailService {

    @Autowired
    private MailFeignClient mailFeignClient;

    @Async("asyncTaskExecutor")
    public void sendExceptionEmail(String subject, String content) {
        mailFeignClient.sendExceptionEmail(subject, content);
    }
}
