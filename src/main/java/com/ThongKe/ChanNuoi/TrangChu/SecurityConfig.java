package com.ThongKe.ChanNuoi.TrangChu;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    private CustomsUserDetailsService usersDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                    .requestMatchers("/login", "/css/**").permitAll() // Cho phép không cần đăng nhập đối với trang login và css
                    .anyRequest().authenticated() // Các trang còn lại phải xác thực
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .defaultSuccessUrl("/Huyen", true).permitAll()
            )
            .logout(logout -> logout
                    .logoutUrl("/logout") // Đường dẫn đăng xuất
                    .logoutSuccessUrl("/login?logout") // Redirect khi đăng xuất thành công
                    .invalidateHttpSession(true) // Xóa session
                    .clearAuthentication(true) // Xóa thông tin xác thực
                    .permitAll()
            )
            .userDetailsService(usersDetailsService); // Sử dụng CustomUserDetailsService
        return http.build();
    }
}
