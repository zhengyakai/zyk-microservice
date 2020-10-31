package cn.zhengyk.mqtt;

import cn.zhengyk.mqtt.core.MqttTemplate;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Yakai Zheng
 * @description
 * @date 2020/10/31 4:51 下午
 * I'm glad to share knowledge with you all together.
 */
@Slf4j
@Component
public class Sub implements InitializingBean {

    @Autowired
    private MqttTemplate mqttTemplate;


    @Override
    public void afterPropertiesSet() throws Exception {
        log.info("开始监听......");
        String clientId = mqttTemplate.getMqttAsyncClient().getClientId();
        mqttTemplate.subscribe("topic/test/a", 0, (topic, message) -> log.info("服务端:{}从topic:{},接收到消息:{}",clientId,topic, message));
    }
}
