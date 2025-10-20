package com.example.e.commerce.website.Service.AdminService;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;

import org.springframework.stereotype.Service;

import com.example.e.commerce.website.Security.JwtUtils;
import com.example.e.commerce.website.dto.Admindto.Login;


@Service
public class AdSignInService {
	@Autowired
	private JwtUtils util;
	@Autowired
	private AuthenticationManager authenticationmanger;
	public String generateToken(Login use) {
		Authentication authenticate=authenticationmanger.authenticate(new UsernamePasswordAuthenticationToken(use.getEmail(),use.getPassword()));
		if(authenticate.isAuthenticated()) {
			return util.generateToken(use);
		}
		return null;
	}
}
