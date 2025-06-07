package com.ThongKe.ChanNuoi.TrangChu;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ThongKe.ChanNuoi.Entity.Users;

public class CustomUserDetails implements UserDetails {
	Users user;
	Collection<? extends GrantedAuthority> authorities;

	public CustomUserDetails(Users user, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.user = user;
		this.authorities = authorities;
	}

	public CustomUserDetails(Users user) {
		this.user = user;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUserName();
	}
}
