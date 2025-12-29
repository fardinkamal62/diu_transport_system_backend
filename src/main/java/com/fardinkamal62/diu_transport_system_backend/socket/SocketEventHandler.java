package com.fardinkamal62.diu_transport_system_backend.socket;

import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.fardinkamal62.diu_transport_system_backend.dtos.ErrorResponseDto;
import com.fardinkamal62.diu_transport_system_backend.entities.Coordinate;
import com.fardinkamal62.diu_transport_system_backend.services.RedisService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import java.util.Set;

@Component
public class SocketEventHandler {

    private final SocketIOServer socketIOServer;
    private final RedisService redisService;
    private final Validator validator;
    private static final Logger logger = LoggerFactory.getLogger(SocketEventHandler.class);

    public SocketEventHandler(SocketIOServer socketIOServer, RedisService redisService, Validator validator) {
        this.socketIOServer = socketIOServer;
        this.redisService = redisService;
        this.validator = validator;
    }

    @PostConstruct
    public void registerListeners() {
        socketIOServer.addConnectListener(onConnected());
        socketIOServer.addDisconnectListener(onDisconnected());
        socketIOServer.addEventListener("location", Coordinate.class, onLocationReceived());
    }

    private ConnectListener onConnected() {
        return client -> {
            String sessionId = client.getSessionId().toString();
            logger.info("Client connected: {}", sessionId);
        };
    }

    private DisconnectListener onDisconnected() {
        return client -> {
            String sessionId = client.getSessionId().toString();
            logger.info("Client disconnected: {}", sessionId);
        };
    }

    private DataListener<Coordinate> onLocationReceived() {
        return (client, data, ackRequest) -> {
            String sessionId = client.getSessionId().toString();

            // Validate coordinate data using Jakarta validation
            Set<ConstraintViolation<Coordinate>> violations = validator.validate(data);
            if (!violations.isEmpty()) {
                String errorMsg = violations.iterator().next().getMessage();
                logger.error("Invalid location data received from {}: {}", sessionId, errorMsg);
                ErrorResponseDto error = ErrorResponseDto.builder()
                        .message("Invalid location data")
                        .status(400)
                        .timestamp(java.time.LocalDateTime.now())
                        .build();
                client.sendEvent("error", error);
                return;
            }

            logger.info("Location received from {}: vehicle={}, lat={}, lng={}",
                    sessionId, data.getVehicleId(), data.getLatitude(), data.getLongitude());

            try {
                redisService.cacheLocationData(data);

                socketIOServer.getBroadcastOperations().sendEvent("location", data);

                if (ackRequest.isAckRequested()) {
                    ackRequest.sendAckData("Location updated successfully");
                }
            } catch (Exception e) {
                logger.error("Failed to process location update", e);
                ErrorResponseDto error = ErrorResponseDto.builder()
                        .message("Failed to update location")
                        .status(500)
                        .timestamp(java.time.LocalDateTime.now())
                        .build();
                client.sendEvent("error", error);
            }
        };
    }
}
