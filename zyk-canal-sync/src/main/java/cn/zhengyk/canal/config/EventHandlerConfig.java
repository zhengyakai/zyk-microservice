package cn.zhengyk.canal.config;

import cn.zhengyk.canal.handler.DataDbHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

/**
 * @author by yakai.zheng
 * @Description 数据库事件handler 配置 （工厂模式）
 * 数据库的 schema、table 按照 schema.table 格式命名 bean，将每个表的业务处理解耦
 */
@Configuration
public class EventHandlerConfig implements ApplicationContextAware {
    private Map<String, DataDbHandler> handlers;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        handlers = applicationContext.getBeansOfType(DataDbHandler.class);
    }

    /**
     * 这里约定， 定义 bean 的时候， beanName 统一转小写
     */
    public DataDbHandler getDbDataHandler(String beanName) {
        if (StringUtils.isNotEmpty(beanName)) {
            beanName = beanName.toLowerCase();
        }
        return handlers.get(beanName);
    }
}
