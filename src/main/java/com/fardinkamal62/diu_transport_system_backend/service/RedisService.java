package com.fardinkamal62.diu_transport_system_backend.service;

import com.fardinkamal62.diu_transport_system_backend.entities.Coordinate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class RedisService {
    private final RedisTemplate<String, Object> redisTemplate;
    private static final Logger logger = LoggerFactory.getLogger(RedisService.class);

    public RedisService(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void cacheLocationData(Coordinate data) {
        try {
            String key = "vehicle:" + data.getVehicleId();
            Map<String, String> map = new HashMap<>();
            map.put("latitude", String.valueOf(data.getLatitude()));
            map.put("longitude", String.valueOf(data.getLongitude()));
            map.put("vehicleId", data.getVehicleId());
            map.put("vehicleName", data.getVehicleName());

            redisTemplate.opsForHash().putAll(key, map);
        } catch (Exception e) {
            logger.error("Failed to cache location data", e);
            throw e;
        }
    }

    public void cacheLocationData(Coordinate data, long ttlSeconds) {
        cacheLocationData(data);
        redisTemplate.expire("vehicle:" + data.getVehicleId(), ttlSeconds, TimeUnit.SECONDS);
    }
}
