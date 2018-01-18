package com.fmi110.springboot.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
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
}
