package com.lucky.config;

import com.lucky.interceptor.JWTInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 配置JWT拦截器
 *
 * @Author 不会笑的人是怪物
 * @Date 2023年 02月 08日 16:44
 */

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTInterceptor())
                //.addPathPatterns("/**")  // 拦截所有路径
                //.excludePathPatterns("/user/**");  // 排除用户相关路径
                .addPathPatterns("/user/test")  // 其他接口token验证
                .excludePathPatterns("/user/login");  // 所有用户接口都放行
    }
}
