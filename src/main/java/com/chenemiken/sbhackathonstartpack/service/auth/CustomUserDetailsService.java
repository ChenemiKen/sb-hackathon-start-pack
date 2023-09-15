package com.chenemiken.sbhackathonstartpack.service.auth;

import com.chenemiken.sbhackathonstartpack.controller.auth.AuthController;
import com.chenemiken.sbhackathonstartpack.dto.UserDto;
import com.chenemiken.sbhackathonstartpack.entity.User;
import com.chenemiken.sbhackathonstartpack.repository.auth.UserRepository;
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
        return userRepository.findByUsernameOrEmail(username, username)
                .orElseThrow(() -> new UsernameNotFoundException("user not found"));
    }

    public void createUser(UserDto newUser){
        User user = UserDto.buildUser(newUser);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }
}
