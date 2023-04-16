package com.bill.app.config

import org.springframework.cache.CacheManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.concurrent.ConcurrentMapCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfig {

    @Bean
    fun cacheManager(): CacheManager? {
        val mgr = ConcurrentMapCacheManager()
        mgr.setCacheNames(mutableListOf("movies", "actors"))
        return mgr
    }
}