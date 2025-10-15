package com.train.business.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * Station entity
 */
@Data
@TableName("station")
public class Station implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String stationName;
    
    private String stationCode;
    
    private String cityName;
    
    private String province;
    
    private Date createTime;
    
    private Date updateTime;
}
