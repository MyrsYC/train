# API Documentation

## Overview

This document provides detailed API documentation for the 12306 Train Ticket Booking System.

## Base URLs

- **User Service**: `http://localhost:8081`
- **Business Service**: `http://localhost:8082`

## User Service APIs

### 1. User Registration

Register a new user account.

**Endpoint**: `POST /user/register`

**Request Body**:
```json
{
  "username": "zhangsan",
  "password": "123456",
  "realName": "张三",
  "idCard": "110101199001011234",
  "phone": "13800138001",
  "email": "zhangsan@example.com"
}
```

**Response**:
```json
{
  "code": 200,
  "message": "Registration successful",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9...",
    "userId": 2,
    "username": "zhangsan"
  }
}
```

**Validation Rules**:
- `username`: Required, not blank
- `password`: Required, not blank
- `realName`: Required, not blank
- `idCard`: Required, must match Chinese ID card format (18 digits)
- `phone`: Required, must match Chinese mobile format (11 digits starting with 1)
- `email`: Optional

### 2. User Login

Authenticate a user and receive a JWT token.

**Endpoint**: `POST /user/login`

**Request Body**:
```json
{
  "username": "test",
  "password": "test"
}
```

**Response**:
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

### 3. Get User Information

Retrieve user profile information.

**Endpoint**: `GET /user/info/{userId}`

**Path Parameters**:
- `userId`: User ID

**Response**:
```json
{
  "code": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "username": "test",
    "password": null,
    "realName": "测试用户",
    "idCard": "110101199001011234",
    "phone": "13800138000",
    "email": "test@example.com",
    "status": 1,
    "createTime": "2025-10-15T08:00:00.000+00:00",
    "updateTime": "2025-10-15T08:00:00.000+00:00"
  }
}
```

## Business Service APIs

### 1. Query Trains

Search for trains between two stations on a specific date.

**Endpoint**: `POST /train/query`

**Request Body**:
```json
{
  "startStation": "北京南站",
  "endStation": "上海虹桥站",
  "departureDate": "2025-10-20"
}
```

**Response**:
```json
{
  "code": 200,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "trainCode": "G1",
      "trainType": "G",
      "startStation": "北京南站",
      "endStation": "上海虹桥站",
      "departureTime": "2025-10-20T08:00:00.000+00:00",
      "arrivalTime": "2025-10-20T13:28:00.000+00:00",
      "totalSeats": 1000,
      "availableSeats": 850,
      "priceFirstClass": 933.0,
      "priceSecondClass": 553.0,
      "priceBusinessClass": 1748.0,
      "status": 1,
      "createTime": "2025-10-15T08:00:00.000+00:00",
      "updateTime": "2025-10-15T08:00:00.000+00:00"
    }
  ]
}
```

### 2. Get All Trains

Retrieve a list of all available trains.

**Endpoint**: `GET /train/list`

**Response**: Same format as Query Trains, returns all trains with status = 1.

### 3. Get Train by ID

Retrieve detailed information about a specific train.

**Endpoint**: `GET /train/{trainId}`

**Path Parameters**:
- `trainId`: Train ID

**Response**:
```json
{
  "code": 200,
  "message": "Success",
  "data": {
    "id": 1,
    "trainCode": "G1",
    "trainType": "G",
    "startStation": "北京南站",
    "endStation": "上海虹桥站",
    "departureTime": "2025-10-20T08:00:00.000+00:00",
    "arrivalTime": "2025-10-20T13:28:00.000+00:00",
    "totalSeats": 1000,
    "availableSeats": 850,
    "priceFirstClass": 933.0,
    "priceSecondClass": 553.0,
    "priceBusinessClass": 1748.0,
    "status": 1,
    "createTime": "2025-10-15T08:00:00.000+00:00",
    "updateTime": "2025-10-15T08:00:00.000+00:00"
  }
}
```

### 4. Create Order

Book a ticket for a train.

**Endpoint**: `POST /order/create`

**Request Body**:
```json
{
  "userId": 1,
  "trainId": 1,
  "passengerName": "张三",
  "idCard": "110101199001011234",
  "seatType": "SECOND"
}
```

**Seat Types**:
- `BUSINESS`: Business Class (商务座)
- `FIRST`: First Class (一等座)
- `SECOND`: Second Class (二等座)

**Response**:
```json
{
  "code": 200,
  "message": "Order created successfully",
  "data": {
    "id": 1,
    "orderNo": "ORD1760517539781fcfed1fa",
    "userId": 1,
    "trainId": 1,
    "trainCode": "G1",
    "startStation": "北京南站",
    "endStation": "上海虹桥站",
    "departureTime": "2025-10-20T08:00:00.000+00:00",
    "passengerName": "张三",
    "idCard": "110101199001011234",
    "seatType": "SECOND",
    "seatNumber": "08车07D",
    "price": 553.0,
    "status": 1,
    "createTime": "2025-10-15T08:38:59.781+00:00",
    "updateTime": "2025-10-15T08:38:59.781+00:00"
  }
}
```

**Order Status**:
- `0`: Cancelled
- `1`: Pending Payment
- `2`: Paid
- `3`: Completed

### 5. Get User Orders

Retrieve all orders for a specific user.

**Endpoint**: `GET /order/user/{userId}`

**Path Parameters**:
- `userId`: User ID

**Response**:
```json
{
  "code": 200,
  "message": "Success",
  "data": [
    {
      "id": 1,
      "orderNo": "ORD1760517539781fcfed1fa",
      "userId": 1,
      "trainId": 1,
      "trainCode": "G1",
      "startStation": "北京南站",
      "endStation": "上海虹桥站",
      "departureTime": "2025-10-20T08:00:00.000+00:00",
      "passengerName": "张三",
      "idCard": "110101199001011234",
      "seatType": "SECOND",
      "seatNumber": "08车07D",
      "price": 553.0,
      "status": 1,
      "createTime": "2025-10-15T08:38:59.781+00:00",
      "updateTime": "2025-10-15T08:38:59.781+00:00"
    }
  ]
}
```

### 6. Get Order by ID

Retrieve detailed information about a specific order.

**Endpoint**: `GET /order/{orderId}`

**Path Parameters**:
- `orderId`: Order ID

**Response**: Same format as individual order in Get User Orders.

## Error Handling

All APIs follow a unified error response format:

```json
{
  "code": 500,
  "message": "Error message",
  "data": null
}
```

**Common Error Codes**:
- `200`: Success
- `400`: Bad Request (validation errors)
- `404`: Not Found
- `500`: Internal Server Error

## Authentication

The system uses JWT (JSON Web Token) for authentication. After successful login or registration, the API returns a token that should be included in subsequent requests (future enhancement).

**Token Format**:
```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
```

## Testing with cURL

### Example 1: Register a new user
```bash
curl -X POST http://localhost:8081/user/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "lisi",
    "password": "123456",
    "realName": "李四",
    "idCard": "110101199001011235",
    "phone": "13800138002",
    "email": "lisi@example.com"
  }'
```

### Example 2: Login
```bash
curl -X POST http://localhost:8081/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test",
    "password": "test"
  }'
```

### Example 3: Query trains
```bash
curl -X POST http://localhost:8082/train/query \
  -H "Content-Type: application/json" \
  -d '{
    "startStation": "北京南站",
    "endStation": "上海虹桥站",
    "departureDate": "2025-10-20"
  }'
```

### Example 4: Create an order
```bash
curl -X POST http://localhost:8082/order/create \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "trainId": 1,
    "passengerName": "张三",
    "idCard": "110101199001011234",
    "seatType": "SECOND"
  }'
```

## Notes

1. **Date Format**: Use `yyyy-MM-dd` format for departure dates.
2. **Time Format**: Responses use ISO 8601 format with UTC timezone.
3. **Database**: The system uses H2 in-memory database. Data is reset on restart.
4. **Test Account**: Username: `test`, Password: `test`
5. **Seat Allocation**: Currently random. Future enhancement will support seat selection.
6. **Payment**: Not yet implemented. Orders are created in "Pending Payment" status.
