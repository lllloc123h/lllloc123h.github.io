package com.ThongKe.ChanNuoi.TrangChu;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {
	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping("/login") // Sửa lại đúng chữ thường để khớp với cấu hình Spring Security
	public String getLoginForm() {
		return "login"; // Tên file HTML viết thường (login.html)
	}

	@GetMapping("/index")
	public String getIndex() {
		return "index";
	}

	@PostMapping("/login")
	public ResponseEntity<?> loginAPI(@RequestBody LoginRqquest loginRequest) {
		try {
			System.out.println(loginRequest);
			new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword());
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
			UserDetails user = (UserDetails) authentication.getPrincipal();
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authToken);

			return ResponseEntity
					.ok(Map.of("message", "Đăng nhập thành công", "token", "", "username", user.getAuthorities()));

		} catch (AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400
					.body(Collections.singletonMap("message", "Sai thông tin đăng nhập"));
		}
	}

}
