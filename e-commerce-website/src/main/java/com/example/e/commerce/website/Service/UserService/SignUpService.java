package com.example.e.commerce.website.Service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.e.commerce.website.Model.signUp;
import com.example.e.commerce.website.Repo.signUpRepo;

@Service
public class SignUpService {

	@Autowired
	private signUpRepo repo;
	
	
	public boolean adduser(signUp log) {
		if(!repo.existsByEmail(log.getEmail())) {
			repo.save(log);
			return true;
		}
		return false;
	}
}
