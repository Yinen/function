package com.yinen.swagger.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
@Component
public class TokenUtil {
    //token过期时长30分钟
    private static long EXPIRE_TIME = 0;
    //token私钥:正常可以自己设置一个固定的（项目名）；
    // 也可以设置成密码，这样一旦用户修改密码token失效就会要重新登录
    private static String TOKEN_SECRET = "yinen";

    @Value("${user.token.expire-time}")
    private String expireTime;

    @Value("${user.token.token-secret}")
    private String tokenSecret;

    @PostConstruct
    public void getExpireTime() {
        EXPIRE_TIME = Integer.parseInt(this.expireTime);
        System.out.println(this.expireTime);
    }
    @PostConstruct
    public void getTokenSecret() {
        TOKEN_SECRET = this.tokenSecret;
        System.out.println(this.tokenSecret);
    }

    public static String sign(String userName, String password, Integer id) {
        System.out.println(EXPIRE_TIME+TOKEN_SECRET);
        String signData = "";
        //过期时间
        Date date = new Date(System.currentTimeMillis() + EXPIRE_TIME);
        //私钥及加密算法
        Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
        //设置头信息
        Map<String, Object> header = new HashMap();
        header.put("typ", "JWT");
        header.put("alg", "HS256");

        signData = JWT.create()
                .withHeader(header)
                .withClaim("id", id)
                .withClaim("userName", userName)
                //.withClaim("password", password)
                .withExpiresAt(date)
                .sign(algorithm);

        return signData;
    }

    /**
     * @Description token解码校验
     * @param token
     */
    public static boolean verfiy(String token) {

        try {
            Algorithm algorithm = Algorithm.HMAC256(TOKEN_SECRET);
            JWTVerifier jwtVerifier = JWT.require(algorithm).build();
            //校验签名
            DecodedJWT decodedJWT = jwtVerifier.verify(token);
            Integer id = decodedJWT.getClaim("id").asInt();
            String userName = decodedJWT.getClaim("userName").asString();
            //String password = decodedJWT.getClaim("password").asString();
            //利用用户id可以去获取具体的用户信息
            //....
            //校验过期时间
            if(new Date().getTime() > decodedJWT.getExpiresAt().getTime()){
                return false;
            }
        } catch (Exception e) {
            return false;
        }

        return true;
    }
}
