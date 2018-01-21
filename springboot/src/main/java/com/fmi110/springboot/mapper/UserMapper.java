package com.fmi110.springboot.mapper;

import com.fmi110.springboot.domain.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * User对象
 */
@Mapper
public interface UserMapper {

    @Select("SELECT * from user where name=#{name}")
    User findByName(@Param("name") String name);

    @Insert("INSERT INTO user (name,age) VALUES (#{name},#{age})")
    int insert(User user);
}
