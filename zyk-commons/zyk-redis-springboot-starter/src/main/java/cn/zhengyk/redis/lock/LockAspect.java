package cn.zhengyk.redis.lock;

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

    /**
     * 用于SpEL表达式解析.
     */
    private final SpelExpressionParser spelExpressionParser = new SpelExpressionParser();
    /**
     * 用于获取方法参数定义名字.
     */
    private final DefaultParameterNameDiscoverer nameDiscoverer = new DefaultParameterNameDiscoverer();


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
            lockKey = this.getValBySpel(lockKey, methodSignature, args);
            if (lockKey == null) {
                throw new LockException("错误的 SpEL 表达式");
            }
        }

        try (ZLock zLock = locker.tryLock(lockKey, lock.waitTime(), lock.leaseTime(), lock.unit(), lock.isFair())) {
            //加锁
            if (zLock != null && zLock.locked()) {
                return point.proceed();
            } else {
                throw new LockException("获取锁失败");
            }
        }
    }


    /**
     * 解析spEL表达式
     */
    private String getValBySpel(String spelLockKey, MethodSignature methodSignature, Object[] args) {
        //获取方法形参名数组
        String[] paramNames = nameDiscoverer.getParameterNames(methodSignature.getMethod());
        if (paramNames != null && paramNames.length > 0) {
            Expression expression = spelExpressionParser.parseExpression(spelLockKey);
            // spring的表达式上下文对象
            EvaluationContext context = new StandardEvaluationContext();
            // 给上下文赋值
            for(int i = 0; i < args.length; i++) {
                context.setVariable(paramNames[i], args[i]);
            }
            Object value = expression.getValue(context);
            if (value != null) {
                return value.toString();
            }else {
                return null;
            }
        }
        return null;
    }
}
