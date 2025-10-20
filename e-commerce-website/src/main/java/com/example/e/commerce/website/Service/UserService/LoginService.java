package com.example.e.commerce.website.Service.UserService;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.e.commerce.website.Model.signUp;
import com.example.e.commerce.website.Repo.signUpRepo;

@Service
public class LoginService {

	@Autowired
	private signUpRepo repo;
	public signUp getUser(String user) {
		if(user==null) {
			return null;
		}
		return repo.findByEmail(user);
	}
	public boolean updateUser(signUp sign,String userEmail) {
		if(sign==null || userEmail ==null) {
			return false;
		}
		signUp optionalUser=repo.findByEmail(userEmail);
		if(optionalUser!=null) {
			
			if(sign.getPhone()!=null) {
				optionalUser.setPhone(sign.getPhone());
			}
			
			if(sign.getName()!=null) {
				optionalUser.setName(sign.getName());
			}
			
			if(sign.getAddress()!=null) {
				optionalUser.setAddress(sign.getAddress());
			}
			repo.save(optionalUser);
			return true;
		}
		return false;
	}
	

}
