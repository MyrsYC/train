package com.train.business.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.train.business.dto.OrderCreateDTO;
import com.train.business.entity.Order;
import com.train.business.entity.Train;
import com.train.business.mapper.OrderMapper;
import com.train.business.mapper.TrainMapper;
import com.train.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Order service
 */
@Slf4j
@Service
public class OrderService {
    
    @Resource
    private OrderMapper orderMapper;
    
    @Resource
    private TrainMapper trainMapper;
    
    @Transactional(rollbackFor = Exception.class)
    public Order createOrder(OrderCreateDTO dto) {
        // Get train info
        Train train = trainMapper.selectById(dto.getTrainId());
        if (train == null) {
            throw new BusinessException("Train does not exist");
        }
        
        if (train.getAvailableSeats() <= 0) {
            throw new BusinessException("No available seats");
        }
        
        // Get price based on seat type
        BigDecimal price;
        switch (dto.getSeatType().toUpperCase()) {
            case "BUSINESS":
                price = train.getPriceBusinessClass();
                break;
            case "FIRST":
                price = train.getPriceFirstClass();
                break;
            case "SECOND":
                price = train.getPriceSecondClass();
                break;
            default:
                throw new BusinessException("Invalid seat type");
        }
        
        // Create order
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(dto.getUserId());
        order.setTrainId(train.getId());
        order.setTrainCode(train.getTrainCode());
        order.setStartStation(train.getStartStation());
        order.setEndStation(train.getEndStation());
        order.setDepartureTime(train.getDepartureTime());
        order.setPassengerName(dto.getPassengerName());
        order.setIdCard(dto.getIdCard());
        order.setSeatType(dto.getSeatType().toUpperCase());
        order.setSeatNumber(generateSeatNumber(dto.getSeatType()));
        order.setPrice(price);
        order.setStatus(1); // Pending payment
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        
        orderMapper.insert(order);
        
        // Update available seats
        train.setAvailableSeats(train.getAvailableSeats() - 1);
        trainMapper.updateById(train);
        
        return order;
    }
    
    public List<Order> getUserOrders(Long userId) {
        LambdaQueryWrapper<Order> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Order::getUserId, userId)
               .orderByDesc(Order::getCreateTime);
        return orderMapper.selectList(wrapper);
    }
    
    public Order getOrderById(Long orderId) {
        return orderMapper.selectById(orderId);
    }
    
    private String generateOrderNo() {
        return "ORD" + System.currentTimeMillis() + UUID.randomUUID().toString().substring(0, 8);
    }
    
    private String generateSeatNumber(String seatType) {
        int carriage = (int)(Math.random() * 10) + 1;
        int row = (int)(Math.random() * 20) + 1;
        char seat = (char)('A' + (int)(Math.random() * 5));
        return String.format("%02d车%02d%c", carriage, row, seat);
    }
}
