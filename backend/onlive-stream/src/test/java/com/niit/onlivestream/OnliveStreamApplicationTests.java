package com.niit.onlivestream;

import com.niit.onlivestream.domain.UserInfo;
import com.niit.onlivestream.service.UserInfoService;
import com.niit.onlivestream.vo.UserInfoRequest.UserUpdateRequest;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class OnliveStreamApplicationTests {



	@Resource
	UserInfoService userInfoService;


	public void test(){
		UserUpdateRequest updateRequest =new UserUpdateRequest();

	}




}
