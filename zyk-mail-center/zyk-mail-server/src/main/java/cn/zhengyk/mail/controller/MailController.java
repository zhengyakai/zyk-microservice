package cn.zhengyk.mail.controller;

import cn.zhengyk.mail.api.MailFeignClient;
import cn.zhengyk.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/27 11:32
 */
@RestController
public class MailController implements MailFeignClient {

    @Autowired
    private MailService mailService;


    @Override
    @PostMapping("sendExceptionEmail")
    public void sendExceptionEmail() {
        mailService.sendExceptionEmail();
    }
}
