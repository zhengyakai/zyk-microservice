package cn.zhengyk.mqtt.factory.support;

import cn.zhengyk.mqtt.factory.MqttClientFactory;
import org.eclipse.paho.client.mqttv3.*;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author by yakai.zheng
 * @Description
 * @Date 2020/10/30 17:32
 */
public class DefaultMqttClientFactory implements MqttClientFactory {

    private MqttClientPersistence persistence;

    private String serverUri;

    public DefaultMqttClientFactory(String serverUri) {
        this.serverUri = serverUri;
    }

    public DefaultMqttClientFactory(String serverUri, MqttClientPersistence persistence) {
        this.serverUri = serverUri;
        this.persistence = persistence;
    }



    @Override
    public IMqttClient createMqttSyncClient(String clientId) throws MqttException {
        return new MqttClient(serverUri, clientId, this.persistence);
    }

    @Override
    public IMqttClient createMqttSyncClient(String clientId, MqttClientPersistence persistence) throws MqttException {
        return new MqttClient(serverUri, clientId, persistence);
    }

    @Override
    public IMqttAsyncClient createMqttAsyncClient(String clientId) throws MqttException {
        return new MqttAsyncClient(serverUri, clientId, this.persistence);
    }

    @Override
    public IMqttAsyncClient createMqttAsyncClient(String clientId, MqttClientPersistence persistence) throws MqttException {
        return new MqttAsyncClient(serverUri, clientId, persistence);
    }
}
