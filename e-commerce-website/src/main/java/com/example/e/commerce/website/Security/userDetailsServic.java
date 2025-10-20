package com.example.e.commerce.website.Security;

import java.util.Collection;
import java.util.Collections;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.e.commerce.website.Model.signUp;


public class userDetailsServic implements UserDetails {

	@Autowired
	private signUp use;
	
	public userDetailsServic(signUp use) {
		super();
		this.use = use;
	}
	
	public userDetailsServic() {
	
	}

	/*@Override
	 public Collection<? extends GrantedAuthority> getAuthorities() {
        // Correct way to map roles to Spring Security GrantedAuthorities
        return use.getRoles().stream() // Get the Set of Role objects from your user
                   .map(role -> new SimpleGrantedAuthority(role.getRoleName())) // Convert each Role to a SimpleGrantedAuthority using its name
                   .collect(Collectors.toList()); // Collect them into a List
    }*/
	 public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(new SimpleGrantedAuthority(use.getRole()));
	 
	 }
	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return use.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return use.getEmail();
	}

}
