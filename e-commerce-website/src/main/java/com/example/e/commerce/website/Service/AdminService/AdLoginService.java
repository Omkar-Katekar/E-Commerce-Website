package com.example.e.commerce.website.Service.AdminService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.e.commerce.website.Model.signUp;
import com.example.e.commerce.website.Repo.signUpRepo;
@Service
public class AdLoginService {

	@Autowired
	private signUpRepo repo;
	public List<signUp> getUsers() {
		// TODO Auto-generated method stub
		return repo.findAll();
	}

	public boolean deleteUser(int id) {
		if(repo.existsById(id)) {
			repo.deleteById(id);
			return true;
		}
		return false;
	}
	
	public boolean updateUser(signUp sign,int id) {
		if(sign==null) {
			return false;
		}
		Optional<signUp> optionalUser=repo.findById(id);
		if(optionalUser.isPresent()) {
			signUp existingUser=optionalUser.get();
			
			if(sign.getPhone()!=null) {
				existingUser.setPhone(sign.getPhone());
			}
			
			if(sign.getName()!=null) {
				existingUser.setName(sign.getName());
			}
			
			if(sign.getAddress()!=null) {
				existingUser.setAddress(sign.getAddress());
			}
			repo.save(existingUser);
			return true;
		}
		return false;
	}
}
