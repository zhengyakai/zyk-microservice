package cn.zhengyk.exception.pool;

import cn.zhengyk.core.config.DefaultAsycTaskConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;


/**
 * @author yakai.zheng
 */
@Configuration
public class AsyncTaskExecutorConfig extends DefaultAsycTaskConfig {

}
