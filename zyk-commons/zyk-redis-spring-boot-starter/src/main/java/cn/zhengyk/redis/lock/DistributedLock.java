package cn.zhengyk.redis.lock;

import java.util.concurrent.TimeUnit;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/19 15:41
 */
public interface DistributedLock {


    /**
     * 尝试获取锁，如果锁不可用则等待最多 waitTime 后放弃
     * @param key 锁的key
     * @param waitTime 获取锁的最大尝试时间
     * @param leaseTime 加锁的时间，超过这个时间后锁便自动解锁；
     *                  如果leaseTime为-1，则保持锁定直到显式解锁
     * @param unit waitTime 和 leaseTime 参数的时间单位
     * @return boolean 获取锁成功返回 true, 反之 false
     */
    ZLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws Exception;

    /**
     * 默认非公平锁
     */
    default ZLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit) throws Exception {
        return this.tryLock(key, waitTime, leaseTime, unit, false);
    }
}
