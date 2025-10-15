package com.train.user.controller;

import com.train.common.response.Result;
import com.train.user.dto.UserLoginDTO;
import com.train.user.dto.UserRegisterDTO;
import com.train.user.entity.User;
import com.train.user.service.UserService;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Map;

/**
 * User controller
 */
@RestController
@RequestMapping("/user")
public class UserController {
    
    @Resource
    private UserService userService;
    
    @PostMapping("/register")
    public Result<Map<String, Object>> register(@RequestBody @Validated UserRegisterDTO dto) {
        Map<String, Object> result = userService.register(dto);
        return Result.success("Registration successful", result);
    }
    
    @PostMapping("/login")
    public Result<Map<String, Object>> login(@RequestBody @Validated UserLoginDTO dto) {
        Map<String, Object> result = userService.login(dto);
        return Result.success("Login successful", result);
    }
    
    @GetMapping("/info/{userId}")
    public Result<User> getUserInfo(@PathVariable Long userId) {
        User user = userService.getUserInfo(userId);
        return Result.success(user);
    }
}
