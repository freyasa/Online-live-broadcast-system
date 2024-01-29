package com.niit.online_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niit.online_music.entity.Admin;
import com.niit.online_music.util.Result;
import jakarta.servlet.http.HttpSession;

public interface AdminService extends IService<Admin> {

    //验证用户登录
    Result verityPasswd(Admin admin, HttpSession session);
}
