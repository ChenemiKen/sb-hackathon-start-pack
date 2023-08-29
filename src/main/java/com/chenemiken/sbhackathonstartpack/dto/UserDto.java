package com.chenemiken.sbhackathonstartpack.dto;

import com.chenemiken.sbhackathonstartpack.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Builder
@AllArgsConstructor
public class UserDto {
    @NotNull
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    private String password;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

    public static User buildUser(UserDto dto){
        return User.builder()
                .username(dto.username)
                .email(dto.email)
                .password(dto.password)
                .build();
    }

    public static UserDto buildDto(User user){
        return UserDto.builder()
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }
}
