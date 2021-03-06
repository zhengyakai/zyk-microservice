package cn.zhengyk.mqtt;

import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Slf4j
public class Subscriber {

    private static final String CONNECTION_URL = "tcp://192.168.1.100:1883";
    private static final String SUP_TOPIC = "simple_test";
    private static final String USERNAME = "zhengyk";
    private static final String PASSWORD = "Yakai1991@";


    public static void main(String[] args) throws MqttException {
        String clientId = MqttClient.generateClientId();
        MqttClient client = new MqttClient(CONNECTION_URL, clientId, new MemoryPersistence());

        MqttConnectOptions connOpts = setUpConnectionOptions();
        client.connect(connOpts);

        client.subscribe(SUP_TOPIC, 0, (topic, message) -> {
            log.info("客户端:{}从topic:{},接收到消息:{}",clientId,topic, message);
        });

    }

    private static MqttConnectOptions setUpConnectionOptions() {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(Subscriber.USERNAME);
        connOpts.setPassword(Subscriber.PASSWORD.toCharArray());
        return connOpts;
    }

}