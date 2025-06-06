package com.ThongKe.ChanNuoi.TrangChu;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {

    @GetMapping("/login") // Sửa lại đúng chữ thường để khớp với cấu hình Spring Security
    public String getLoginForm() {
        return "login"; // Tên file HTML viết thường (login.html)
    }

    @GetMapping("/index")
    public String getIndex() {
        return "index";
    }

}
