package cn.zhengyk.exception.service.impl;

import cn.zhengyk.exception.properties.ExceptionEmailProperties;
import cn.zhengyk.exception.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

/**
 * @author Yakai Zheng
 * @description
 * @date 2020/10/26 10:28 下午
 * I'm glad to share knowledge with you all together.
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;



    @Override
    public void sendEmail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("yakai512@163.com");
        message.setTo("1508379854@qq.com");
        message.setSubject("你好");
        message.setText("今天天气不错");

        mailSender.send(message);

    }
}
