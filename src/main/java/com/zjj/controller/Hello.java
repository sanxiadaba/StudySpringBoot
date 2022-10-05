package com.zjj.controller;


import com.zjj.pojo.DateEntity;
import com.zjj.pojo.User;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;

@RestController
public class Hello {

    @RequestMapping("/hello")
    public String hello(@RequestParam("myName") String name,Integer id){
        System.out.println(name);
        System.out.println(id);
        return "world";
    }

    @RequestMapping("user")
    public User myUser(){
        User user = new User();
        user.setAge(100);
        user.setName("小明");
        return user;
    }

    @RequestMapping("postUser")
    public void postUser(@RequestBody User user){
        System.out.println(user);
    }


    @RequestMapping("/testDate")
    public DateEntity getDate(@RequestBody DateEntity dateEntity){
        System.out.println("入参的date:"+dateEntity.getDate());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date = sdf.format(dateEntity.getDate());
        System.out.println("SimpleDateFormat格式化后的date:"+date);

        DateEntity result = new DateEntity();
        result.setDate(new Date());
        return result;
    }

    // 获取url名
    @RequestMapping("/testPathVariable/{myName}")
    public void testPathVariable(@PathVariable("myName") String name){
        System.out.println(name);
    }
}
