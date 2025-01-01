package io.junnyland.redis.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.redisson.spring.data.connection.RedissonConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host}") private val host: String,
    @Value("\${spring.data.redis.port}") private val port: Int
) {
    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory? {
        return LettuceConnectionFactory(host, port)
    }

    @Bean(destroyMethod = "shutdown")
    fun redissonClient(): RedissonClient = Config()
        .apply {
            useSingleServer()
                .setAddress("redis://$host:$port")
                .setDnsMonitoringInterval(-1)
        }.let {
            Redisson.create()
        }

    @Bean
    fun redisConnectionFactory(redissonClient: RedissonClient): RedisConnectionFactory =
        RedissonConnectionFactory(redissonClient)
}