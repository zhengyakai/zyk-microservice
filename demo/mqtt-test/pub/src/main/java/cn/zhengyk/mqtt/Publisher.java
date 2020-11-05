package cn.zhengyk.mqtt;

import cn.zhengyk.core.utils.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@Slf4j
public class Publisher {

    private static final String CONNECTION_URL = "tcp://192.168.1.100:1883";
    private static final String PUB_TOPIC = "simple_test";
    private static final String USERNAME = "zhengyk";
    private static final String PASSWORD = "Yakai1991@";


    public static void main(String[] args) throws Exception {

        MqttClient client = new MqttClient(CONNECTION_URL, MqttClient.generateClientId(), new MemoryPersistence());
        MqttConnectOptions connOpts = setUpConnectionOptions();
        client.connect(connOpts);
        JsonUtil.writeValueAsBytes("aaaaa");
        client.publish(PUB_TOPIC, new MqttMessage());

    }

    private static MqttConnectOptions setUpConnectionOptions() {
        MqttConnectOptions connOpts = new MqttConnectOptions();
        connOpts.setCleanSession(true);
        connOpts.setUserName(Publisher.USERNAME);
        connOpts.setPassword(Publisher.PASSWORD.toCharArray());
        return connOpts;
    }

}