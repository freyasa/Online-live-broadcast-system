package com.niit.onlivestream.vo.UserInfoRequest;

import lombok.Data;

import java.io.Serializable;

@Data
public class UserChangePswRequest implements Serializable {


    private String oldPassword;

    private String newPassword;

    private String checkPassword;


}
