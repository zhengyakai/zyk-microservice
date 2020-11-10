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
     * 业务模块 自己根据业务定义
     */
    String businessModule() default "";
    /**
     * 操作信息
     */
    String operation();

}
