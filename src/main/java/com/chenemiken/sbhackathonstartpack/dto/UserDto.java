package com.chenemiken.sbhackathonstartpack.dto;

import com.chenemiken.sbhackathonstartpack.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    @NotBlank(message = "please enter your preferred username")
    private String username;

    @NotEmpty(message = "please enter your email address")
    @Email(message = "please enter a valid email address")
    private String email;

    @NotBlank(message = "please enter a secure password")
    @Length(min = 8, message = "password must be at least 8 characters")
    private String password;

    private Boolean enabled = true;

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", enabled='" + enabled + '\'' +
                '}';
    }

    public static User buildUser(UserDto dto){
        return User.builder()
            .username(dto.username)
            .email(dto.email)
            .password(dto.password)
            .enabled(dto.enabled)
            .build();
    }

    public static UserDto buildDto(User user){
        return UserDto.builder()
            .username(user.getUsername())
            .email(user.getEmail())
            .password(user.getPassword())
            .enabled(user.isEnabled())
            .build();
    }
}
