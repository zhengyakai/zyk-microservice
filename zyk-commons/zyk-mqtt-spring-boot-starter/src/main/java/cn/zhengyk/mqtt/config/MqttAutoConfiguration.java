package cn.zhengyk.mqtt.config;

import cn.zhengyk.core.utils.IpUtil;
import cn.zhengyk.mqtt.core.MqttTemplate;
import cn.zhengyk.mqtt.factory.MqttClientFactory;
import cn.zhengyk.mqtt.factory.support.DefaultMqttClientFactory;
import cn.zhengyk.mqtt.properties.MqttProperties;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

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

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.port}")
    private String port;


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

    @Bean
    @ConditionalOnMissingBean
    public MqttConnectOptions mqttConnectOptions() {
        MqttConnectOptions options = new MqttConnectOptions();
        // getServerUris 会覆盖上面的 mqttProperties.getOneUrl()
        options.setServerURIs(mqttProperties.getServerUris());
        options.setKeepAliveInterval(mqttProperties.getKeepAliveInterval());
        options.setAutomaticReconnect(mqttProperties.getAutoReconnect());
        options.setCleanSession(mqttProperties.getCleanSession());
        options.setConnectionTimeout(mqttProperties.getConnectTimeOut());

        String user = mqttProperties.getUserName();
        String password = mqttProperties.getPassword();
        if (StringUtils.isNoneBlank(user, password)) {
            options.setUserName(user);
            options.setPassword(password.toCharArray());
        }
        return options;
    }


    @Autowired
    @Bean(destroyMethod = "destroy")
    @ConditionalOnBean(MqttClientFactory.class)
    @ConditionalOnMissingBean(MqttTemplate.class)
    public MqttTemplate mqttTemplate(MqttClientFactory mqttClientFactory, MqttConnectOptions options) throws Exception {
        MqttTemplate mqttTemplate = new MqttTemplate();
        mqttTemplate.setEnv(env);
        mqttTemplate.setMqttClientFactory(mqttClientFactory);

        String clientId = appName + "_" + IpUtil.getHostIp() + ":" + port;
        IMqttAsyncClient mqttAsyncClient = mqttClientFactory.createMqttAsyncClient(clientId);
        mqttAsyncClient.connect(options, null, new IMqttActionListener() {
            @Override
            public void onSuccess(IMqttToken asyncActionToken) {
                log.info("客户端{},连接 mqtt 服务器成功", clientId);
            }
            @SneakyThrows
            @Override
            public void onFailure(IMqttToken asyncActionToken, Throwable exception) {
                log.error("客户端"+clientId+",连接 mqtt 服务器失败", exception);
                throw exception;
            }
        });

        mqttTemplate.setMqttAsyncClient(mqttAsyncClient);
        return mqttTemplate;
    }

}
