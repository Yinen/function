package com.yinen.swagger.controller;

import com.sun.deploy.net.HttpResponse;
import com.sun.org.glassfish.gmbal.ParameterNames;
import com.yinen.swagger.pojo.User;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

@RestController
@ResponseBody
public class SwaggerController {
    @GetMapping("/swagger")
    public String swagger(){
        return "1、新建一个springboot项目，即web项目\n2、创建对应的控制器\n" +
                "3、引入两个Swagger依赖\n4、创建一个配置类，使用@EnableSwagger2注解\n" +
                "5、访问链接为http:\\localhost:8080/swagger-ui.html";
    }
    //只要我们的接口的返回值中存在实体类，这个实体类就会被扫描到swagger的model中
    @ApiOperation("登录接口")
    @PostMapping("/login")
    public User login(@ApiParam("用户名") @RequestParam String name,@ApiParam("密码") @RequestParam String password){
        return new User(name,password);
    }
    @ApiOperation("获取用户信息接口")
    @GetMapping("/user")
    public User getUser(){
        return new User("name","password");
    }

    @ApiOperation("修改用户信息接口")
    @RequestMapping(value = "/user",method = RequestMethod.PUT)
    public String updateUser(@ApiParam("用户名") @RequestParam String name,@ApiParam("密码") @RequestParam String password){
        return "200";
    }

    @ApiOperation("删除用户接口")
    @RequestMapping(value = "/user",method = RequestMethod.DELETE)
    public String deleteUser(@ApiParam("用户名") @RequestParam String name){
        return "200";
    }


}

