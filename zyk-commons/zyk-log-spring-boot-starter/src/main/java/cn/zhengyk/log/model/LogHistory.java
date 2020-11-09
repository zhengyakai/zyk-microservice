package cn.zhengyk.log.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;


/**
 * @author yakai.zheng
 * 历史日志 bean
 */
@Setter
@Getter
public class LogHistory {
    /**
     * 操作时间
     */
    private Date operateTime;
    /**
     * 应用名
     */
    private String applicationName;
    /**
     * 类名
     */
    private String className;
    /**
     * 方法名
     */
    private String methodName;
    /**
     * 登录用户 id
     */
    private String userId;
    /**
     * 用户名
     */
    private String userName;
    /**
     * 操作信息
     */
    private String operation;
}
