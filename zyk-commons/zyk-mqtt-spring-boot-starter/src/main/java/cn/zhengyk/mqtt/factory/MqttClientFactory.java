package cn.zhengyk.mqtt.factory;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttClient;
import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * mqtt 创建客户端 抽象工厂
 * @author yakai.zheng
 */
public interface MqttClientFactory {


    IMqttAsyncClient createMqttAsyncClient(String clientId) throws MqttException;

}
