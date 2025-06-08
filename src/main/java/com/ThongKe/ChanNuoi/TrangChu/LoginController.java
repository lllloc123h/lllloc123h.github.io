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
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "http://localhost:5173")
public class LoginController {
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private HttpServletRequest req;

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
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUserName(), loginRequest.getPassword()));
			UserDetails user = (UserDetails) authentication.getPrincipal();
			UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(user, null,
					user.getAuthorities());

			SecurityContextHolder.getContext().setAuthentication(authentication);
			HttpSession session = req.getSession(true); // tạo session nếu chưa có
			session.setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
					SecurityContextHolder.getContext());
			return ResponseEntity
					.ok(Map.of("message", "Đăng nhập thành công", "token", "", "username", user.getAuthorities()));

		} catch (

		AuthenticationException e) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST) // 400
					.body(Collections.singletonMap("message", "Sai thông tin đăng nhập"));
		}
	}

}
