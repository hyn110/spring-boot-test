package com.fmi110.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
    @RequestMapping("/hello")
    @ResponseBody
    public Object hello(){
        return "hello springboot...";
    }
    @RequestMapping("/index")
    public String index(ModelMap map) {
        map.addAttribute("host", "www.fmi110.com");
        return "/index";
    }

    @GetMapping("/exception")
    @ResponseBody
    public String exception(){
//        throw new RuntimeException("故意抛个一个异常...");
        int a= 1/0;
        return "异常统一处理演示";
    }
}
