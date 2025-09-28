```
$$$$$$$\  $$$$$$\ $$\   $$\       $$$$$$$$\                                                                    $$\            $$$$$$\                        $$\
$$  __$$\ \_$$  _|$$ |  $$ |      \__$$  __|                                                                   $$ |          $$  __$$\                       $$ |
$$ |  $$ |  $$ |  $$ |  $$ |         $$ | $$$$$$\  $$$$$$\  $$$$$$$\   $$$$$$$\  $$$$$$\   $$$$$$\   $$$$$$\ $$$$$$\         $$ /  \__|$$\   $$\  $$$$$$$\ $$$$$$\    $$$$$$\  $$$$$$\$$$$\
$$ |  $$ |  $$ |  $$ |  $$ |         $$ |$$  __$$\ \____$$\ $$  __$$\ $$  _____|$$  __$$\ $$  __$$\ $$  __$$\\_$$  _|        \$$$$$$\  $$ |  $$ |$$  _____|\_$$  _|  $$  __$$\ $$  _$$  _$$\
$$ |  $$ |  $$ |  $$ |  $$ |         $$ |$$ |  \__|$$$$$$$ |$$ |  $$ |\$$$$$$\  $$ /  $$ |$$ /  $$ |$$ |  \__| $$ |           \____$$\ $$ |  $$ |\$$$$$$\    $$ |    $$$$$$$$ |$$ / $$ / $$ |
$$ |  $$ |  $$ |  $$ |  $$ |         $$ |$$ |     $$  __$$ |$$ |  $$ | \____$$\ $$ |  $$ |$$ |  $$ |$$ |       $$ |$$\       $$\   $$ |$$ |  $$ | \____$$\   $$ |$$\ $$   ____|$$ | $$ | $$ |
$$$$$$$  |$$$$$$\ \$$$$$$  |         $$ |$$ |     \$$$$$$$ |$$ |  $$ |$$$$$$$  |$$$$$$$  |\$$$$$$  |$$ |       \$$$$  |      \$$$$$$  |\$$$$$$$ |$$$$$$$  |  \$$$$  |\$$$$$$$\ $$ | $$ | $$ |
\_______/ \______| \______/          \__|\__|      \_______|\__|  \__|\_______/ $$  ____/  \______/ \__|        \____/        \______/  \____$$ |\_______/    \____/  \_______|\__| \__| \__|
                                                                                $$ |                                                   $$\   $$ |
                                                                                $$ |                                                   \$$$$$$  |
                                                                                \__|                                                    \______/
```

# DIU Transport System Backend

Version: 0.0.1-SNAPSHOT

## 🌟 Project Overview

This is the Spring Boot implementation of the [DIU Transport App project](https://github.com/fardinkamal62/diu_transport_app)'s Node.JS backend.

DIU Transport App is a mobile application that provides information about the transportation services available and location of the vehicles in real-time.

## 🔧 Tech Stack
- **Language:** Java 25
- **Backend Framework:** Spring Boot
- **Build Tool:** Maven
- **Database:** PostgreSQL, Redis
- **WebSocket:** Socket.IO (via netty-socketio)
- **Security:** Spring Security

## 📋 Prerequisites

### Development Environment
- **Java Development Kit (JDK):** 25
- **Maven:** 3.9.9
- **PostgreSQL:** 14+
- **Redis:** 6+

### Installation Steps

#### 1. Clone the Repository
```bash
git clone https://github.com/fardinkamal62/diu_transport_system_backend.git
```

#### 2. Navigate to the Project Directory
```bash
cd diu_transport_system_backend
```

#### 3. Configure the Database
- Install and set up PostgreSQL and Redis on your local machine or server.
- Create a new PostgreSQL database for the application.
- Start the Redis server.
- Add the necessary environment variables (see Environment Variables section below).

#### 4. Build the Project
```bash
mvn clean install
```

#### 5. Run the Application
```bash
mvn spring-boot:run
```

## 🚀 Running the Application
After completing the installation steps, the application should be running on [http://localhost:8080](http://localhost:8080) with the Socket.IO server running on port 8085.

## 📡 Socket.IO Events

The Socket.IO server operates on port 8085 (default configuration).

### Client → Server Events

| Event Name | Payload                                      | Description                           |
|------------|--------------------------------------------|---------------------------------------|
| `location` | `{ latitude, longitude, vehicleId, vehicleName }` | Send vehicle location updates          |


### Server → Client Events

| Event Name | Payload                                      | Description                           |
|------------|--------------------------------------------|---------------------------------------|
| `location` | `{ latitude, longitude, vehicleId, vehicleName }` | Broadcast vehicle location to clients  |
| `error`    | `{ message }`                              | Error messages                        |

## 🔒 Environment Variables

Required environment variables:

```
DB_PASSWORD=strong-password
DB_URL=jdbc:postgresql://localhost:5432/diu_transport_system
DB_USERNAME=postgres
REDIS_HOST=localhost
REDIS_PORT=6379
```

## 📂 Project Structure

```
src/main/java/com/fardinkamal62/diu_transport_system_backend/
├── config                  # Configuration classes
│   ├── RedisConfig.java    # Redis connection configuration
│   ├── SecurityConfig.java # Spring Security configuration
│   └── WebConfig.java      # CORS and other web configurations
├── dto                     # Data Transfer Objects
│   └── ErrorResponseDto.java   # Error response structure
├── entities                # Domain models/entities
│   ├── Coordinate.java     # Location coordinate model
├── service                 # Business logic
│   └── RedisService.java   # Redis operations
└── socket                  # Socket.IO related components
    ├── MessageData.java    # Socket message structure
    ├── SocketEventHandler.java  # Socket event handlers
    └── config
        └── SocketConfig.java  # Socket server configuration
```

## ⚠️ Troubleshooting

### Common Issues

1. **Connection to Redis Failed**
   - Ensure Redis server is running
   - Verify REDIS_HOST and REDIS_PORT environment variables are correct

2. **Database Connection Issues**
   - Check PostgreSQL is running
   - Verify DB_URL, DB_USERNAME, and DB_PASSWORD are correct

3. **Socket.IO Connection Problems**
   - Ensure port 8085 is not in use by another application
   - Check firewall settings if connecting from external clients
   - Check the URL in the client application matches the server's Socket.IO URL

## 📜 License

Check the [LICENSE](LICENSE) file for license information.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.
