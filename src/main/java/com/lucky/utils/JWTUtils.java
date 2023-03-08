package com.lucky.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * JWT工具类
 *
 * @Author 不会笑的人是怪物
 * @Date 2023年 02月 08日 14:31
 */

public class JWTUtils {
    //设置签名
    public static String SIGNATURE = "token@xl-jj-my-98-r";

    /***
     * 生成token
     * @param map 传入payload
     * @return
     */
    public static String getToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        // 默认7天过期
        instance.add(Calendar.DATE, 7);
        // 创建jwt builder
        JWTCreator.Builder builder = JWT.create();
        // payload
        map.forEach((k, v) -> {
            builder.withClaim(k, v);
        });
        // 指定过期时间
        builder.withExpiresAt(instance.getTime());
        // 返回token令牌
        return builder.sign(Algorithm.HMAC256(SIGNATURE)).toString();
    }

    /**
     * 验证token
     *
     * @param token
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SIGNATURE)).build().verify(token);
    }
}
