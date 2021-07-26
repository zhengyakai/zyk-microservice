package cn.zhengyk.redis.lock;

import cn.zhengyk.core.utils.SpELUtil;
import cn.zhengyk.redis.exception.LockException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.Expression;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Objects;
import java.util.Optional;

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
            throw new LockException("DistributedLock is null");
        }
        if (StringUtils.isEmpty(lockKey)) {
            throw new LockException("lockKey is null");
        }

        if (lockKey.contains("#")) {
            MethodSignature methodSignature = (MethodSignature)point.getSignature();
            //获取方法参数值
            Object[] args = point.getArgs();
            lockKey = SpELUtil.getValBySpel(lockKey, methodSignature, args);
            if (lockKey == null) {
                throw new LockException("根据SpEL表达式，未获取到 key");
            }
        }

        try (ZLock zLock = locker.tryLock(lockKey, lock.waitTime(), lock.leaseTime(), lock.unit(), lock.isFair())) {
            //加锁
            if (zLock != null && zLock.locked()) {
                return point.proceed();
            } else {
                throw new LockException("获取锁["+lockKey+"]失败");
            }
        }
    }
}
