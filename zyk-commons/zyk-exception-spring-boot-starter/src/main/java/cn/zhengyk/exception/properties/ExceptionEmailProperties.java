package cn.zhengyk.exception.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/26 16:48
 */
@Getter
@Setter
@ConfigurationProperties("exception-email")
public class ExceptionEmailProperties {

    private boolean enable;

    /**
     * 发件人邮箱
     */
    private String from;

    /**
     * 密码
     */
    private String password;

    /**
     * 邮件接收者列表,多个以 “,” 分隔
     */
    private String to;

}