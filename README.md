# 12306 Train Ticket Booking System (仿12306项目)

A train ticket booking system inspired by China Railway's 12306 platform, built with Spring Boot and MyBatis Plus.

## Project Structure

```
train-parent/
├── train-common/          # Common utilities and base classes
├── train-user/            # User service (registration, login, user management)
├── train-business/        # Business service (trains, tickets, orders)
└── train-gateway/         # API Gateway (future: routing and load balancing)
```

## Features

### User Service (Port: 8081)
- User registration with validation
- User login with JWT token authentication
- User information management
- Password encryption (MD5)

### Business Service (Port: 8082)
- Train schedule management
- Station information
- Ticket search and query
- Order creation and management
- Seat allocation
- Real-time seat availability

### Data Models
- **User**: User account information with ID card validation
- **Train**: Train schedules with multiple seat classes
- **Station**: Railway station information
- **Order**: Ticket booking orders

## Technology Stack

- **Framework**: Spring Boot 2.7.14
- **ORM**: MyBatis Plus 3.5.3.1
- **Database**: H2 (in-memory, for development)
- **Security**: JWT (JSON Web Token)
- **Validation**: Spring Validation
- **Utilities**: Hutool, FastJSON2
- **Build Tool**: Maven

## Quick Start

### Prerequisites
- JDK 1.8 or higher
- Maven 3.6+

### Build the Project

```bash
mvn clean install
```

### Run User Service

```bash
cd train-user
mvn spring-boot:run
```

The user service will start on `http://localhost:8081`

### Run Business Service

```bash
cd train-business
mvn spring-boot:run
```

The business service will start on `http://localhost:8082`

## API Documentation

### User Service APIs (Port 8081)

#### 1. Register User
```
POST /user/register
Content-Type: application/json

{
  "username": "zhangsan",
  "password": "123456",
  "realName": "张三",
  "idCard": "110101199001011234",
  "phone": "13800138000",
  "email": "zhangsan@example.com"
}
```

#### 2. Login
```
POST /user/login
Content-Type: application/json

{
  "username": "test",
  "password": "test"
}
```

Response:
```json
{
  "code": 200,
  "message": "Login successful",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "userId": 1,
    "username": "test",
    "realName": "测试用户"
  }
}
```

#### 3. Get User Info
```
GET /user/info/{userId}
```

### Business Service APIs (Port 8082)

#### 1. Query Trains
```
POST /train/query
Content-Type: application/json

{
  "startStation": "北京南站",
  "endStation": "上海虹桥站",
  "departureDate": "2025-10-20"
}
```

#### 2. Get All Trains
```
GET /train/list
```

#### 3. Create Order
```
POST /order/create
Content-Type: application/json

{
  "userId": 1,
  "trainId": 1,
  "passengerName": "张三",
  "idCard": "110101199001011234",
  "seatType": "SECOND"
}
```

#### 4. Get User Orders
```
GET /order/user/{userId}
```

## Test Data

### Test User
- Username: `test`
- Password: `test` (MD5: 098f6bcd4621d373cade4e832627b4f6)

### Test Trains
- G1: 北京南站 → 上海虹桥站 (08:00-13:28)
- G2: 上海虹桥站 → 北京南站 (09:00-14:35)
- G15: 北京南站 → 广州南站 (10:00-18:45)
- D101: 上海虹桥站 → 杭州东站 (07:30-08:30)
- G7001: 深圳北站 → 广州南站 (08:00-08:35)

## Seat Types

- **BUSINESS**: Business Class (商务座)
- **FIRST**: First Class (一等座)
- **SECOND**: Second Class (二等座)

## Database

The project uses H2 in-memory database for development. The H2 console is available at:
- User Service: `http://localhost:8081/h2-console`
- Business Service: `http://localhost:8082/h2-console`

Connection settings:
- JDBC URL: `jdbc:h2:mem:traindb`
- Username: `sa`
- Password: (empty)

## Future Enhancements

- [ ] Redis integration for caching and distributed locks
- [ ] Payment integration
- [ ] Ticket refund functionality
- [ ] Real-time seat selection
- [ ] SMS/Email notifications
- [ ] Admin panel for managing trains and stations
- [ ] Spring Cloud Gateway for API routing
- [ ] Docker containerization
- [ ] Message queue (RabbitMQ/Kafka) for async processing
- [ ] Monitoring and logging (ELK stack)

## License

This is a learning project inspired by 12306.
