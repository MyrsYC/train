package com.train.business.controller;

import com.train.business.dto.OrderCreateDTO;
import com.train.business.entity.Order;
import com.train.business.service.OrderService;
import com.train.common.response.Result;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Order controller
 */
@RestController
@RequestMapping("/order")
public class OrderController {
    
    @Resource
    private OrderService orderService;
    
    @PostMapping("/create")
    public Result<Order> createOrder(@RequestBody @Validated OrderCreateDTO dto) {
        Order order = orderService.createOrder(dto);
        return Result.success("Order created successfully", order);
    }
    
    @GetMapping("/user/{userId}")
    public Result<List<Order>> getUserOrders(@PathVariable Long userId) {
        List<Order> orders = orderService.getUserOrders(userId);
        return Result.success(orders);
    }
    
    @GetMapping("/{orderId}")
    public Result<Order> getOrderById(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return Result.success(order);
    }
}
