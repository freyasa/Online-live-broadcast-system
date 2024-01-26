package com.niit.onlivestream.interceptor;

import com.niit.onlivestream.common.ErrorCode;
import com.niit.onlivestream.domain.UserInfo;
import com.niit.onlivestream.exception.BusinessException;
import com.niit.onlivestream.util.JwtUtil;
import com.niit.onlivestream.util.ThreadLocalUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

import static com.niit.onlivestream.util.OMUtils.MapToObject;


@Component
public class LoginInterceptor implements HandlerInterceptor {


    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)throws Exception{
        String token = request.getHeader("Authorization");
        try {
            Map<String,Object> objectMap = JwtUtil.parseToken(token);
            //把业务数据存储到ThreadLocal中
            UserInfo user = new UserInfo();
            user= (UserInfo) MapToObject(objectMap,user);
            ThreadLocalUtil.set(user);
            // 放行
            return true;
        }catch (Exception e){
            //不放行
            throw new BusinessException(ErrorCode.NOT_LOGIN,"token为空");
        }

    }


    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        //清空ThreadLocal
        ThreadLocalUtil.remove();
    }
}