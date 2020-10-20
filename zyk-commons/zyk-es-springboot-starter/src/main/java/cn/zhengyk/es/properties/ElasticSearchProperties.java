package cn.zhengyk.es.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author yakai.zheng
 */
@Data
@ConfigurationProperties(prefix = "elasticsearch")
public class ElasticSearchProperties {

    /**
     * es 地址 示例：192.168.1.1:9200,192.168.1.2:9200,192.168.1.3:9200
     */
    private String hostList;

    /**
     * es 用户名
     */
    private String username;

    /**
     * es 密码
     */
    private String password;
}