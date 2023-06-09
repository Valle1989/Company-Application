package com.fvalle.company.security.auth;

import com.fvalle.company.security.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @NotBlank
    @Size(max = 30)
    private String firstname;
    @NotBlank
    @Size(max = 30)
    private String lastname;
    @Email
    @NotBlank
    @Size(max = 80)
    private String email;
    @NotBlank
    private String password;
    private Role role;
}
