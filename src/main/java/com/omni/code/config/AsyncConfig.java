package com.omni.code.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@EnableAsync
public class AsyncConfig {

    @Bean
    public ThreadPoolTaskExecutor taskExecutor() {
        int availableProcessors = Runtime.getRuntime().availableProcessors();
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(availableProcessors); // Number of threads
        executor.setMaxPoolSize(20);  // Maximum number of threads
        executor.setQueueCapacity(100); // Queue capacity for pending tasks
        executor.setThreadNamePrefix("async-task-");
        executor.initialize();
        return executor;
    }
}
