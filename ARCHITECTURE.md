# System Architecture

## Overview

The 12306 Train Ticket Booking System is built using a microservices architecture with Spring Boot.

## Architecture Diagram

```
┌─────────────────────────────────────────────────────────────────┐
│                         Client Layer                            │
│  (Web Browser, Mobile App, API Testing Tools like Postman)     │
└────────────────────────────┬────────────────────────────────────┘
                             │
                             │ HTTP/REST
                             │
┌────────────────────────────▼────────────────────────────────────┐
│                    API Gateway (Optional)                       │
│                    Port: 8080                                   │
│              (Future: Routing, Load Balancing)                  │
└─────────────────┬───────────────────────┬───────────────────────┘
                  │                       │
         ┌────────▼────────┐     ┌───────▼────────┐
         │  User Service   │     │ Business Service│
         │   Port: 8081    │     │   Port: 8082    │
         └────────┬────────┘     └───────┬─────────┘
                  │                      │
        ┌─────────▼─────────┐  ┌─────────▼──────────┐
        │  User Management  │  │  Train Management  │
        │  - Registration   │  │  - Train Query     │
        │  - Login          │  │  - Schedule Mgmt   │
        │  - Authentication │  │  Order Management  │
        │  - User Info      │  │  - Ticket Booking  │
        └─────────┬─────────┘  │  - Order Tracking  │
                  │             └─────────┬──────────┘
                  │                       │
         ┌────────▼───────────────────────▼────────┐
         │          Common Module                   │
         │  - Result/Response DTOs                  │
         │  - Exception Handling                    │
         │  - JWT Utilities                         │
         │  - Base Classes                          │
         └────────┬─────────────────────────────────┘
                  │
         ┌────────▼─────────────────────────────────┐
         │          Data Layer                       │
         │  - MyBatis Plus (ORM)                     │
         │  - H2 Database (In-Memory)                │
         │  - Redis (Future: Caching)                │
         └───────────────────────────────────────────┘
```

## Module Structure

### 1. train-parent (Root Module)
- **Purpose**: Maven parent POM for dependency management
- **Type**: POM project
- **Responsibilities**:
  - Central dependency version management
  - Common build configuration
  - Multi-module orchestration

### 2. train-common (Common Module)
- **Purpose**: Shared utilities and base classes
- **Type**: JAR library
- **Key Components**:
  - `Result<T>`: Unified response wrapper
  - `PageResult<T>`: Pagination support
  - `BusinessException`: Custom exception class
  - `GlobalExceptionHandler`: Global error handling
  - `JwtUtil`: JWT token generation and validation
- **Dependencies**: Spring Web, Validation, FastJSON, Hutool, JWT

### 3. train-user (User Service)
- **Purpose**: User account management and authentication
- **Type**: Spring Boot application
- **Port**: 8081
- **Key Features**:
  - User registration with validation
  - User login with JWT tokens
  - Password encryption (MD5)
  - User profile management
- **API Endpoints**:
  - `POST /user/register`: Register new user
  - `POST /user/login`: User authentication
  - `GET /user/info/{userId}`: Get user information
- **Database Tables**:
  - `user`: User account information

### 4. train-business (Business Service)
- **Purpose**: Core business logic for train ticketing
- **Type**: Spring Boot application
- **Port**: 8082
- **Key Features**:
  - Train schedule management
  - Station information
  - Ticket search and filtering
  - Order creation and management
  - Seat allocation
- **API Endpoints**:
  - `POST /train/query`: Search trains by route and date
  - `GET /train/list`: Get all available trains
  - `GET /train/{trainId}`: Get train details
  - `POST /order/create`: Create ticket order
  - `GET /order/user/{userId}`: Get user orders
  - `GET /order/{orderId}`: Get order details
- **Database Tables**:
  - `station`: Railway station information
  - `train`: Train schedules and pricing
  - `train_order`: Ticket orders

### 5. train-gateway (Gateway Service)
- **Purpose**: API gateway for routing and load balancing
- **Type**: Spring Boot application
- **Port**: 8080
- **Status**: Basic skeleton (future enhancement)
- **Future Features**:
  - Request routing
  - Load balancing
  - Rate limiting
  - API authentication
  - Request/response logging

## Data Model

### Entity Relationship Diagram

```
┌─────────────────┐
│     User        │
│─────────────────│
│ id (PK)         │◄──────┐
│ username        │       │
│ password        │       │
│ real_name       │       │
│ id_card         │       │
│ phone           │       │
│ email           │       │
│ status          │       │
└─────────────────┘       │
                          │
                          │ user_id (FK)
                          │
                    ┌─────▼──────────┐
                    │  Order         │
                    │────────────────│
                    │ id (PK)        │
                    │ order_no       │
                    │ user_id (FK)   │───┐
                    │ train_id (FK)  │   │
                    │ passenger_name │   │
                    │ id_card        │   │
                    │ seat_type      │   │
                    │ seat_number    │   │
                    │ price          │   │
                    │ status         │   │
                    └────────────────┘   │
                                         │
                                         │ train_id (FK)
                                         │
                    ┌────────────────────▼───┐
                    │      Train             │
                    │────────────────────────│
                    │ id (PK)                │
                    │ train_code             │
                    │ train_type             │
                    │ start_station          │
                    │ end_station            │
                    │ departure_time         │
                    │ arrival_time           │
                    │ total_seats            │
                    │ available_seats        │
                    │ price_first_class      │
                    │ price_second_class     │
                    │ price_business_class   │
                    │ status                 │
                    └────────────────────────┘

┌─────────────────┐
│    Station      │
│─────────────────│
│ id (PK)         │
│ station_name    │
│ station_code    │
│ city_name       │
│ province        │
└─────────────────┘
```

## Technology Stack

### Backend Framework
- **Spring Boot 2.7.14**: Main application framework
- **Spring Web**: RESTful API development
- **Spring Data Redis**: Redis integration (configured, not yet used)
- **Spring Validation**: Input validation

### Data Access
- **MyBatis Plus 3.5.3.1**: ORM framework with enhanced features
- **H2 Database 2.1.214**: In-memory database (development)
- **MySQL Connector 8.0.33**: Production database support (configured)

### Utilities
- **Lombok**: Reduce boilerplate code
- **Hutool 5.8.18**: Java utility library
- **FastJSON 2.0.32**: JSON serialization
- **JJWT 0.9.1**: JWT token handling
- **JAXB API 2.3.1**: XML binding for Java 11+

### Build Tools
- **Maven 3.6+**: Project build and dependency management
- **JDK 1.8+**: Java runtime (compatible with Java 17)

## Security

### Authentication
- **JWT Tokens**: Stateless authentication
- **Token Expiration**: 7 days
- **Password Encryption**: MD5 hashing

### Validation
- **Bean Validation**: Request DTO validation
- **ID Card Format**: Chinese ID card validation (18 digits)
- **Phone Format**: Chinese mobile number validation (11 digits)

## Performance Considerations

### Database
- **Connection Pooling**: HikariCP (default in Spring Boot)
- **MyBatis Plus**: Optimized SQL generation
- **Indexes**: Primary keys and unique constraints

### Future Optimizations
- **Redis Caching**: Cache frequently accessed data
- **Database Indexes**: Add indexes on query fields
- **Connection Pool Tuning**: Adjust pool size based on load
- **Async Processing**: Use message queues for order processing

## Scalability

### Current Design
- **Microservices**: Independent services can be scaled separately
- **Stateless**: JWT tokens enable horizontal scaling
- **Database**: Single H2 instance (development only)

### Future Enhancements
- **Load Balancer**: Nginx or Spring Cloud Gateway
- **Service Discovery**: Eureka or Consul
- **Distributed Database**: MySQL cluster or sharding
- **Cache Layer**: Redis cluster
- **Message Queue**: RabbitMQ or Kafka for async operations
- **Distributed Locks**: Redis or ZooKeeper for seat locking

## Deployment

### Development
```bash
# Start User Service
cd train-user && mvn spring-boot:run

# Start Business Service (in another terminal)
cd train-business && mvn spring-boot:run
```

### Production (Future)
- **Containerization**: Docker images for each service
- **Orchestration**: Kubernetes or Docker Compose
- **CI/CD**: GitHub Actions or Jenkins
- **Monitoring**: Prometheus + Grafana
- **Logging**: ELK Stack (Elasticsearch, Logstash, Kibana)

## API Design Principles

### RESTful Architecture
- **Resources**: Users, Trains, Orders
- **HTTP Methods**: GET (read), POST (create), PUT (update), DELETE (remove)
- **Status Codes**: 200 (success), 400 (bad request), 404 (not found), 500 (error)

### Unified Response Format
All APIs return a consistent response structure:
```json
{
  "code": 200,
  "message": "Success message",
  "data": { /* response data */ }
}
```

### Error Handling
- **Global Exception Handler**: Catches all exceptions
- **Business Exceptions**: Custom error messages
- **Validation Errors**: Detailed field-level errors

## Testing Strategy

### Unit Tests
- Service layer business logic
- Utility classes
- Validation rules

### Integration Tests
- API endpoint testing
- Database operations
- End-to-end workflows

### Manual Testing
- Postman collections
- cURL commands
- Web browser testing

## Future Roadmap

### Phase 1 (Current)
- ✅ Basic user management
- ✅ Train query and listing
- ✅ Order creation
- ✅ In-memory database

### Phase 2
- [ ] Redis caching
- [ ] Real-time seat selection
- [ ] Payment integration
- [ ] Order cancellation
- [ ] Ticket refund

### Phase 3
- [ ] Admin dashboard
- [ ] Train schedule management UI
- [ ] Real-time notifications
- [ ] Email/SMS alerts

### Phase 4
- [ ] Performance optimization
- [ ] Load testing
- [ ] Production deployment
- [ ] Monitoring and alerting

## Best Practices

### Code Quality
- **Separation of Concerns**: Controller → Service → Mapper layers
- **DRY Principle**: Common code in shared modules
- **Error Handling**: Consistent exception handling
- **Documentation**: Comprehensive API docs

### Security
- **Input Validation**: All user inputs validated
- **SQL Injection Prevention**: MyBatis parameterized queries
- **Password Security**: Never store plain text passwords
- **Token Expiration**: JWT tokens expire after 7 days

### Performance
- **Efficient Queries**: Select only needed fields
- **Pagination**: Avoid loading all records
- **Caching**: Cache static data (future)
- **Connection Pooling**: Reuse database connections
