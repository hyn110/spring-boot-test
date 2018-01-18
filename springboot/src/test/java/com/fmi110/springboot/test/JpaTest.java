package com.fmi110.springboot.test;

import com.fmi110.springboot.domain.User;
import com.fmi110.springboot.reposity.UserReposity;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class JpaTest {
    @Autowired
    private UserReposity userReposity;
    @Test
    public void test(){
        // 保存操作
        userReposity.save(new User("hadoop", 2));
        userReposity.save(new User("storm", 3));

        // 分页并排序查找
        Page<User> page = userReposity.findAll(new PageRequest(1, 4, new Sort(Sort.Direction.ASC, "age")));
        int total = page.getSize();
        List<User> list = page.getContent();
        System.out.println(total+"  ====  "+list);

        List<User> allUsers = userReposity.findAllUsers();
        System.out.println(allUsers);


        // 删除
        userReposity.delete(9l); // 根据id 删除
        User user = new User();
        user.setId(8l);
        userReposity.delete(user);

        // 修改 , 持久态对象修改即可
    }
    @Test
    public void test2(){
        userReposity.deleteUserByName("蜂蜜110");
    }
}
