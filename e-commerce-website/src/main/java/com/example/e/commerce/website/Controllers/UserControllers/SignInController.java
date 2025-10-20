package com.example.e.commerce.website.Controllers.UserControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.e.commerce.website.Service.UserService.SignInService;
import com.example.e.commerce.website.dto.Admindto.Login;

@RestController
public class SignInController {

	@Autowired
	private SignInService service;
	
	@PostMapping("/SignIn")
	public ResponseEntity<?> check(@RequestBody Login log){
		try {
			String token=service.generateToken(log);
			if(token!=null && !token.isEmpty()) {
				System.out.println(token);
				return new ResponseEntity<>(token,HttpStatus.OK);
			}else {
				return new ResponseEntity<>("Failed to generate token",HttpStatus.UNAUTHORIZED);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
