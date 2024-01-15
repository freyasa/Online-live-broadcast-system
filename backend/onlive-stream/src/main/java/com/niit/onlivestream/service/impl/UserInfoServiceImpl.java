package com.niit.onlivestream.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niit.onlivestream.domain.UserInfo;
import generator.service.UserInfoService;
import com.niit.onlivestream.mapper.UserInfoMapper;
import org.springframework.stereotype.Service;

/**
* @author 29756
* @description 针对表【user_info】的数据库操作Service实现
* @createDate 2024-01-15 19:09:48
*/
@Service
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService{

}




