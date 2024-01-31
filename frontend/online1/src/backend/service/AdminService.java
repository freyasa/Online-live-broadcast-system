package com.niit.online_music.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.niit.online_music.entity.Admin;
import com.niit.online_music.util.Result;
import jakarta.servlet.http.HttpSession;

/**
 * @ProjectName: online_music
 * @Package: com.niit.online_music.service
 * @ClassName: AdminService
 * @Author: zjh
 * @description: TODO
 * @date: 2024/1/17 10:40
 * @version: 1.0
 */
public interface AdminService extends IService<Admin> {
    //验证用户登录
    Result verityPasswd(Admin admin, HttpSession session);
}
