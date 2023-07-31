package com.nauht.shortlink.auth;

import com.nauht.shortlink.user.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
  private String firstname;
  private String lastname;
  @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
  private String email;
  @NotEmpty(message = "Password cannot be empty")
  private String password;
  @NotEmpty(message = "Captcha cannot be empty")
  String captcha;
  private Role role;
}
