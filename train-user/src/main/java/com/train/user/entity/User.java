package com.train.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * User entity
 */
@Data
@TableName("`user`")
public class User implements Serializable {
    
    private static final long serialVersionUID = 1L;
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String username;
    
    private String password;
    
    private String realName;
    
    private String idCard;
    
    private String phone;
    
    private String email;
    
    private Integer status; // 0: disabled, 1: enabled
    
    private Date createTime;
    
    private Date updateTime;
}
