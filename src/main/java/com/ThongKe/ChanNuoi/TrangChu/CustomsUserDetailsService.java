package com.ThongKe.ChanNuoi.TrangChu;

import java.util.Collection;
import java.util.Collections;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import jakarta.websocket.server.ServerEndpoint;
@Service
public class CustomsUserDetailsService implements UserDetailsService{
	@Autowired
	UserRepository UserRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException{
		User user = UserRepo.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));
		return new org.springframework.security.core.userdetails.User(
			    user.getUsername(),
			    user.getPassword(),
			    Collections.singleton(new SimpleGrantedAuthority(user.isUseradmin() ? "ROLE_ADMIN" : "ROLE_USER"))
			);

	}
}
