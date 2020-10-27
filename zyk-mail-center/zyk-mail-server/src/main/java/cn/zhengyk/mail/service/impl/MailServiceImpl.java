package cn.zhengyk.mail.service.impl;

import cn.zhengyk.mail.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
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

    @Value("${spring.mail.username}")
    private String from;



    @Override
    public void sendExceptionEmail(String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo("1508379854@qq.com");
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);

    }
}
