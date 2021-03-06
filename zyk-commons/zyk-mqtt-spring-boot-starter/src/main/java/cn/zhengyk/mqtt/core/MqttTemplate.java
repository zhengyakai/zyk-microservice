package cn.zhengyk.mqtt.core;

import cn.zhengyk.core.utils.JsonUtil;
import cn.zhengyk.mqtt.factory.MqttClientFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.*;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import java.nio.charset.Charset;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/30 17:26
 */
@Getter
@Setter
@Slf4j
public class MqttTemplate implements DisposableBean {

    private static final String CHARSET = "UTF-8";

    private String env = "dev";

    private ObjectMapper objectMapper;

    private MqttClientFactory mqttClientFactory;

    private IMqttAsyncClient mqttAsyncClient;

    /**
     * @param topic 主题
     * @param message 消息体
     **/
    public void asyncPublish(String topic, Object message) throws MqttException {
        byte[] payload;
        try {
            payload = JsonUtil.writeValueAsBytes(message);
        } catch (Exception e) {
            throw new MqttException(e);
        }
        this.mqttAsyncClient.publish(prependEnv(topic), new MqttMessage(payload));
    }

    /**
     * @param topic 主题
     * @param message 消息体
     * @param qos 通信质量
     *            0-最多一次
     *            1-至少一次
     *            2-只有一次
     * @param retained broker 是否保留消息
     **/
    public void asyncPublish(String topic, Object message, int qos, boolean retained) throws MqttException {
        this.checkQos(qos);
        byte[] payload;
        try {
            payload = JsonUtil.writeValueAsBytes(message);
        } catch (Exception e) {
            throw new MqttException(e);
        }
        this.mqttAsyncClient.publish(prependEnv(topic), payload, qos, retained);
    }

    public void asyncPublish(String topic, byte[] payload, int qos, boolean retained) throws MqttException {
        this.checkQos(qos);
        this.mqttAsyncClient.publish(prependEnv(topic), payload, qos, retained);
    }


    /**
     * @param topic 主题
     * @param qos  通信质量
     * @param messageListener 消息消费者
     **/
    public IMqttToken subscribe(String topic, int qos, IMqttMessageListener messageListener) throws MqttException {
        this.checkQos(qos);
        return this.mqttAsyncClient.subscribe(prependEnv(topic), qos, messageListener);
    }

    private String prependEnv(String topic) {
        return getEnv() + "/" + topic;
    }

    private void checkQos(int qos) throws MqttException {
        if (qos != 0 && qos != 1 && qos != 2) {
            throw new MqttException(new Exception("mqtt 协议 qos 只能为 0、1、2"));
        }
    }




    @Override
    public void destroy() throws Exception {
        mqttAsyncClient.disconnect();
    }


}
