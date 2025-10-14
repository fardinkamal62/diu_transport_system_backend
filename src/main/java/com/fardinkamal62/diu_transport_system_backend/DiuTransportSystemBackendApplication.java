package com.fardinkamal62.diu_transport_system_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DiuTransportSystemBackendApplication {

    static void main(String[] args) {
        java.io.File envFile = new java.io.File(".env");
        if (!envFile.exists()) {
            throw new RuntimeException(".env file not found in project root.");
        }
        SpringApplication.run(DiuTransportSystemBackendApplication.class, args);
    }
}
