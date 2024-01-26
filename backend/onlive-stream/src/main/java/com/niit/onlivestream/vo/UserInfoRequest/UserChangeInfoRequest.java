package com.niit.onlivestream.vo.UserInfoRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserChangeInfoRequest implements Serializable {

    private String uuid;

    private String username;

    private Integer userage;

    private String useremail;

    private Integer usersex;

    private byte[] useravatar;


}
