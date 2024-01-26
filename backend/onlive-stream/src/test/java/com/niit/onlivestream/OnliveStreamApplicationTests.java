package com.niit.onlivestream;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.niit.onlivestream.domain.UserInfo;
import com.niit.onlivestream.service.UserInfoService;
import com.niit.onlivestream.vo.UserInfoRequest.UserUpdateRequest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class OnliveStreamApplicationTests {



    @Test
    void genToken(){
        Map<String,Object> claim = new HashMap<>();
        claim.put("id","1");
        claim.put("username","张三");

        String token = JWT.create().
                withClaim("user",claim)
                .withExpiresAt(new Date(System.currentTimeMillis()+1000*60*60*12))
                .sign(Algorithm.HMAC256("arthur"));
        System.out.println(token);
    }


    @Test
    void parseToken(){
        String token="eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9" +
                ".eyJ1c2VyIjp7ImlkIjoiMSIsInVzZXJuYW" +
                "1lIjoi5byg5LiJIn0sImV4cCI6MTcwNjI5NjcxMn0" +
                ".yTFzYpbeZ5BN2YahQGBrBAQoJdkE58QITVFeUtEQ3cA";
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("arthur")).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        Map<String, Claim> claimMap = decodedJWT.getClaims();
        System.out.println(claimMap.get("user"));
    }




}
