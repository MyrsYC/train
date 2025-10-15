package com.train.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Train entity
 */
@Data
@TableName("train")
public class Train implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String trainCode; // Train number, e.g., G1234
    
    private String trainType; // Train type: G(高铁), D(动车), K(快速), T(特快), Z(直达)
    
    private String startStation;
    
    private String endStation;
    
    private Date departureTime;
    
    private Date arrivalTime;
    
    private Integer totalSeats;
    
    private Integer availableSeats;
    
    private BigDecimal priceFirstClass;
    
    private BigDecimal priceSecondClass;
    
    private BigDecimal priceBusinessClass;
    
    private Integer status; // 0: cancelled, 1: normal
    
    private Date createTime;
    
    private Date updateTime;
}
