package com.example.e.commerce.website.Security;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;


import com.example.e.commerce.website.Model.signUp;
import com.example.e.commerce.website.Repo.signUpRepo;


@Component
public class customeUserDetailService implements UserDetailsService {

	@Autowired
	private signUpRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		signUp use=repo.findByEmail(email);
		if(Objects.isNull(use)) {
			System.out.println("User not found");
			throw new UsernameNotFoundException("User not found");
		}
		return new userDetailsServic(use);
	}

}
