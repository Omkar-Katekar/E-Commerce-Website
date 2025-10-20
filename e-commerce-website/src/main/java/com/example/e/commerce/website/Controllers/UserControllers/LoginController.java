package com.example.e.commerce.website.Controllers.UserControllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.e.commerce.website.Model.signUp;
import com.example.e.commerce.website.Service.UserService.LoginService;

@RestController
public class LoginController {

	@Autowired
	private LoginService service;
	@GetMapping("/show")
	public ResponseEntity<?> showusers(){
		try {
			String user=getloggedInUsername();
			if(user==null || user.isEmpty()) {
				return new ResponseEntity<>("Not logged in",HttpStatus.BAD_REQUEST);
			}
			
			signUp log=service.getUser(user);
			if(log !=null) {
				return new ResponseEntity<>(log,HttpStatus.OK);
			}
			return new ResponseEntity<>("No user",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/update")
	public ResponseEntity<?> updateUsers(@RequestBody signUp sign){
		try {
			String user=getloggedInUsername();
			if(user==null || user.isEmpty()) {
				return new ResponseEntity<>("Not logged in",HttpStatus.BAD_REQUEST);
			}
			
			boolean stat=service.updateUser(sign,user);
			if(stat) {
				return new ResponseEntity<>(user+" User updated",HttpStatus.OK);
			}
			return new ResponseEntity<>(user+ " Failed to update user",HttpStatus.BAD_REQUEST);
		}catch(Exception e) {
			return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	 public String getloggedInUsername() {
		 var authentication =SecurityContextHolder.getContext().getAuthentication();
		 if(authentication!=null && authentication.isAuthenticated() && authentication.getPrincipal() instanceof UserDetails) {
			 return ((UserDetails) authentication.getPrincipal()).getUsername();
		 }
		 return null;
	 }
}
