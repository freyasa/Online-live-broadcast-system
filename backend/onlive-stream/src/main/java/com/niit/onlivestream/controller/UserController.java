package com.niit.onlivestream.controller;

import com.niit.onlivestream.common.BaseResponse;
import com.niit.onlivestream.common.ErrorCode;
import com.niit.onlivestream.common.ResultUtils;
import com.niit.onlivestream.domain.UserInfo;
import com.niit.onlivestream.exception.BusinessException;
import com.niit.onlivestream.service.UserInfoService;
import com.niit.onlivestream.vo.UserInfoRequest.UserLoginRequest;
import com.niit.onlivestream.vo.UserInfoRequest.UserRegisterRequest;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.niit.onlivestream.contant.UserConstant.ADMIN_ROLE;
import static com.niit.onlivestream.contant.UserConstant.USER_LOGIN_STATE;


@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserInfoService userService;

    /**
     * 用户注册
     * @param userRegisterRequest 注册请求体
     * @return 用户ID
     */
    @PostMapping("/register")
    public BaseResponse<String> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        // 校验
        if (userRegisterRequest == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        if (StringUtils.isAnyBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR);
        }
        String result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }

    /**
     *
     * @param userLoginRequest 登录请求体
     * @param request 保存session
     * @return 脱敏后的用户信息
     */
    @PostMapping("/login")
    public BaseResponse<UserInfo> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest==null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword))
            throw new BusinessException(ErrorCode.NULL_ERROR);
        UserInfo userInfo = userService.userLogin(userAccount,userPassword,request);
        if(userInfo==null)
            throw new BusinessException(ErrorCode.SUCCESS,"账户或密码不正确");
        return ResultUtils.success(userInfo);
    }




    @PostMapping("/delete")
    public BaseResponse<Boolean> deleteUser(@RequestBody String id, HttpServletRequest request) {
        if (!isAdmin(request)) {
            throw new BusinessException(ErrorCode.NO_AUTH);
        }
        if (id !=null && !id.equals("")) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        boolean b = userService.removeById(id);
        return ResultUtils.success(b);
    }



    /**
     * 是否为管理员
     *
     * @param request
     * @return
     */
    private boolean isAdmin(HttpServletRequest request) {
        // 仅管理员可查询
        Object userObj = request.getSession().getAttribute(USER_LOGIN_STATE);
        UserInfo user = (UserInfo) userObj;
        return user != null && user.getUserprivilege() == ADMIN_ROLE;
    }


}
