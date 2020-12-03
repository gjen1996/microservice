package com.glen.appcustomerlogin.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


//配置redis整入spring的缓存框架

@Configuration
@EnableCaching  //继承CachingConfigurerSupport并重写方法，配合该注解实现spring缓存框架的使用
public class RedisConfig extends CachingConfigurerSupport {
    /**
     * 载入配置文件配置的连接工厂
     **/
    @Autowired
    RedisConnectionFactory redisConnectionFactory;
    /*不提示警告信息*/
    @SuppressWarnings("rawtypes")
    @Autowired
    RedisTemplate redisTemplate;

    @Bean
    RedisTemplate<String, Object> objectRedisTemplate() {
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setKeySerializer(new StringRedisSerializer()); // key的序列化类型
        redisTemplate.setValueSerializer(new RedisObjectSerializer()); // value的序列化类型

        redisTemplate.setConnectionFactory(redisConnectionFactory);
        return redisTemplate;
    }

    /**
     * 设置缓存过期时间，默认不过期，需要过期的在此处添加cacheName
     */
    @Bean
    public CacheManager cacheManager() {

        RedisCacheManager cacheManager;

        RedisCacheConfiguration cacheConfiguration = RedisCacheConfiguration.defaultCacheConfig().entryTtl(Duration.ofDays(1))//缓存1天
                .disableCachingNullValues();
        // 设置一个初始化的缓存空间set集合
        Set<String> cacheNames = new HashSet<>();
        cacheNames.add("login");

        // 对每个缓存空间应用不同的配置
        Map<String, RedisCacheConfiguration> configMap = new HashMap<>();
        configMap.put("login", cacheConfiguration.entryTtl(Duration.ofSeconds(30)));
        cacheManager = RedisCacheManager.builder(redisConnectionFactory)     // 使用自定义的缓存配置初始化一个cacheManager
                .initialCacheNames(cacheNames)  // 注意这两句的调用顺序，一定要先调用该方法设置初始化的缓存名，再初始化相关的配置
                .withInitialCacheConfigurations(configMap)
                .build();

        return cacheManager;


    }


    /**
     * 重写缓存key生成策略，可根据自身业务需要进行自己的配置生成条件
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getName());
                sb.append(method.getName());
                for (Object obj : params) {
                    sb.append(obj.toString());
                }
                return sb.toString();
            }
        };
    }

}