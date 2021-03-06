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
     * mqtt服务器集群地址数组 示例:
     * -tcp://192.168.1.100:1883
     * -tcp://192.168.1.101:1883
     */
    private String[] serverUris;

    private String userName =  "zhengyk";

    private String password = "Yakai1991@";

    /**
     * 连接超时
     */
    private Integer connectTimeOut = 30;

    /**
     * 设置会话心跳时间 单位为秒 服务器会每隔(1.5*keepTime)秒的时间向客户端发送个消息判断客户端是否在线
     */
    private Integer keepAliveInterval = 60;

    /**
     * 断开是否自动重联
     */
    private Boolean autoReconnect = true;

    /**
     * 客户端每次连接是否清除 session， 为否表示是 重连时之前发的消息不会保存
     */
    private Boolean cleanSession = true;

    /**
     * 消息保存类型  memory、file
     * 使用 file 的好处是 保证了数据肯定能够发送到服务器， 只要发送的数据包没有收到确认，
     * 这个数据包就一直保存在文件当中，直到其发送出去为止
     */
    private String persistenceType = "memory";


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
