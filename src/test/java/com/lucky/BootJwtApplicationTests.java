package com.lucky;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Verification;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

@SpringBootTest
class BootJwtApplicationTests {

    @Test
    void contextLoads() {
        //设置jwt的header信息
        HashMap<String, Object> map = new HashMap<>();

        //设置过期时间为 20s
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.SECOND, 20);

        //设置签名
        String signature = "xl-jj-my-98-r";
        //生成token
        String token = JWT.create()
                .withHeader(map) //header，可不设置
                .withClaim("id", 521) //payload
                .withClaim("name", "小辣鸡")
                .withClaim("sex", "女")
                .withExpiresAt(instance.getTime()) //指定令牌过期时间
                .sign(Algorithm.HMAC256(signature)); //签名

        System.out.println("token为：" + token);
    }


    //验证token
    @Test
    public void test() {
        //设置签名
        String signature = "xl-jj-my-98-r";
        //创建验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(signature)).build();

        DecodedJWT verify = jwtVerifier.verify("eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJzZXgiOiLlpbMiLCJuYW1lIjoi5bCP6L6j6bihIiwiaWQiOjUyMSwiZXhwIjoxNjc1ODM3MjU5fQ.sjNwbkH6gNnsiEi3sLRng6N3R65QyP9TaXGSkfdfI8A");

        List list = new ArrayList<>();
        list.add(verify.getClaim("id").asInt());  //id
        list.add(verify.getClaim("name").asString());  //姓名
        list.add(verify.getClaim("sex").asString());  //性别
        list.add(verify.getExpiresAt()); //过期时间

        System.out.println(list);

    }
}
