package cn.zhengyk.mqtt.properties;

import lombok.Getter;
import lombok.Setter;
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


    public static AtomicInteger count = new AtomicInteger(3);

    private boolean enable = true;
    /**
     * mqtt服务器集群地址数组 示例: 192.168.1.100:1883
     */
    private String[] serverUris;

    private String userName =  "admin";

    private String password = "public";

    private Integer connectTimeOut = 3000;
    private Integer keepAliveInterval = 10000;
    private Boolean autoReconnect = true;
    private Boolean cleanSession=true;


    /**
     * 简单的负载均衡  轮询
     */
    public String getOneUrl() {
        if (serverUris == null || serverUris.length == 0) {
            throw new RuntimeException("urls config error!");
        }
        return  serverUris[count.incrementAndGet()%serverUris.length];
    }

}
