package com.ThongKe.ChanNuoi.TrangChu;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
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
		System.out.println(username);
		Users user = usersService.usersFindByUserName(username).orElse(null);
		if (user == null) {
			throw new UsernameNotFoundException("Không tìm thấy Username: " + username);
		}
		Collection<GrantedAuthority> grantedAuthoritySet = new HashSet<GrantedAuthority>();
		grantedAuthoritySet.add(new SimpleGrantedAuthority(user.isAdmin() ? "ROLE_ADMIN" : "ROLE_USER"));
		return new CustomUserDetails(user, grantedAuthoritySet);
	}
}
