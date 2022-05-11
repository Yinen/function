package com.yinen.swagger.controller;

import com.yinen.swagger.pojo.User;
import com.yinen.swagger.util.TokenUtil;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;

@RestController
//跨域处理：本质上就是在response对象设置请求中，设置Access-Control-Allow-Origin该属性值
//同源策略：协议、主机、端口作为一个整体，有一个不一样就是属于不同的源，不同源之间访问就会产生跨域问题
/* prefight(预检请求)：首先使用 OPTIONS 方法发起一个预检请求到服务器，以获知服务器是否允许该实际请求。
   "预检请求“的使用，可以避免跨域请求对服务器的用户数据产生未预期的影响
   简单请求（POST、GET、HEAD）不会触发预检请求

   Origin: https://foo.example 源
   Access-Control-Request-Method: POST  下一次真实请求的方法
   Access-Control-Request-Headers: X-PINGOTHER, Content-Type 下一次真实请求头所包含的字段

   服务器跨域设置：
   Access-Control-Allow-Origin: https://foo.example  允许访问的源
   Access-Control-Allow-Methods: POST, GET, OPTIONS  允许访问的方法
   Access-Control-Allow-Headers: X-PINGOTHER, Content-Type   允许访问的请求头
   Access-Control-Max-Age: 86400  有效时间，即该期间内不需要跨域预检请求
 */

/*
  附带身份凭证的请求
  前端将 XMLHttpRequest 的 withCredentials 标志设置为 true，从而向服务器发送 Cookies。
  因为这是一个简单 GET 请求，所以浏览器不会对其发起“预检请求”。
  但是，如果服务器端的响应中未携带 Access-Control-Allow-Credentials: true，浏览器将不会把响应内容返回给请求的发送者。
 */
@CrossOrigin(origins = "http://localhost:8080",allowCredentials = "true")
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
