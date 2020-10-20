package cn.zhengyk.redis.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.List;


/**
 * 根据 springboot 的 RedisProperties 配置 Redisson
 * @author yakai.zheng
 */
//@Configuration
@EnableConfigurationProperties(RedisProperties.class)
public class RedissonAutoConfig {


    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String password = redisProperties.getPassword();
        RedisProperties.Pool pool = redisProperties.getLettuce().getPool();

        Duration connectTimeoutDuration = redisProperties.getTimeout();
        int connectTimeout = 20000;
        if (connectTimeoutDuration != null) {
            connectTimeout =  Long.valueOf(connectTimeoutDuration.toMillis()).intValue();
        }

        // 集群模式
        if (redisProperties.getCluster() != null) {
            List<String> nodes = redisProperties.getCluster().getNodes();
            config.useClusterServers()
                    .addNodeAddress(nodes.stream().map(node -> "redis://" + node).toArray(String[]::new))
                    .setMasterConnectionPoolSize(pool.getMaxActive())
                    .setSlaveConnectionPoolSize(pool.getMaxActive())
                    .setConnectTimeout(connectTimeout)
                    .setPassword(password);
            return Redisson.create(config);
        }

        // 哨兵模式
        if (redisProperties.getSentinel() != null) {
            List<String> nodes = redisProperties.getSentinel().getNodes();
            config.useSentinelServers()
                    .addSentinelAddress(nodes.stream().map(node -> "redis://" + node).toArray(String[]::new))
                    .setMasterName(redisProperties.getSentinel().getMaster())
                    .setMasterConnectionPoolSize(pool.getMaxActive())
                    .setSlaveConnectionPoolSize(pool.getMaxActive())
                    .setConnectTimeout(connectTimeout)
                    .setPassword(password);
            return Redisson.create(config);
        }

        // 单机模式
        config.useSingleServer()
                .setAddress("redis://" + redisProperties.getHost() + ":" + redisProperties.getPort())
                .setDatabase(redisProperties.getDatabase())
                .setConnectionPoolSize(pool.getMaxActive())
                .setConnectTimeout(connectTimeout)
                .setPassword(password);
        return Redisson.create(config);
    }
}