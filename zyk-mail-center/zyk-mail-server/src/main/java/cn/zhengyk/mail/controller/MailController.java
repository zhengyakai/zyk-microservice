package cn.zhengyk.mail.controller;

import cn.zhengyk.core.beans.R;
import cn.zhengyk.mail.api.MailFeignClient;
import cn.zhengyk.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    public R<?> sendExceptionEmail(@RequestParam("subject")String subject, @RequestParam("content")String content) {
        mailService.sendExceptionEmail(subject, content);
        return R.ok();
    }
}
