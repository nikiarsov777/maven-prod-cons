package com.nikiarsov.app.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.nikiarsov.app")
public class AppConfig {

    // @Bean
    // public BlockingQueue blockingQueue() {
    //     return new LinkedBlockingQueue();
    // }
}
