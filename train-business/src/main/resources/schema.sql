-- Station table
CREATE TABLE IF NOT EXISTS station (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    station_name VARCHAR(100) NOT NULL,
    station_code VARCHAR(20) NOT NULL UNIQUE,
    city_name VARCHAR(50) NOT NULL,
    province VARCHAR(50) NOT NULL,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Train table
CREATE TABLE IF NOT EXISTS train (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    train_code VARCHAR(20) NOT NULL UNIQUE,
    train_type VARCHAR(10) NOT NULL,
    start_station VARCHAR(100) NOT NULL,
    end_station VARCHAR(100) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    arrival_time TIMESTAMP NOT NULL,
    total_seats INT NOT NULL,
    available_seats INT NOT NULL,
    price_first_class DECIMAL(10,2),
    price_second_class DECIMAL(10,2),
    price_business_class DECIMAL(10,2),
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Order table
CREATE TABLE IF NOT EXISTS train_order (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_no VARCHAR(50) NOT NULL UNIQUE,
    user_id BIGINT NOT NULL,
    train_id BIGINT NOT NULL,
    train_code VARCHAR(20) NOT NULL,
    start_station VARCHAR(100) NOT NULL,
    end_station VARCHAR(100) NOT NULL,
    departure_time TIMESTAMP NOT NULL,
    passenger_name VARCHAR(50) NOT NULL,
    id_card VARCHAR(18) NOT NULL,
    seat_type VARCHAR(20) NOT NULL,
    seat_number VARCHAR(20) NOT NULL,
    price DECIMAL(10,2) NOT NULL,
    status INT DEFAULT 1,
    create_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    update_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Insert test stations
INSERT INTO station (station_name, station_code, city_name, province) VALUES
('北京南站', 'BJP', '北京', '北京'),
('上海虹桥站', 'SHH', '上海', '上海'),
('广州南站', 'GZN', '广州', '广东'),
('深圳北站', 'SZB', '深圳', '广东'),
('杭州东站', 'HZD', '杭州', '浙江'),
('南京南站', 'NJN', '南京', '江苏');

-- Insert test trains
INSERT INTO train (train_code, train_type, start_station, end_station, departure_time, arrival_time, total_seats, available_seats, price_first_class, price_second_class, price_business_class, status) VALUES
('G1', 'G', '北京南站', '上海虹桥站', '2025-10-20 08:00:00', '2025-10-20 13:28:00', 1000, 850, 933.00, 553.00, 1748.00, 1),
('G2', 'G', '上海虹桥站', '北京南站', '2025-10-20 09:00:00', '2025-10-20 14:35:00', 1000, 900, 933.00, 553.00, 1748.00, 1),
('G15', 'G', '北京南站', '广州南站', '2025-10-20 10:00:00', '2025-10-20 18:45:00', 1200, 1000, 1613.00, 862.00, 2727.00, 1),
('D101', 'D', '上海虹桥站', '杭州东站', '2025-10-20 07:30:00', '2025-10-20 08:30:00', 800, 650, 78.00, 48.00, 148.00, 1),
('G7001', 'G', '深圳北站', '广州南站', '2025-10-20 08:00:00', '2025-10-20 08:35:00', 600, 550, 99.50, 74.50, 188.00, 1);
