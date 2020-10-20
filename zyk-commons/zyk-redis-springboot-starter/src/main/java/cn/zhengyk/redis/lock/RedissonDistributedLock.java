package cn.zhengyk.redis.lock;

import cn.zhengyk.redis.constant.RedisConstant;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;

import java.util.concurrent.TimeUnit;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/19 15:44
 */
@ConditionalOnClass(RedissonClient.class)
public class RedissonDistributedLock implements DistributedLock{


    @Autowired
    private RedissonClient redissonClient;

    private ZLock getLock(String key, boolean isFair) {
        RLock lock;
        if (isFair) {
            lock = redissonClient.getFairLock(RedisConstant.REDIS_LOCK_KEY_PREFIX + key);
        } else {
            lock =  redissonClient.getLock(RedisConstant.REDIS_LOCK_KEY_PREFIX + key);
        }
        return new ZLock(lock);
    }

    @Override
    public ZLock tryLock(String key, long waitTime, long leaseTime, TimeUnit unit, boolean isFair) throws InterruptedException {
        ZLock zLock = this.getLock(key, isFair);
        RLock rLock = zLock.getRLock();
        if (rLock.tryLock(waitTime, leaseTime, unit)) {
            return zLock;
        }
        return zLock;
    }
}
