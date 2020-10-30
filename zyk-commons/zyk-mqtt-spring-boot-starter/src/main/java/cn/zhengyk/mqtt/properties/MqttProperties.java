package cn.zhengyk.mqtt.properties;

import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author by yakai.zheng
 * @Description MQTT 属性配置
 * @Date 2020/10/30 10:38
 */
@Getter
@Setter
@ConfigurationProperties("mqtt")
public class MqttProperties {


    public static AtomicInteger count = new AtomicInteger(0);

    private boolean enable = true;
    /**
     * mqtt服务器集群地址 示例: 192.168.1.100:1883,192.168.1.101:1883
     */
    private String urls = "127.0.0.1:1883";

    private String clientId;

    private String user =  "admin";

    private String password = "public";



    private Integer connectTimeOut = 3000;
    private Integer connectReTryMaxTimes = 5;
    private Integer connectReTryInterval = 60;
    private Integer keepAliveTime = 60000;

    private boolean isReConnect = true;
    private boolean autoConnectWhenStarted = true;
    private boolean cleanSession=true;


    /**
     * 简单的负载均衡  轮询
     */
    public String getOneUrl() {
        if (urls == null || urls.split(",").length == 0) {
            throw new RuntimeException("urls config error!");
        }
        String[] urlArr = urls.split(",");
        return  urlArr[count.incrementAndGet()%urlArr.length];
    }

}
