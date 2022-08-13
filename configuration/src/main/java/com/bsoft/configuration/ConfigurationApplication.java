package com.bsoft.configuration;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnCloudPlatform;
import org.springframework.boot.cloud.CloudPlatform;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.EventListener;
import org.springframework.core.env.Environment;

@SpringBootApplication
@RequiredArgsConstructor
public class ConfigurationApplication {

    private final Environment environment;

    public static void main(String[] args) {

        SpringApplication.run(ConfigurationApplication.class, args);
    }

    @EventListener({ApplicationReadyEvent.class})
    public void begin() {

        System.out.println("the message is: " + this.environment.getProperty("message"));
    }

    @Bean
    @ConditionalOnCloudPlatform(CloudPlatform.KUBERNETES)
    ApplicationListener<ApplicationReadyEvent>  readyEventApplicationListener() {
        return args -> System.out.println("the application is running on k8s!");
    }
}
