package com.web.dazu.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.web.dazu.service.LoginService;

import io.swagger.annotations.ApiOperation;

@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
public class LoginController {

	@Autowired
	private LoginService service;
	
	@ApiOperation(value = "로그인 - 카카오  로그인 API로 Token 및 회원 정보 불러오기", response = HashMap.class)
	@PostMapping("/login")
	public HashMap<String, Object> login(@RequestParam(value = "code", required = false) String code) {
		
		String accessToken = null;
		HashMap<String, Object> userInfo = new HashMap<String, Object>();
		try {
			accessToken = service.getAccessToken(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			userInfo = service.getMemberInfoKAKAO(accessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
		userInfo.put("accessToken", accessToken);
		
		return userInfo;
	}
	
	@ApiOperation(value = "로그아웃")
	@PostMapping("/logout")
	public void logout(String accessToken) {
		try {
			service.logout(accessToken);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

