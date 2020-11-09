package cn.zhengyk.canal.config;

import cn.zhengyk.canal.handler.DataDbHandler;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author by yakai.zheng
 * @Description
 */
@Component
public class BaseHolder implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    private static Map<String, DataDbHandler> handlers;


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        BaseHolder.applicationContext = applicationContext;
        handlers = applicationContext.getBeansOfType(DataDbHandler.class);
    }



    /**
     * 这里约定， 定义 bean 的时候， beanName 统一转小写
     */
    public static DataDbHandler getDbDataHandler(String beanName) {
        if (StringUtils.isNotEmpty(beanName)) {
            beanName = beanName.toLowerCase();
        }
        return handlers.get(beanName);
    }

}
