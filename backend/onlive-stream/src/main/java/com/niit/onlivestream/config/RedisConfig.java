package com.niit.onlivestream.config;


import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettucePoolingClientConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;

import static com.niit.onlivestream.contant.RedisDataUse.*;

/**
 * Redis多数据库配置
 * @author  arthur
 */
@Configuration
public class RedisConfig {

    private final int db0 = 0;

    private final int db1 = 1;

    @Bean
    public GenericObjectPoolConfig getPoolConfig(){
        // 配置redis连接池
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        int maxActive = 100;
        poolConfig.setMaxTotal(maxActive);
        int maxIdle = 100;
        poolConfig.setMaxIdle(maxIdle);
        int minIdle = 10;
        poolConfig.setMinIdle(minIdle);
        return poolConfig;
    }

    private RedisConnectionFactory getFactory(int database) {
        // 构建工厂对象
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        String host = "8.140.143.119";
        config.setHostName(host);
        int port = 6379;
        config.setPort(port);
        String password = "123456";
        config.setPassword(RedisPassword.of(password));
        int timeout = 3;
        LettucePoolingClientConfiguration clientConfig = LettucePoolingClientConfiguration.builder()
                .commandTimeout(Duration.ofSeconds(timeout))
                .poolConfig(getPoolConfig())
                .build();
        LettuceConnectionFactory factory = new LettuceConnectionFactory(config, clientConfig);
        // 设置使用的redis数据库
        factory.setDatabase(database);
        // 重新初始化工厂
        factory.afterPropertiesSet();
        return factory;
    }

    @NotNull
    private RedisTemplate<String, Object> getObjectRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        return redisTemplate;
    }

    /**
     * userToken数据库
     * @return
     */

    @Bean(name = TokenRedisTemplate)
    //加这个注解表示默认使用 也就是 主库的意思 我这里默认使用0号
    public RedisTemplate<String, Object> redisTemplate() {
        RedisTemplate<String, Object> redisTemplate = getObjectRedisTemplate();
        redisTemplate.setConnectionFactory(this.getFactory(db0));
        return redisTemplate;
    }
    @Bean(name = TokenStringRedisTemplate)
    public StringRedisTemplate stringRedisTemplate() {
        RedisConnectionFactory factory = getFactory(db0);
        return new StringRedisTemplate(factory);
    }

    /**
     * 直播信息数据库
     * @return
     */
    @Bean(name = LiveRedisTemplate)
    public RedisTemplate<String, Object> redisTemplate2() {
        RedisTemplate<String, Object> redisTemplate = getObjectRedisTemplate();
        redisTemplate.setConnectionFactory(this.getFactory(db1));
        return redisTemplate;
    }
    @Bean(name = LiveStringRedisTemplate)
    public StringRedisTemplate getRedisTemplate2(){
        RedisConnectionFactory factory = getFactory(db1);
        return new StringRedisTemplate(factory);
    }





}