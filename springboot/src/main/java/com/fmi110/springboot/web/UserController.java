package com.fmi110.springboot.web;

import com.fmi110.springboot.domain.User;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * restful api 用户操作,增删改查
 * get , post , put , get , delete
 */
@RestController
@RequestMapping("/users")
public class UserController {
    static Map<Long, User> users = Collections.synchronizedMap(new HashMap<>());

    // 获取所有
    @ApiOperation(value = "获取用户列表")
    @GetMapping("/")
    public Map<Long, User> getUserList() {
        return users;
    }

    @ApiIgnore
    @GetMapping("xx")
    public String xx() {
        return "xx";
    }

    // 增加
    @ApiOperation(value = "创建用户", notes = "根据 User 对象创建用户")
    @ApiImplicitParam(name = "user", value = "用户详细实体user", required = true, dataType = "User")
    @PostMapping("/")
    public String postUser(@ModelAttribute User user) {
        users.put(user.getId(), user);
        return "success";
    }

    // 根据id 获取
    @ApiOperation(value = "根据id获取用户", notes = "根据url路径中的 id 获取")
    @ApiImplicitParam(name = "id", value = "用户 id", paramType = "path", required = true, dataType = "Long")
    @GetMapping("/{id}")
    public User getUserById(@PathVariable("id") Long id) {
        return users.get(id);
    }

    // 修改
    @ApiOperation(value = "更新用户详细信息", notes = "根据url中id 更新指定对象")
    @ApiImplicitParams({
                               @ApiImplicitParam(name = "id", value = "用户id", paramType = "path", required = true,
                                                 dataType = "Long"),
                               @ApiImplicitParam(name = "user", value = "用户详细信息实体user", required = true,
                                                 dataType = "User")})
    @PutMapping("/{id}")
    public User putUser(@PathVariable Long id, @ModelAttribute User user) {
        User u = new User();
        BeanUtils.copyProperties(user, u);
        users.put(u.getId(), u);
        return u;
    }

    @ApiOperation("根据id删除用户")
    @ApiImplicitParam(name = "id", value = "用户id", paramType = "path", required = true, dataType = "Long")
    @DeleteMapping("/{id}")
    public User deleteUser(@PathVariable Long id) {
        User remove = users.remove(id);
        return remove;
    }
}
