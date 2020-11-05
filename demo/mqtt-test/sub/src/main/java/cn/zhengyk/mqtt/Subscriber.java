package cn.zhengyk.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;

public class Subscriber {

    private static final String CONNECTION_URL = "tcp://192.168.1.100:1883";
    private static final String SUBSCRIPTION = "iot_data";
    private static final String USERNAME = "zhengyk";
    private static final String PASSWORD = "Yakai1991@";


    public static void main(String[] args) throws MqttException {

       System.out.println("== START SUBSCRIBER ==");

       MqttClient client = new MqttClient(CONNECTION_URL,
       MqttClient.generateClientId());

       MqttConnectOptions connOpts = setUpConnectionOptions();
       client.connect(connOpts);

       client.subscribe(SUBSCRIPTION);

    }

   private static MqttConnectOptions setUpConnectionOptions() {
       MqttConnectOptions connOpts = new MqttConnectOptions();
       connOpts.setCleanSession(true);
       connOpts.setUserName(Subscriber.USERNAME);
       connOpts.setPassword(Subscriber.PASSWORD.toCharArray());
       return connOpts;
   }  

  }