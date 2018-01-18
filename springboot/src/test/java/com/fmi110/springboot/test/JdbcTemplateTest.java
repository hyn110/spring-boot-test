package com.fmi110.springboot.test;

import com.fmi110.springboot.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JdbcTemplateTest {
    @Autowired
    IUserService userService;
    @Test
    public void insert(){
        userService.create("fmi110",18);
        userService.create("黑马",18);
        userService.create("传智播客",12);
    }

    @Test
    public void delete(){
        userService.deleteByName("黑马");
    }

}
