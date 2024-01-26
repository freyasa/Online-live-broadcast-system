package com.niit.onlivestream.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.niit.onlivestream.common.BaseResponse;
import com.niit.onlivestream.common.ErrorCode;
import com.niit.onlivestream.common.ResultUtils;
import com.niit.onlivestream.domain.UserInfo;
import com.niit.onlivestream.exception.BusinessException;
import com.niit.onlivestream.service.UserInfoService;
import com.niit.onlivestream.util.JwtUtil;
import com.niit.onlivestream.util.ThreadLocalUtil;
import com.niit.onlivestream.vo.UserInfoRequest.UserChangePswRequest;
import com.niit.onlivestream.vo.UserInfoRequest.UserLoginRequest;
import com.niit.onlivestream.vo.UserInfoRequest.UserLoginResponse;
import com.niit.onlivestream.vo.UserInfoRequest.UserRegisterRequest;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.catalina.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

import static com.niit.onlivestream.contant.UserConstant.ADMIN_ROLE;
import static com.niit.onlivestream.contant.UserConstant.USER_LOGIN_STATE;
import static com.niit.onlivestream.util.OMUtils.MapToObject;
import static com.niit.onlivestream.util.OMUtils.ObjectToMap;


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
    public BaseResponse<UserLoginResponse> userLogin(@RequestBody UserLoginRequest userLoginRequest, HttpServletRequest request){
        if(userLoginRequest==null)
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        String userAccount = userLoginRequest.getUserAccount();
        String userPassword = userLoginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword))
            throw new BusinessException(ErrorCode.NULL_ERROR);

        //开始执行操作
        UserInfo userInfo = userService.userLogin(userAccount,userPassword,request);
        if(userInfo==null)
            throw new BusinessException(ErrorCode.SUCCESS,"账户或密码不正确");

        //生成Token
        Map<String,Object> claim = new HashMap<>();
        claim = ObjectToMap(userInfo);
        String token = JwtUtil.getToken(claim);

        // 封装返回体
        UserLoginResponse response =new UserLoginResponse();
        response = getResponse(userInfo);
        response.setToken(token);

        // 返回
        return ResultUtils.success(response);
    }


    @PostMapping("/getCurrentUser")
    public BaseResponse<UserInfo > userLogin(){
        UserInfo user= ThreadLocalUtil.get();
        return  ResultUtils.success(user);
    }



    @PostMapping("/updatePsw")
    public BaseResponse<String> userRegister(@RequestBody UserChangePswRequest request) {
        // 校验
        if (request == null) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        String oldPassword = request.getOldPassword();
        String newPassword = request.getNewPassword();
        String checkPassword = request.getCheckPassword();

        if (StringUtils.isAnyBlank(oldPassword,newPassword,checkPassword)) {
            throw new BusinessException(ErrorCode.NULL_ERROR,"缺少必要参数");
        }
        int result = userService.userUpdatePassword(oldPassword,newPassword,checkPassword);
        return ResultUtils.success(null,"更新成功");
    }



    public UserLoginResponse getResponse(UserInfo userInfo){
        if (userInfo == null) {
            return null;
        }
        UserLoginResponse response = new UserLoginResponse();
        response.setUseraccount(userInfo.getUseraccount());
        response.setUuid(userInfo.getUuid());
        response.setUsername(userInfo.getUsername());
        response.setUserage(userInfo.getUserage());
        response.setUseremail(userInfo.getUseremail());
        response.setUserprivilege(userInfo.getUserprivilege());
        response.setUserpassword(null);
        response.setUseravatar(userInfo.getUseravatar());
        response.setUsersex(userInfo.getUsersex());
        response.setUsercreatetime(userInfo.getUsercreatetime());
        return  response;
    }

}
