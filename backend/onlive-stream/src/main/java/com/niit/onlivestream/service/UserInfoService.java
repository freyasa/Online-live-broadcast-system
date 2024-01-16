package com.niit.onlivestream.service;


import com.niit.onlivestream.domain.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface UserInfoService {
    long userRegister(String userAccount, String userPassword, String checkPassword);
    User userLogin(String userAccount, String userPassword, HttpServletRequest request);
    User getSafetyUser(User originUser);
    int userLogout(HttpServletRequest request);
    List<UserInfo> userInfoGetAll();
    UserInfo userGetById(String id);
}
