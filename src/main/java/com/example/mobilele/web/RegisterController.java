package com.example.mobilele.web;

import com.example.mobilele.dto.UserRegisterDTO;
import com.example.mobilele.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/users")
public class RegisterController {

    private UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/register")
    public String register(){
        return "auth-register";
    }

    @PostMapping("/register")
    public String register(UserRegisterDTO userRegisterDTO){
        userService.registerAndLogin(userRegisterDTO);
        return "redirect:/";
    }
}
