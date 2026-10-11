package com.app.auth.customuserdetails;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

public class CustomUserDetails extends User {

	private long id;

	public CustomUserDetails(String username, String password, 
			Collection<? extends GrantedAuthority> authorities,
			long id) {
		super(username, password, authorities);
		this.id = id;
	}

	public long getId() {
		return id;
	}

}