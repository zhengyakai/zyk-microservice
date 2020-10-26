package cn.zhengyk.redis.exception;

/**
 * @author by yakai.zheng
 * @Description redis 分布式锁异常
 */
public class LockException extends RuntimeException {

    public LockException(String msg) {
        super(msg);
    }
}
