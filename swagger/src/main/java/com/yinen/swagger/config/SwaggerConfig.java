package com.yinen.swagger.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
/*
1、导入相关依赖
2、由于swagger默认api网页处于/META-INF下，需要在WebMvcConfig配置中指定资源的映射路径
3、创建配置文件，@Configuration，@EnableSwagger2配置api扫描的包。http://localhost:8081/swagger-ui.html
4、如果只希望Swagger在生产环境中使用，发布时候不使用则利用enable属性来动态控制(创建不同的springboot配置文件)
4.1判断是不是生产环境flag(在swagger配置类)
4.2注入enable（flag）
5、如何设置多个分组，创建多个Docket实例即可，不重名。利用的就是new Docket().groupName("开发者")

6、实体类配置 pojo User
6.1只要我们的接口的返回值中存在实体类，这个实体类就会被扫描到swagger的model中
6.2添加注释，增加接口的可读性，有利于前后端对接
可以直接在实体类上加一个@Api("注释")或@APIMode("用户实体类") 加在属性上@ApiModeProperty("用户名")
@ApiOperation("")给controller接口加注释,放在方法上
@ApiParam("") 给controller接口的参数加注释,放在方法的参数上
7、总结：正式发布时，出于安全和节省运行内存要关闭swagger
 */
@Configuration
@EnableSwagger2
public class SwaggerConfig {
    @Bean
    public Docket createRestApi(Environment environment) {
        //设置要显示的Swagger环境
        Profiles profiles = Profiles.of("dev","test");
        //获取项目的环境
        boolean flag = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(flag)
                .groupName("CYR")
                //.host("127.0.0.1")
                .select()
                //RequestHandlerSelectors配置要扫描接口的方式
                //basePackage指定要扫描的包
                //any扫描全部
                //none不扫描
                //withClassAnnotation()扫描类上的注解，参数是一个注解类的反射对象GetMapping.class
                //withMethodAnnotation()扫描方法上的注解
                .apis(RequestHandlerSelectors.basePackage("com.yinen.swagger.controller"))
                //paths指定过滤哪些路径,不过滤就别填
                //none()
                //any
                //ant("/com/")
                //.paths(PathSelectors.none())
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("陈迎仁","https://github.com/Yinen","2318191704@qq.com");
        return new ApiInfoBuilder()
                .title("配置系统API文档")
                .version("1.0")
                .termsOfServiceUrl("")
                .contact(contact)
                .description("陈迎仁-2022-05-09")
                .build();
    }
}
