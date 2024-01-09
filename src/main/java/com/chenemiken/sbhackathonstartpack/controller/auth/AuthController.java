package com.chenemiken.sbhackathonstartpack.controller.auth;

import com.chenemiken.sbhackathonstartpack.dto.UserDto;
import com.chenemiken.sbhackathonstartpack.model.request.ForgotPasswordRequest;
import com.chenemiken.sbhackathonstartpack.model.request.ResetPasswordRequest;
import com.chenemiken.sbhackathonstartpack.service.auth.CustomUserDetailsService;
import jakarta.servlet.http.HttpServletRequest;
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
import org.springframework.ui.Model;
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
    public String postSignup(HttpServletRequest request, @ModelAttribute(value = "user") @Valid UserDto user,
                             BindingResult bindingResult){
        logger.info("signup request: " + user);
        if(bindingResult.hasErrors()){
            return "signup";
        }
        try {
            userDetailsService.createUser(request, user);
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
    public String postForgotPassword(@ModelAttribute(value = "user") @Valid ForgotPasswordRequest user,
                                     BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()) return "forgotPassword";
        try {
//            userDetailsService.handleForgotPassword(user);
            model.addAttribute("success",
                    "Success! please check your mail for a link to reset your password.");
        }catch (Exception e){
            bindingResult.reject("", e.getMessage());
        }

        return "forgotPassword";
    }

    @GetMapping(path = "/reset-password")
    public String getResetPassword(@ModelAttribute(value = "user") ResetPasswordRequest request){
        return "resetPassword";
    }

    @PostMapping(path = "/reset-password")
    public String postResetPassword(@ModelAttribute(value = "user") @Valid ResetPasswordRequest data,
                                    BindingResult bindingResult, HttpServletRequest request){
        logger.info("reset password request: " + data);
        if(bindingResult.hasErrors()){
            return "resetPassword";
        }

        try {
            userDetailsService.resetPassword(request, data);
        }catch (SecurityException e){
            bindingResult.reject("", e.getMessage());
            return "resetPassword";
        }

        return "redirect:/";
    }

    @GetMapping(path = "/start")
    public ResponseEntity<String> start(){
        return new ResponseEntity<>("start", HttpStatus.OK);
    }
}
