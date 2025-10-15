package com.train.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Order entity
 */
@Data
@TableName("train_order")
public class Order implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String orderNo;
    
    private Long userId;
    
    private Long trainId;
    
    private String trainCode;
    
    private String startStation;
    
    private String endStation;
    
    private Date departureTime;
    
    private String passengerName;
    
    private String idCard;
    
    private String seatType; // BUSINESS, FIRST, SECOND
    
    private String seatNumber;
    
    private BigDecimal price;
    
    private Integer status; // 0: cancelled, 1: pending payment, 2: paid, 3: completed
    
    private Date createTime;
    
    private Date updateTime;
}
