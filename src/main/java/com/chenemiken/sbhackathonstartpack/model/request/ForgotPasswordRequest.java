package com.chenemiken.sbhackathonstartpack.model.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class ForgotPasswordRequest {

    @NotEmpty(message = "please enter your email address")
    private String email;
}
