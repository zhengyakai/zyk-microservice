package cn.zhengyk.mail.service.impl;

import cn.zhengyk.mail.service.MailService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @author Yakai Zheng
 * @description
 * @date 2020/10/26 10:28 下午
 * I'm glad to share knowledge with you all together.
 */
@Slf4j
@Service
@RefreshScope
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private String from;

    /**
     * nacos 里配置会覆盖本地
     */
    @Value("${exception-mail.to}")
    private String to;



    @Override
    public void sendExceptionEmail(String subject, String content) {
        log.info("to:{}", to);
        if (StringUtils.isBlank(to)) {
            log.info("收件邮箱列表为空");
            return;
        }
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(from);
        message.setTo(StringUtils.split(to,","));
        message.setSubject(subject);
        message.setText(content);
        mailSender.send(message);

    }
}
