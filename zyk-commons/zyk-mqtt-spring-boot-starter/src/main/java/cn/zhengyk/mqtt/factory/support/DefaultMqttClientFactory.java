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

    private final MqttClientPersistence persistence;

    private final String serverUri;

    public DefaultMqttClientFactory(String serverUri, MqttClientPersistence persistence) {
        this.serverUri = serverUri;
        this.persistence = persistence;
    }


    @Override
    public IMqttAsyncClient createMqttAsyncClient(String clientId) throws MqttException {
        return new MqttAsyncClient(serverUri, clientId, this.persistence);
    }
}
