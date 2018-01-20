package com.fmi110.springboot.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

/**
 * redis 配置类
 */
@Configuration
public class RedisConfig {

    /**
     * 注入 application.properties 中的 redis 的配置
     */
    @Autowired
    private RedisProperties redisProperties;

    @Bean
    public JedisConnectionFactory jedisConnectionFactory(){
        System.out.println("===jedisConnectionFactory==");
        JedisPoolConfig poolConfig = new JedisPoolConfig();
        // 将配置文件中的配置
        BeanUtils.copyProperties(redisProperties.getPool(),poolConfig);
        return new JedisConnectionFactory(poolConfig);
    }

    /**
     * 构建 redisTemplate 指定使用 Jackson2JsonRedisSerializer 序列化器,对数据进行序列化
     * @param jedisConnectionFactory
     * @return
     */
    @Bean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory jedisConnectionFactory) {

        System.out.println("=====redisTemplate=====jedisConnectionFactory="+jedisConnectionFactory);
        // 1 构建序列化器
        Jackson2JsonRedisSerializer<Object> jackson2JsonRedisSerializer =
                new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper mapper = new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(mapper);

        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        // 2 设置 redisTemplate 连接的redis服务器
        redisTemplate.setConnectionFactory(jedisConnectionFactory);
        // 3 设置序列化器
        redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
        redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }
}
