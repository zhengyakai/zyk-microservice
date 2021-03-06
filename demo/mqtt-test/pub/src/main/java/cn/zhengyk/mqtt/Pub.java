package cn.zhengyk.mqtt;

import cn.zhengyk.mqtt.core.MqttTemplate;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
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
public class Pub implements InitializingBean {

    @Autowired
    private MqttTemplate mqttTemplate;


    @Override
    public void afterPropertiesSet() throws Exception {
        IMqttAsyncClient mqttAsyncClient = mqttTemplate.getMqttAsyncClient();
        while (!mqttAsyncClient.isConnected()) {
            log.info("开始连接mqtt服务器....");
            Thread.sleep(500);
        }
        mqttTemplate.asyncPublish("topic/test/a","aaaa");
    }
}
