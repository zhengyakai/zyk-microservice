package cn.zhengyk.mqtt.config;

import cn.zhengyk.mqtt.properties.MqttProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/30 10:42
 */
@EnableConfigurationProperties(MqttProperties.class)
@ConditionalOnProperty(prefix = "mqtt", name = "enable", havingValue = "true")
public class MqttAutoConfiguration {

    @Autowired
    private MqttProperties mqttProperties;




}
