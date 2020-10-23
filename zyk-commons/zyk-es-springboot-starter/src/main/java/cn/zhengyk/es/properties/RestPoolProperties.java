package cn.zhengyk.es.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 客户端 rest 连接池配置
 * @author yakai.zheng
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "elasticsearch.rest-pool")
public class RestPoolProperties {
    /**
     * 连接建立超时时间
     */
    private Integer connectTimeOut = 1000;
    /**
     * 等待数据超时时间
     */
    private Integer socketTimeOut = 30000;
    /**
     * 连接池获取连接的超时时间
     */
    private Integer connectionRequestTimeOut = 500;
    /**
     * 最大连接数
     */
    private Integer maxConnTotal = 30;
    /**
     * 最大路由连接数
     */
    private Integer maxConnPerRoute = 10;
}