package io.junnyland.redis.config

import jakarta.annotation.PostConstruct
import jakarta.annotation.PreDestroy
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisServer
import java.io.IOException


@Configuration
class EmbeddedConfig(
    private val redisPort:Int = 0
) {
    private lateinit var redisServer: RedisServer

    @PostConstruct
    @Throws(IOException::class)
    fun startRedis() {
        redisServer = Redis
        redisServer.start()
    }

    @PreDestroy
    fun stopRedis() {
        redisServer.stop()
    }
}