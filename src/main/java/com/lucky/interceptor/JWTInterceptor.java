package com.lucky.interceptor;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucky.utils.JWTUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 验证token的拦截器
 *
 * @Author 不会笑的人是怪物
 * @Date 2023年 02月 08日 16:29
 */

public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map = new HashMap<>();
        //获取请求头里的token
        String token = request.getHeader("token");
        try {
            // 验证token
            JWTUtils.verify(token);
            // 放行请求
            return true;
        } catch (SignatureVerificationException e) {
            // 签名不一致异常
            e.printStackTrace();
            map.put("msg", "无效签名");
        } catch (TokenExpiredException e) {
            //令牌过期异常
            e.printStackTrace();
            map.put("msg", "token已过期");
        } catch (AlgorithmMismatchException e) {
            //算法不匹配异常
            e.printStackTrace();
            map.put("msg", "token算法不一致");
        } catch (Exception e) {
            //监听总异常
            e.printStackTrace();
            map.put("msg", "token无效");
        }
        // 拦截请求
        map.put("state", false);  //设置状态
        // 将map转为json  --> jackson
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);
        return false;
    }
}
