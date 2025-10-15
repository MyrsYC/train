package com.train.user.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.train.common.exception.BusinessException;
import com.train.common.utils.JwtUtil;
import com.train.user.dto.UserLoginDTO;
import com.train.user.dto.UserRegisterDTO;
import com.train.user.entity.User;
import com.train.user.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * User service
 */
@Slf4j
@Service
public class UserService {
    
    @Resource
    private UserMapper userMapper;
    
    public Map<String, Object> register(UserRegisterDTO dto) {
        // Check if username exists
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        User existUser = userMapper.selectOne(wrapper);
        if (existUser != null) {
            throw new BusinessException("Username already exists");
        }
        
        // Create new user
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        user.setPassword(DigestUtils.md5DigestAsHex(dto.getPassword().getBytes()));
        user.setStatus(1);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        
        userMapper.insert(user);
        
        // Generate token
        String token = JwtUtil.generateToken(user.getId(), user.getUsername());
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userId", user.getId());
        result.put("username", user.getUsername());
        
        return result;
    }
    
    public Map<String, Object> login(UserLoginDTO dto) {
        // Find user by username
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUsername, dto.getUsername());
        User user = userMapper.selectOne(wrapper);
        
        if (user == null) {
            throw new BusinessException("User does not exist");
        }
        
        // Check password
        String encryptedPassword = DigestUtils.md5DigestAsHex(dto.getPassword().getBytes());
        if (!encryptedPassword.equals(user.getPassword())) {
            throw new BusinessException("Incorrect password");
        }
        
        // Check status
        if (user.getStatus() == 0) {
            throw new BusinessException("User account is disabled");
        }
        
        // Generate token
        String token = JwtUtil.generateToken(user.getId(), user.getUsername());
        
        Map<String, Object> result = new HashMap<>();
        result.put("token", token);
        result.put("userId", user.getId());
        result.put("username", user.getUsername());
        result.put("realName", user.getRealName());
        
        return result;
    }
    
    public User getUserInfo(Long userId) {
        User user = userMapper.selectById(userId);
        if (user == null) {
            throw new BusinessException("User does not exist");
        }
        // Clear sensitive information
        user.setPassword(null);
        return user;
    }
}
