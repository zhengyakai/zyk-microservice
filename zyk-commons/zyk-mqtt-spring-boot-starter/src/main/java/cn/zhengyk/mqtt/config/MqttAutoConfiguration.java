package cn.zhengyk.mqtt.config;

import cn.zhengyk.mqtt.core.MqttTemplate;
import cn.zhengyk.mqtt.factory.MqttClientFactory;
import cn.zhengyk.mqtt.factory.support.DefaultMqttClientFactory;
import cn.zhengyk.mqtt.properties.MqttProperties;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${spring.profiles.active:dev}")
    private String env;


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
    @Autowired
    @ConditionalOnBean(MqttClientFactory.class)
    @ConditionalOnMissingBean(MqttTemplate.class)
    public MqttTemplate mqttTemplate(MqttClientFactory mqttClientFactory) throws Exception {
        MqttTemplate mqttTemplate = new MqttTemplate();
        mqttTemplate.setEnv(env);
        mqttTemplate.setMqttClientFactory(mqttClientFactory);
        IMqttAsyncClient mqttAsyncClient = mqttClientFactory.createMqttAsyncClient(mqttProperties.getClientId());
        mqttAsyncClient.connect();
        while (!mqttAsyncClient.isConnected()) {
            Thread.sleep(500);
        }
        log.info("连接 mqtt 服务器成功");
        mqttTemplate.setMqttAsyncClient(mqttAsyncClient);
        return mqttTemplate;
    }

}
