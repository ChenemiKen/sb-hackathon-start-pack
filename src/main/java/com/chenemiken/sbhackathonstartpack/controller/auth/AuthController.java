package com.chenemiken.sbhackathonstartpack.controller.auth;

import com.chenemiken.sbhackathonstartpack.dto.UserDto;
import com.chenemiken.sbhackathonstartpack.model.request.ForgotPasswordRequest;
import com.chenemiken.sbhackathonstartpack.service.auth.CustomUserDetailsService;
import jakarta.validation.Valid;
import jakarta.validation.Validation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AuthController {
    private static final Log logger = LogFactory.getLog(AuthController.class);
    @Autowired
    CustomUserDetailsService userDetailsService;

    @GetMapping(path = "/signup")
    public String getSignup(@ModelAttribute("user") UserDto user){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "signup";
        }
        return "redirect:/";
    }

    @PostMapping(path = "/signup")
    public String postSignup(@ModelAttribute(value = "user") @Valid UserDto user, BindingResult bindingResult){
        logger.info("signup request: " + user);
        if(bindingResult.hasErrors()){
            return "signup";
        }
        try {
            userDetailsService.createUser(user);
        }catch (Exception e){
            bindingResult.reject("", e.getMessage());
            return "signup";
        }
        return "redirect:/login";
    }

    @GetMapping(path = "/login")
    public String getLogin(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "login";
        }
        return "redirect:/";
    }

    @GetMapping(path = "/forgot-password")
    public String getForgotPassword(@ModelAttribute("user")ForgotPasswordRequest request){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || authentication instanceof AnonymousAuthenticationToken){
            return "forgotPassword";
        }
        return "redirect:/reset-password";
    }

    @PostMapping(path = "/forgot-password")
    public String postForgotPassword(@Valid ForgotPasswordRequest user, BindingResult bindingResult){
        bindingResult.hasErrors();
        return "forgotPassword";
    }

    @GetMapping(path = "/reset-password")
    public String getResetPassword(){
        return "resetPassword";
    }

    @GetMapping(path = "/start")
    public ResponseEntity<String> start(){
        return new ResponseEntity<>("start", HttpStatus.OK);
    }
}
