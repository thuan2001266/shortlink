package com.nauht.shortlink.auth;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
  @Email(message = "Email is not valid", regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$")
  private String email;
  @NotEmpty(message = "Password cannot be empty")
  @Size(min = 6, message = "Password have at least 6 chars")
  String password;
  @NotEmpty(message = "Captcha cannot be empty")
  String captcha;
}
