package com.ThongKe.ChanNuoi.TrangChu;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ThongKe.ChanNuoi.Entity.Users;
import com.ThongKe.ChanNuoi.Service.UsersService;

@Service
public class CustomsUserDetailsService implements UserDetailsService {
	@Autowired
	private UsersService usersService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Users user = usersService.usersFindByUserName(username)
				.orElseThrow(() -> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(user.getUserName(), user.getPassword(),
				Collections.singleton(new SimpleGrantedAuthority(user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER")));

	}
}
