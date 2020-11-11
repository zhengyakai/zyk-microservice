package cn.zhengyk.es.config;

import cn.zhengyk.es.properties.ElasticSearchProperties;
import cn.zhengyk.es.properties.RestPoolProperties;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Yakai Zheng
 */
@Configuration
@EnableConfigurationProperties({ElasticSearchProperties.class, RestPoolProperties.class})
public class ElasticSearchAutoConfig {

    @Autowired
    private ElasticSearchProperties esProperties;

    @Autowired
    private RestPoolProperties restPoolProperties;

    @Bean
    public RestHighLevelClient restHighLevelClient() {
        // 解析hostList配置信息
        HttpHost[] httpHostArray = getHttpHosts();
        // 创建RestHighLevelClient客户端
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(esProperties.getUsername(), esProperties.getPassword()));
        RestClientBuilder builder = RestClient.builder(httpHostArray);

        //设置请求超时时间
        builder.setRequestConfigCallback(requestConfigBuilder -> {
            requestConfigBuilder.setConnectTimeout(restPoolProperties.getConnectTimeOut());
            requestConfigBuilder.setSocketTimeout(restPoolProperties.getSocketTimeOut());
            requestConfigBuilder.setConnectionRequestTimeout(restPoolProperties.getConnectionRequestTimeOut());
            return requestConfigBuilder;
        });

        // 连接 认证
        builder.setHttpClientConfigCallback(httpClientBuilder -> {
            httpClientBuilder.setMaxConnTotal(restPoolProperties.getMaxConnTotal());
            httpClientBuilder.setMaxConnPerRoute(restPoolProperties.getMaxConnPerRoute());
            httpClientBuilder.setDefaultCredentialsProvider(credentialsProvider);
            return httpClientBuilder;
        });
        return new RestHighLevelClient(builder);
    }






    private HttpHost[] getHttpHosts() {
        String[] split = esProperties.getHostList().split(",");
        // 创建HttpHost数组，其中存放es主机和端口的配置信息
        HttpHost[] httpHostArray = new HttpHost[split.length];
        for (int i = 0; i < split.length; i++) {
            String item = split[i];
            httpHostArray[i] = new HttpHost(item.split(":")[0], Integer.parseInt(item.split(":")[1]), "http");
        }
        return httpHostArray;
    }

}
