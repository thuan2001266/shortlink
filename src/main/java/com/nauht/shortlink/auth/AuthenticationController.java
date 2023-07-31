package com.nauht.shortlink.auth;

import com.nauht.shortlink.ValidateForm.ValidateForm;
import com.nauht.shortlink.config.LogoutService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

  private final AuthenticationService service;

  private final LogoutService logoutService;

  @PostMapping("/register")
  public ResponseEntity<?> register(
      @RequestBody @Valid RegisterRequest request, BindingResult bindingResult
  ) {
    var validateResult = ValidateForm.validate(bindingResult);
    if (validateResult != null) {
      return validateResult;
    }
    return service.register(request);
  }
  @PostMapping("/authenticate")
  public ResponseEntity<?> authenticate(
      @RequestBody @Valid AuthenticationRequest request, BindingResult bindingResult
  ) {
    var validateResult = ValidateForm.validate(bindingResult);
    if (validateResult != null) {
      return validateResult;
    }
    return service.authenticate(request);
  }

  @PostMapping("/refresh-token")
  public ResponseEntity<AuthenticationResponse> refreshToken(@RequestHeader("Authorization") String authorizationHeader
  ) throws IOException {
    return service.refreshToken(authorizationHeader);
  }

  @PostMapping("/logout")
  public ResponseEntity<HttpStatus> logout(@RequestHeader("Authorization") String authorizationHeader
  ) throws IOException {
    return logoutService.logout(authorizationHeader);
  }


}
