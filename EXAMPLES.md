# Usage Examples

This guide provides practical examples of how to use the 12306 Train Ticket Booking System.

## Scenario 1: New User Registration and First Booking

### Step 1: Register a new account

```bash
curl -X POST http://localhost:8081/user/register \
  -H "Content-Type: application/json" \
  -d '{
    "username": "wangwu",
    "password": "abc123",
    "realName": "王五",
    "idCard": "110101199001011236",
    "phone": "13800138003",
    "email": "wangwu@example.com"
  }'
```

**Response**:
```json
{
  "code": 200,
  "message": "Registration successful",
  "data": {
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3NjExMjIxNzAsInVzZXJJZCI6MywiaWF0IjoxNzYwNTE3MzcwLCJ1c2VybmFtZSI6Indhbmd3dSJ9.xxx",
    "userId": 3,
    "username": "wangwu"
  }
}
```

Note the `userId` (3) for later use.

### Step 2: Search for available trains

```bash
curl -X POST http://localhost:8082/train/query \
  -H "Content-Type: application/json" \
  -d '{
    "startStation": "北京南站",
    "endStation": "上海虹桥站",
    "departureDate": "2025-10-20"
  }'
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
      "availableSeats": 850,
      "priceFirstClass": 933.0,
      "priceSecondClass": 553.0,
      "priceBusinessClass": 1748.0
    }
  ]
}
```

Note the `id` (1) of the desired train.

### Step 3: Book a ticket

```bash
curl -X POST http://localhost:8082/order/create \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 3,
    "trainId": 1,
    "passengerName": "王五",
    "idCard": "110101199001011236",
    "seatType": "SECOND"
  }'
```

**Response**:
```json
{
  "code": 200,
  "message": "Order created successfully",
  "data": {
    "id": 2,
    "orderNo": "ORD1760517600123abc789ef",
    "userId": 3,
    "trainId": 1,
    "trainCode": "G1",
    "startStation": "北京南站",
    "endStation": "上海虹桥站",
    "departureTime": "2025-10-20T08:00:00.000+00:00",
    "passengerName": "王五",
    "idCard": "110101199001011236",
    "seatType": "SECOND",
    "seatNumber": "05车12B",
    "price": 553.0,
    "status": 1
  }
}
```

## Scenario 2: Returning User Books Multiple Tickets

### Step 1: Login

```bash
curl -X POST http://localhost:8081/user/login \
  -H "Content-Type: application/json" \
  -d '{
    "username": "test",
    "password": "test"
  }'
```

### Step 2: View all available trains

```bash
curl -X GET http://localhost:8082/train/list
```

### Step 3: Book first class seat

```bash
curl -X POST http://localhost:8082/order/create \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "trainId": 2,
    "passengerName": "测试用户",
    "idCard": "110101199001011234",
    "seatType": "FIRST"
  }'
```

### Step 4: Book business class seat

```bash
curl -X POST http://localhost:8082/order/create \
  -H "Content-Type: application/json" \
  -d '{
    "userId": 1,
    "trainId": 3,
    "passengerName": "测试用户",
    "idCard": "110101199001011234",
    "seatType": "BUSINESS"
  }'
```

### Step 5: View all my orders

```bash
curl -X GET http://localhost:8082/order/user/1
```

**Response**:
```json
{
  "code": 200,
  "message": "Success",
  "data": [
    {
      "id": 3,
      "orderNo": "ORD1760517610456def123gh",
      "trainCode": "G2",
      "seatType": "FIRST",
      "price": 933.0,
      "status": 1
    },
    {
      "id": 4,
      "orderNo": "ORD1760517620789hij456kl",
      "trainCode": "G15",
      "seatType": "BUSINESS",
      "price": 2727.0,
      "status": 1
    }
  ]
}
```

## Scenario 3: Comparing Different Routes

### Search Beijing to Guangzhou

```bash
curl -X POST http://localhost:8082/train/query \
  -H "Content-Type: application/json" \
  -d '{
    "startStation": "北京南站",
    "endStation": "广州南站",
    "departureDate": "2025-10-20"
  }'
```

### Search Shenzhen to Guangzhou (short distance)

```bash
curl -X POST http://localhost:8082/train/query \
  -H "Content-Type: application/json" \
  -d '{
    "startStation": "深圳北站",
    "endStation": "广州南站",
    "departureDate": "2025-10-20"
  }'
```

## Scenario 4: Get Detailed Order Information

### Get specific order details

```bash
curl -X GET http://localhost:8082/order/5
```

**Response**:
```json
{
  "code": 200,
  "message": "Success",
  "data": {
    "id": 5,
    "orderNo": "ORD1760517630123mno789pq",
    "userId": 1,
    "trainId": 4,
    "trainCode": "D101",
    "startStation": "上海虹桥站",
    "endStation": "杭州东站",
    "departureTime": "2025-10-20T07:30:00.000+00:00",
    "passengerName": "测试用户",
    "idCard": "110101199001011234",
    "seatType": "SECOND",
    "seatNumber": "03车15C",
    "price": 48.0,
    "status": 1,
    "createTime": "2025-10-15T09:00:30.123+00:00",
    "updateTime": "2025-10-15T09:00:30.123+00:00"
  }
}
```

## Python Example

Here's a complete Python script to automate the booking process:

```python
import requests
import json

BASE_USER_URL = "http://localhost:8081"
BASE_BUSINESS_URL = "http://localhost:8082"

def register_user(username, password, real_name, id_card, phone, email):
    """Register a new user"""
    url = f"{BASE_USER_URL}/user/register"
    data = {
        "username": username,
        "password": password,
        "realName": real_name,
        "idCard": id_card,
        "phone": phone,
        "email": email
    }
    response = requests.post(url, json=data)
    return response.json()

def login(username, password):
    """Login and get token"""
    url = f"{BASE_USER_URL}/user/login"
    data = {"username": username, "password": password}
    response = requests.post(url, json=data)
    return response.json()

def query_trains(start_station, end_station, departure_date):
    """Search for trains"""
    url = f"{BASE_BUSINESS_URL}/train/query"
    data = {
        "startStation": start_station,
        "endStation": end_station,
        "departureDate": departure_date
    }
    response = requests.post(url, json=data)
    return response.json()

def create_order(user_id, train_id, passenger_name, id_card, seat_type):
    """Create a ticket order"""
    url = f"{BASE_BUSINESS_URL}/order/create"
    data = {
        "userId": user_id,
        "trainId": train_id,
        "passengerName": passenger_name,
        "idCard": id_card,
        "seatType": seat_type
    }
    response = requests.post(url, json=data)
    return response.json()

def get_user_orders(user_id):
    """Get all orders for a user"""
    url = f"{BASE_BUSINESS_URL}/order/user/{user_id}"
    response = requests.get(url)
    return response.json()

# Example usage
if __name__ == "__main__":
    # Login
    login_result = login("test", "test")
    print("Login:", json.dumps(login_result, indent=2, ensure_ascii=False))
    user_id = login_result["data"]["userId"]
    
    # Search trains
    trains = query_trains("北京南站", "上海虹桥站", "2025-10-20")
    print("\nAvailable trains:", json.dumps(trains, indent=2, ensure_ascii=False))
    
    if trains["data"]:
        train_id = trains["data"][0]["id"]
        
        # Book a ticket
        order = create_order(user_id, train_id, "测试用户", "110101199001011234", "SECOND")
        print("\nOrder created:", json.dumps(order, indent=2, ensure_ascii=False))
        
        # View all orders
        my_orders = get_user_orders(user_id)
        print("\nMy orders:", json.dumps(my_orders, indent=2, ensure_ascii=False))
```

## JavaScript/Node.js Example

```javascript
const axios = require('axios');

const BASE_USER_URL = 'http://localhost:8081';
const BASE_BUSINESS_URL = 'http://localhost:8082';

async function login(username, password) {
  const response = await axios.post(`${BASE_USER_URL}/user/login`, {
    username,
    password
  });
  return response.data;
}

async function queryTrains(startStation, endStation, departureDate) {
  const response = await axios.post(`${BASE_BUSINESS_URL}/train/query`, {
    startStation,
    endStation,
    departureDate
  });
  return response.data;
}

async function createOrder(userId, trainId, passengerName, idCard, seatType) {
  const response = await axios.post(`${BASE_BUSINESS_URL}/order/create`, {
    userId,
    trainId,
    passengerName,
    idCard,
    seatType
  });
  return response.data;
}

async function main() {
  try {
    // Login
    const loginResult = await login('test', 'test');
    console.log('Login:', JSON.stringify(loginResult, null, 2));
    
    const userId = loginResult.data.userId;
    
    // Search trains
    const trains = await queryTrains('北京南站', '上海虹桥站', '2025-10-20');
    console.log('Available trains:', JSON.stringify(trains, null, 2));
    
    if (trains.data && trains.data.length > 0) {
      const trainId = trains.data[0].id;
      
      // Book ticket
      const order = await createOrder(userId, trainId, '测试用户', '110101199001011234', 'SECOND');
      console.log('Order created:', JSON.stringify(order, null, 2));
    }
  } catch (error) {
    console.error('Error:', error.message);
  }
}

main();
```

## Common Operations

### Check if username exists (will fail if exists)
```bash
curl -X POST http://localhost:8081/user/register \
  -H "Content-Type: application/json" \
  -d '{"username":"test","password":"test","realName":"测试","idCard":"110101199001011234","phone":"13800138000"}'
```

Expected response:
```json
{
  "code": 500,
  "message": "Username already exists",
  "data": null
}
```

### Invalid ID card format
```bash
curl -X POST http://localhost:8081/user/register \
  -H "Content-Type: application/json" \
  -d '{"username":"invalid","password":"test","realName":"测试","idCard":"123","phone":"13800138000"}'
```

Expected response:
```json
{
  "code": 500,
  "message": "Invalid ID card format",
  "data": null
}
```

### Booking with no available seats
If a train is fully booked, you'll get an error:
```json
{
  "code": 500,
  "message": "No available seats",
  "data": null
}
```

## Tips

1. **Test Data**: Use the pre-configured test account (`test`/`test`) for quick testing
2. **Date Format**: Always use `yyyy-MM-dd` format for dates
3. **Station Names**: Use full Chinese station names (e.g., `北京南站`, not `北京`)
4. **Seat Types**: Choose from `BUSINESS`, `FIRST`, or `SECOND` (case-insensitive)
5. **User IDs**: Keep track of user IDs from registration/login responses
6. **Train IDs**: Get from the train query or list responses
