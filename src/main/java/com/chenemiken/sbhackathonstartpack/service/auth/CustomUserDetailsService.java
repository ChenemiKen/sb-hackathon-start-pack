package com.chenemiken.sbhackathonstartpack.service.auth;

import com.chenemiken.sbhackathonstartpack.dto.UserDto;
import com.chenemiken.sbhackathonstartpack.entity.User;
import com.chenemiken.sbhackathonstartpack.exceptions.SignupValidationException;
import com.chenemiken.sbhackathonstartpack.model.request.ResetPasswordRequest;
import com.chenemiken.sbhackathonstartpack.repository.auth.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.security.Principal;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Log logger = LogFactory.getLog(CustomUserDetailsService.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }

    public void createUser(HttpServletRequest request, UserDto newUser) throws SignupValidationException {
        if(userRepository.findByEmail(newUser.getEmail()).isPresent()){
            throw new SignupValidationException("email already registered.");
        }
        if(userRepository.findByUsername(newUser.getUsername()).isPresent()){
            throw new SignupValidationException("Username already in use please enter a different username.");
        }
        User user = UserDto.buildUser(newUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user = userRepository.save(user);
        Authentication authentication = new UsernamePasswordAuthenticationToken(user, user.getPassword());
        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(authentication);
        HttpSession session = request.getSession(true);
        session.setAttribute("SPRING_SECURITY_CONTEXT", sc);
    }

    public void resetPassword(ResetPasswordRequest data){
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if(!passwordEncoder.matches(data.getOldPassword(), user.getPassword())){
            throw new SecurityException("incorrect password");
        }
        user.setPassword(passwordEncoder.encode(data.getNewPassword()));
        userRepository.save(user);
    }
}
