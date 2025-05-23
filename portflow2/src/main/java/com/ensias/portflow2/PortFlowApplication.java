package com.ensias.portflow2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class PortFlowApplication {
    public static void main(String[] args) {
        SpringApplication.run(PortFlowApplication.class, args);
    }
}