package com.fmi110.springboot.test;

import com.fmi110.springboot.domain.User;
import com.fmi110.springboot.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class MybatisTest {
    @Autowired
    UserMapper userMapper;

    @Test
    public void testMybatis(){
        User user = new User("spring", 5);
        userMapper.insert(user);
    }

    @Test
    public void testselect(){
        User user = userMapper.findByName("黑马程序员");
        System.out.println("====="+user);
    }
}
