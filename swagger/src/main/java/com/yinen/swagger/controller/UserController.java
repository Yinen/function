package com.yinen.swagger.controller;

import com.yinen.swagger.pojo.User;
import com.yinen.swagger.util.TokenUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

@RestController
public class UserController  {
//    @Value("${user.id}")
//    private Integer id_;
//    private static Integer id;
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public User login(@ApiParam("用户名") @RequestParam String name, @ApiParam("密码")@RequestParam  String password){
        //先获取用户id,生成token
        Integer id = 1;
        String token = TokenUtil.sign(name,password,id);
        boolean isLogin = TokenUtil.verfiy(token);
        System.out.println(isLogin);
        return new User(name,password);
    }

//    @PostConstruct
//    public void getTokenSecret() {
//        id = this.id_;
//    }
//
//    @PostMapping("/a")
//    public void a(){
//        System.out.println(id);
//    }
}
