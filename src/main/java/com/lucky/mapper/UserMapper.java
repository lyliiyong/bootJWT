package com.lucky.mapper;

import com.lucky.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @Author 不会笑的人是怪物
 * @Date 2023年 02月 08日 15:18
 */

@Mapper
public interface UserMapper {
    User login(User user);
}
