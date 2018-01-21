package com.fmi110.springboot.service.impl;

import com.fmi110.springboot.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class UserService implements IUserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    /**
     * 新增一个用户
     *
     * @param name
     * @param age
     */
    @Override
    public void create(String name, Integer age) {
        jdbcTemplate.update("INSERT INTO user (name,age) VALUES (?,?)", name, age);
    }

    /**
     * 根据name删除一个用户高
     *
     * @param name
     */
    @Override
    public void deleteByName(String name) {
        jdbcTemplate.update("DELETE FROM user WHERE name=?", name);
    }

    /**
     * 获取用户总量
     */
    @Cacheable(value = "oneMinute")  // 指定使用的缓存对象为 onMinute (在 ehcache.xml 中配置的)
    @Override
    public Integer getAllUsersCount() {
        System.out.println("===========getAllUsersCount==========");
        return jdbcTemplate.queryForObject("select count(1) from user",Integer.class);
    }

    /**
     * 删除所有用户
     */
    @Override
    public void deleteAllUsers() {
        jdbcTemplate.update("delete from user");
    }
}
