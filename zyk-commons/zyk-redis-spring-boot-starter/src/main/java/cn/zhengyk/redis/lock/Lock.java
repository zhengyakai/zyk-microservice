package cn.zhengyk.redis.lock;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

/**
 * @author yakai.zheng
 * 分布式锁注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Lock {

    /**
     * 锁的 key
     */
    String key();
    /**
     * 获取锁的最大尝试时间
     */
    long waitTime() default 1000;

    /**
     * 加锁的时间, 超过这个时间后锁便自动解锁；
     * 如果leaseTime为-1，则保持锁定直到显式解锁
     */
    long leaseTime() default -1;

    /**
     * waitTime 和 leaseTime 的时间单位
     */
    TimeUnit unit() default TimeUnit.MILLISECONDS;

    /**
     * 是否公平锁
     */
    boolean isFair() default false;
}
