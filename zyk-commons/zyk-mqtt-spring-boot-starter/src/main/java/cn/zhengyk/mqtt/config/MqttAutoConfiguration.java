package cn.zhengyk.mqtt.config;

import cn.zhengyk.mqtt.core.MqttTemplate;
import cn.zhengyk.mqtt.factory.MqttClientFactory;
import cn.zhengyk.mqtt.factory.support.DefaultMqttClientFactory;
import cn.zhengyk.mqtt.properties.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URISyntaxException;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/30 10:42
 */
@Slf4j
@Configuration
@EnableConfigurationProperties(MqttProperties.class)
public class MqttAutoConfiguration {

    @Autowired
    private MqttProperties mqttProperties;


    @Bean
    @ConditionalOnMissingBean
    public MqttClientPersistence mqttClientPersistence() {
        return new MemoryPersistence();
    }

    @Bean
    @Autowired
    @ConditionalOnMissingBean
    public MqttClientFactory mqttClientFactory(MqttClientPersistence persistence) {
        return new DefaultMqttClientFactory(mqttProperties.getOneUrl(), persistence);
    }

    @Bean(destroyMethod = "destroy")
    @ConditionalOnBean(MqttClientFactory.class)
    @ConditionalOnMissingBean(MqttTemplate.class)
    public MqttTemplate mqttTemplate(MqttClientFactory mqttClientFactory) {
        MqttTemplate mqttTemplate = new MqttTemplate();
//        mqttTemplate.setMqttClientFactory(mqttClientFactory);
//        log.info("{}", mqttProperties);
//        mqttTemplate.setConnectOptions(mqttProperties.getConnectOptions());
//        mqttTemplate.setObjectMapper(mqttObjectMapper);
        return mqttTemplate;
    }

}
