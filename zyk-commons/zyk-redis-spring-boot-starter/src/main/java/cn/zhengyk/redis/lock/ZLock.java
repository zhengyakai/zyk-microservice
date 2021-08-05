package cn.zhengyk.redis.lock;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.redisson.api.RLock;

/**
 * @author by yakai.zheng
 * @Description  实现 AutoCloseable, 使用 try-with-resource 语句可以自动解锁
 * @Date 2020/10/19 15:35
 */
@Getter
@AllArgsConstructor
public class ZLock implements AutoCloseable{

    private final RLock rLock;

    @Override
    public void close() {
        if (this.locked()) {
            rLock.unlock();
        }
    }

    public boolean locked() {
        return rLock != null && rLock.isHeldByCurrentThread();
    }
}
