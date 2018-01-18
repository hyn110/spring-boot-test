package com.fmi110.springboot.reposity;

import com.fmi110.springboot.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserReposity extends JpaRepository<User,Long> {
    /**
     * 通过用户名查找用户
     * @param name
     * @return
     */
    List<User> findUsersByName(String name);

    /**
     * 使用原生sql语句 ,查找所有用户并按年龄排序
     * @return
     */
    @Query(value = "select * from user order by age",nativeQuery = true)
    List<User> findAllUsers();

    @Transactional
    @Modifying
    @Query(value = "delete from user where name=?1",nativeQuery = true)
    void deleteUserByName(String name);
}
