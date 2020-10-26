package cn.zhengyk.swagger.properties;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * swagger2 属性配置
 *
 * @author zlt
 * @date 2018/11/18 9:17
 */
@Data
@ConfigurationProperties("swagger")
public class SwaggerProperties {

    /**
     * 是否开启swagger
     */
    private Boolean enable;

    /**
     * 项目应用名
     */
    private String applicationName;

    /**
     * 描述
     */
    private String applicationDescription = "";

    /**
     * 版本
     */
    private String applicationVersion = "";

    /**
     * 接口调试地址
     */
    private String tryHost;

    /**
     * swagger会解析的包路径
     */
    private String basePackage = "";


}
