package com.fardinkamal62.diu_transport_system_backend.socket.config;

import com.corundumstudio.socketio.SocketIOServer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PreDestroy;

@Configuration
public class SocketConfig {
    @Value("${socket-server.host}")
    private String host;

    @Value("${socket-server.port}")
    private Integer port;

    @Value("${socket-server.upgrade-timeout}")
    private Integer setUpgradeTimeout;

    @Value("${socket-server.ping-timeout}")
    private Integer setPingTimeout;

    @Value("${socket-server.ping-interval}")
    private Integer setPingInterval;

    private SocketIOServer socketIOServer;

    @Bean(initMethod = "start")
    public SocketIOServer socketIOServer() {
        com.corundumstudio.socketio.Configuration config = new com.corundumstudio.socketio.Configuration();
        config.setHostname(host);
        config.setPort(port);

        config.setUpgradeTimeout(setUpgradeTimeout);
        config.setPingTimeout(setPingTimeout);
        config.setPingInterval(setPingInterval);

        config.setOrigin("*");

        this.socketIOServer = new SocketIOServer(config);
        return this.socketIOServer;
    }

    @PreDestroy
    public void stopServer() {
        if (socketIOServer != null) {
            socketIOServer.stop();
        }
    }
}
