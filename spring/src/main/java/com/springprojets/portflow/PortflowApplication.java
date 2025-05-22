package com.springprojets.portflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EntityScan("com.springprojets.portflow")
@EnableJpaRepositories("com.springprojets.portflow")
@EnableScheduling
public class PortflowApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortflowApplication.class, args);
    }
} 