package cn.zhengyk.mail.fallback;

import cn.zhengyk.core.constants.ApplicationNameConstants;
import cn.zhengyk.core.utils.LogUtil;
import cn.zhengyk.mail.api.MailFeignClient;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/27 10:55
 */
@Slf4j
@Component
public class MailFeignClientFallback implements FallbackFactory<MailFeignClient> {
    @Override
    public MailFeignClient create(Throwable throwable) {
        return new MailFeignClient() {
            @Override
            public void sendExceptionEmail() {
                LogUtil.logError(ApplicationNameConstants.MAIL, Thread.currentThread().getStackTrace()[1], throwable);
            }
        };

    }
}
