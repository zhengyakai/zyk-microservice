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


    public IMqttAsyncClient createAsyncClient(String clientId) throws MqttException {
        return mqttClientFactory.createMqttAsyncClient(clientId);
    }

    public IMqttClient createSyncClient(String clientId) throws MqttException {
        return mqttClientFactory.createMqttSyncClient(clientId);
    }

    public void asyncPublish(String topic, Object message) throws MqttException {
        byte[] payload = new byte[0];
        try {
            waitIfNotConnected();
            payload = JsonUtil.writeValueAsBytes(message);
        } catch (Exception e) {
            throw new MqttException(e);
        }
        this.mqttAsyncClient.publish(prependEnv(topic), new MqttMessage(payload));
    }

    public void asyncPublish(String topic, MqttMessage mqttMessage) throws MqttException {
        try {
            waitIfNotConnected();
        } catch (Exception e) {
            throw new MqttException(e);
        }
        this.mqttAsyncClient.publish(prependEnv(topic), mqttMessage);
    }

    public void asyncPublish(String topic, Object message, Object userContext, IMqttActionListener callback) throws MqttException {
        try {
            waitIfNotConnected();
            byte[] payload = JsonUtil.writeValueAsBytes(message);
            this.mqttAsyncClient.publish(prependEnv(topic), new MqttMessage(payload), userContext, callback);
        } catch (Exception e) {
            throw new MqttException(e);
        }
    }

    public void asyncPublish(String topic, MqttMessage mqttMessage, Object userContext, IMqttActionListener callback) throws MqttException {
        try {
            waitIfNotConnected();
        } catch (Exception e) {
            throw new MqttException(e);
        }
        this.mqttAsyncClient.publish(prependEnv(topic), mqttMessage, userContext, callback);
    }

    public void asyncPublish(String topic, Object message, int qos, boolean retained) throws MqttException {
        byte[] payload = new byte[0];
        try {
            waitIfNotConnected();
            payload = JsonUtil.writeValueAsBytes(message);
        } catch (Exception e) {
            throw new MqttException(e);
        }
        this.mqttAsyncClient.publish(prependEnv(topic), payload, qos, retained);
    }

    public void asyncPublish(String topic, byte[] payload, int qos, boolean retained) throws MqttException {
        try {
            waitIfNotConnected();
        } catch (Exception e) {
            throw new MqttException(e);
        }
        this.mqttAsyncClient.publish(prependEnv(topic), payload, qos, retained);
    }

    public void asyncPublish(String topic, Object message, int qos, boolean retained, Object userContext, IMqttActionListener callback) throws MqttException {
        byte[] payload = new byte[0];
        try {
            waitIfNotConnected();
            payload = JsonUtil.writeValueAsBytes(message);
        } catch (Exception e) {
            throw new MqttException(e);
        }
        this.mqttAsyncClient.publish(prependEnv(topic), payload, qos, retained, userContext, callback);
    }

    public void asyncPublish(String topic, byte[] payload, int qos, boolean retained, Object userContext, IMqttActionListener callback) throws MqttException {
        try {
            waitIfNotConnected();
        } catch (Exception e) {
            throw new MqttException(e);
        }
        this.mqttAsyncClient.publish(prependEnv(topic), payload, qos, retained, userContext, callback);
    }

    public IMqttToken subscribe(String topic, int qos, IMqttMessageListener messageListener) throws MqttException {
        try {
            waitIfNotConnected();
        } catch (Exception e) {
            throw new MqttException(e);
        }
        return this.mqttAsyncClient.subscribe(prependEnv(topic), qos, messageListener);
    }

    private String prependEnv(String topic) {
        return getEnv() + "/" + topic;
    }


    private void waitIfNotConnected() throws InterruptedException {
        while (!this.mqttAsyncClient.isConnected()) {
            Thread.sleep(200);
        }
    }


    @Override
    public void destroy() throws Exception {
        mqttAsyncClient.disconnect();
    }
}
