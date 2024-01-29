package com.niit.online_music.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niit.online_music.mapper.AdminMapper;
import com.niit.online_music.service.AdminService;
import com.niit.online_music.util.Result;
import com.niit.online_music.entity.Admin;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper,Admin> implements AdminService {

    private  AdminMapper adminMapper;

    @Autowired
    public void setAdminMapper(AdminMapper adminMapper) {
        this.adminMapper = adminMapper;
    }

    //验证用户登录信息
    @Override
    public Result verityPasswd(Admin admin, HttpSession session) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("name", admin.getName());
        queryWrapper.eq("password", admin.getPassword());

        if (adminMapper.selectCount(queryWrapper) > 0) {
            session.setAttribute("name", admin.getName());
            return Result.success(admin);
        } else {
            return Result.error("200", "用户名或者密码不对");
        }
    }
}
