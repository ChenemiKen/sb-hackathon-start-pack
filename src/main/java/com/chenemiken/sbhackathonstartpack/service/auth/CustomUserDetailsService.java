package com.chenemiken.sbhackathonstartpack.service.auth;

import com.chenemiken.sbhackathonstartpack.controller.auth.AuthController;
import com.chenemiken.sbhackathonstartpack.dto.UserDto;
import com.chenemiken.sbhackathonstartpack.entity.User;
import com.chenemiken.sbhackathonstartpack.exceptions.SignupValidationException;
import com.chenemiken.sbhackathonstartpack.repository.auth.UserRepository;
import jakarta.validation.ConstraintViolationException;
import lombok.SneakyThrows;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {
    private static final Log logger = LogFactory.getLog(AuthController.class);
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("username not found"));
    }

    public void createUser(UserDto newUser) throws SignupValidationException {
        if(userRepository.findByEmail(newUser.getEmail()).isPresent()){
            throw new SignupValidationException("email already registered.");
        }
        if(userRepository.findByUsername(newUser.getUsername()).isPresent()){
            throw new SignupValidationException("Username already in use please enter a different username.");
        }
        User user = UserDto.buildUser(newUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
