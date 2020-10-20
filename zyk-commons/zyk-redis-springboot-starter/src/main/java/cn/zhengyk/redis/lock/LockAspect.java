package cn.zhengyk.redis.lock;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 分布式锁切面
 * @author yakai.zheng
 */
@Slf4j
@Aspect
public class LockAspect {

    @Autowired
    private DistributedLock locker;


    @Around("@annotation(lock)")
    public Object aroundLock(ProceedingJoinPoint point, Lock lock) throws Throwable {
        String lockKey = lock.key();
        if (locker == null) {
            throw new RuntimeException("DistributedLock is null");
        }
        if (StringUtils.isEmpty(lockKey)) {
            throw new RuntimeException("lockKey is null");
        }
        try (ZLock zLock = locker.tryLock(lockKey, lock.waitTime(), lock.leaseTime(), lock.unit(), lock.isFair())) {
            //加锁
            if (zLock != null && zLock.locked()) {
                return point.proceed();
            } else {
                throw new RuntimeException("获取锁失败");
            }
        }
    }
}
