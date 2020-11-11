package cn.zhengyk.log.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/11/9 17:11
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "history-log")
public class HistoryLogProperties {

    /**
     * 是否开启 历史日志 记录
     */
    private Boolean enable;

    /**
     * 日志记录类型(logger/db/es)
     */
    private String logType;

}
