package com.example.e.commerce.website.Controllers.UserControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.e.commerce.website.Model.signUp;
import com.example.e.commerce.website.Service.UserService.SignUpService;


@RestController
public class SignUpController {

	@Autowired
	private SignUpService service;
	
	@PostMapping("/SignUp")
	public ResponseEntity<?> add(@RequestBody signUp log){
		try {
			boolean stat=service.adduser(log);
			if(stat) {
				
				return new ResponseEntity<>(log.getEmail()+" Email added.",HttpStatus.OK);
			}else {
				return new ResponseEntity<>(log.getEmail()+" Email alreday present.",HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
