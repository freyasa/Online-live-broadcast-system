package com.niit.onlivestream.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niit.onlivestream.domain.UserInfo;
import com.niit.onlivestream.service.UserInfoService;
import com.niit.onlivestream.mapper.UserInfoMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
* @author 29756
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2024-01-15 19:09:48
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService {

    @Resource
    UserInfoMapper userInfoMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        UserInfo userInfo =new UserInfo();
        userInfo.setUseraccount(userAccount);
        userInfo.setUserpassword(userPassword);
        userInfo.setUsercreatetime(new Date());
        userInfoMapper.insert(userInfo);
        return 0;
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        return null;
    }

    @Override
    public User getSafetyUser(User originUser) {
        return null;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        return 0;
    }

    @Override
    public List<UserInfo> userInfoGetAll() {
        return userInfoMapper.selectList(null);
    }

    @Override
    public UserInfo userGetById(String id) {
        return null;
    }
}




