package com.chenemiken.sbhackathonstartpack.controller.auth;

import com.chenemiken.sbhackathonstartpack.dto.UserDto;
import com.chenemiken.sbhackathonstartpack.entity.User;
import com.chenemiken.sbhackathonstartpack.service.auth.CustomUserDetailsService;
import jakarta.validation.Valid;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private static final Log logger = LogFactory.getLog(AuthController.class);
    @Autowired
    CustomUserDetailsService userDetailsService;

    @GetMapping(path = "/signup")
    public String getSignup(){
        return "signup";
    }

    @PostMapping(path = "/signup")
    public String postSignup(@Valid UserDto user, BindingResult bindingResult){
        logger.info("signup request: " + user);
        if(bindingResult.hasErrors()) return "signup";
        return new ResponseEntity<>(userDetailsService.createUser(user).toString(), HttpStatus.CREATED).toString();
    }

    @GetMapping(path = "/start")
    public ResponseEntity<String> start(){
        return new ResponseEntity<>("start", HttpStatus.OK);
    }
}
