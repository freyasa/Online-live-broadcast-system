package com.niit.onlivestream.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.niit.onlivestream.common.ErrorCode;
import com.niit.onlivestream.common.ResultUtils;
import com.niit.onlivestream.domain.UserInfo;
import com.niit.onlivestream.exception.BusinessException;
import com.niit.onlivestream.service.UserInfoService;
import com.niit.onlivestream.mapper.UserInfoMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.niit.onlivestream.contant.UserConstant.USER_LOGIN_STATE;

/**
* @author arthur
*/
@Service
@Slf4j
public class UserInfoServiceImpl extends ServiceImpl<UserInfoMapper, UserInfo>
    implements UserInfoService {

    private static final String SALT = "arthur";
    @Resource
    UserInfoMapper userInfoMapper;


    /**
     * 用户注册
     * @param userAccount 账号
     * @param userPassword 密码
     * @param checkPassword 校验密码
     * @return 用户ID
     */
    @Override
    public String userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }

        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户不能包含特殊字符");
        }
        // 密码和校验密码相同
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次账号密码不一样");
        }

        // 账户不能重复
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = userInfoMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 3. 插入数据
        UserInfo user = new UserInfo();
        user.setUseraccount(userAccount);
        user.setUserpassword(encryptPassword);
        user.setUsercreatetime(new Date());
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR,"插入失败");
        }
        return user.getUuid();
    }


    /**
     * 登录
     * @param userAccount 账户
     * @param userPassword 密码
     * @param request 请求
     * @return  用户脱敏信息
     */
    @Override
    public UserInfo userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if (userPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        // 账户不能包含特殊字符
        String validPattern = "[`~!@#$%^&*()+=|{}':;',\\\\[\\\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if (matcher.find()) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账户不能包含特殊字符");
        }
        // 2. 加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT + userPassword).getBytes());
        // 查询用户是否存在
        QueryWrapper<UserInfo> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        queryWrapper.eq("userPassword", encryptPassword);
        queryWrapper.eq("isDelete",0);
        UserInfo user = userInfoMapper.selectOne(queryWrapper);
        // 用户不存在
        if (user == null) {
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }
        // 3. 用户脱敏
        UserInfo safetyUser = getSafetyUser(user);
        // 4. 记录用户的登录态
        request.getSession().setAttribute(USER_LOGIN_STATE, safetyUser);
        return safetyUser;
    }

    @Override
    public int userLogout(HttpServletRequest request) {
        return 0;
    }

    @Override
    public List<UserInfo> userInfoGetAll() {
        return null;
    }

    @Override
    public UserInfo userGetById(String id) {
        return null;
    }

    /**
     * 用户脱敏
     * @param originUser 原始用户
     * @return safetyUser
     */
    @Override
    public UserInfo getSafetyUser(UserInfo originUser) {
        if (originUser == null) {
            return null;
        }
        UserInfo safetyUser = new UserInfo();
        safetyUser.setUuid(originUser.getUuid());
        safetyUser.setUseraccount(originUser.getUseraccount());
        safetyUser.setUsername(originUser.getUsername());
        safetyUser.setUserage(originUser.getUserage());
        safetyUser.setUseremail(originUser.getUseremail());
        safetyUser.setUserprivilege(originUser.getUserprivilege());
        safetyUser.setUsercreatetime(originUser.getUsercreatetime());
        safetyUser.setUserupdatetime(originUser.getUserupdatetime());
        safetyUser.setUseravatar(originUser.getUseravatar());
        safetyUser.setUsersex(originUser.getUsersex());
        return safetyUser;
    }
}



