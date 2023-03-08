package com.lucky.controller;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.lucky.entity.User;
import com.lucky.service.UserService;
import com.lucky.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author 不会笑的人是怪物
 * @Date 2023年 02月 08日 15:34
 */

@Slf4j
@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/login")
    public Map<String, Object> login(User user) {
        log.info("用户名：[{}]", user.getName());
        log.info("密码：[{}]", user.getPassword());
        Map<String, Object> map = new HashMap<>();
        try {
            User userDB = userService.login(user);
            // 设置payload
            Map<String, String> payload = new HashMap<>();
            payload.put("id", userDB.getId());
            payload.put("name", userDB.getName());
            // 生成JWT令牌
            String token = JWTUtils.getToken(payload);
            map.put("state", true);
            map.put("msg", "登录成功");
            // 响应token
            map.put("token", token);
        } catch (Exception e) {
            map.put("state", false);
            map.put("msg", e.getMessage());
        }
        return map;
    }

    @PostMapping("/user/test")
    public Map<String,Object> test(HttpServletRequest request){
        Map<String, Object> map = new HashMap<>();
        //处理自己的业务逻辑

        //获取数据
        String token =request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        log.info("用户id：[{}]",verify.getClaim("id").asString());
        log.info("用户名：[{}]",verify.getClaim("name").asString());

        map.put("state", true);
        map.put("msg", "请求成功");

        return map;
    }
}
