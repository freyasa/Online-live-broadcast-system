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

    @Value("${spring.data.redis.database.db0}")
    private int db0;

    @Value("${spring.data.redis.database.db1}")
    private int db1;

    @Value("${spring.data.redis.host}")
    private String host;

    @Value("${spring.data.redis.password}")
    private String password;

    @Value("${spring.data.redis.port}")
    private int port;

    @Value("${spring.data.redis.timeout}")
    private int timeout;

    @Value("${spring.data.redis.lettuce.pool.max-idle}")
    private int maxIdle;

    @Value("${spring.data.redis.lettuce.pool.max-active}")
    private int maxActive;

    @Value("${spring.data.redis.lettuce.pool.min-idle}")
    private int minIdle;

    @Bean
    public GenericObjectPoolConfig getPoolConfig(){
        // 配置redis连接池
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(maxActive);
        poolConfig.setMaxIdle(maxIdle);
        poolConfig.setMinIdle(minIdle);
        return poolConfig;
    }

    private RedisConnectionFactory getFactory(int database) {
        // 构建工厂对象
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        config.setPassword(RedisPassword.of(password));
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