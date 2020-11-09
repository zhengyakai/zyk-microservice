package cn.zhengyk.log.annotation;

import java.lang.annotation.*;


/**
 * @author yakai.zheng
 */
@Documented
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface HistoryLog {
    /**
     * 操作信息
     */
    String operation();
}
