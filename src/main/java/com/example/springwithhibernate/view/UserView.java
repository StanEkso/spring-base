package com.example.springwithhibernate.view;

import com.example.springwithhibernate.dto.UserCreationDto;
import com.example.springwithhibernate.entity.UserEntity;
import com.example.springwithhibernate.exceptions.UserAlreadyExistsException;
import com.example.springwithhibernate.service.AuthService;
import com.example.springwithhibernate.service.DefaultMailService;
import com.example.springwithhibernate.service.UserService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserView {
    private static Logger logger = LogManager.getLogger();
    private UserService userService;
    private AuthService authService;
    private DefaultMailService mailService;

    public UserView(UserService userService, AuthService authService, DefaultMailService mailService) {
        this.userService = userService;
        this.authService = authService;
        this.mailService = mailService;
    }

    @GetMapping("/users")
    public String get(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        logger.log(Level.INFO, "Users view GET");
        return "index";
    }

    @GetMapping("/signup")
    public String signupPage(Model model) {
        return "signup";
    }

    @PostMapping(value = "/signupUser", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String registerUser(UserCreationDto dto, Model model) {
        try {
            logger.log(Level.INFO, dto.toString());
            UserEntity user = authService.registerUser(dto.toUserEntity(), dto.getFile());
            mailService.sendRegisterEmail(user);
        } catch (UserAlreadyExistsException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        } catch (Exception e) {
            logger.log(Level.ERROR, e.toString());
        }
        return "redirect:/users";
    }
}
