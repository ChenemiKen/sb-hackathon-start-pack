package com.chenemiken.sbhackathonstartpack.controller.auth;

import com.chenemiken.sbhackathonstartpack.service.auth.CustomUserDetailsService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.bytecode.internal.bytebuddy.PassThroughInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class AuthController {
    private static final Log logger = LogFactory.getLog(AuthController.class);
    @Autowired
    CustomUserDetailsService userDetailsService;

    @GetMapping(path = "/signup")
    public String getSignup(){return "signup";}

    @PostMapping(path = "/signup")
    public ResponseEntity<String> postSignup(){
        logger.info("signup request");
        return new ResponseEntity<>("userDetailsService.createUser(user)", HttpStatus.CREATED);
    }

    @GetMapping(path = "/start")
    public ResponseEntity<String> start(){
        return new ResponseEntity<>("start", HttpStatus.OK);
    }
}
