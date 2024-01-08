package com.chenemiken.sbhackathonstartpack.model.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
public class ResetPasswordRequest {
    @NotBlank(message = "please enter your old password")
    String oldPassword;

    @NotBlank(message = "please enter a new secure password")
    @Length(min = 8, message = "password must be at least 8 characters")
    String newPassword;

    @NotBlank(message = "please enter your new password again")
    @Length(min = 8, message = "password must be at least 8 characters")
    String confirmPassword;
}
