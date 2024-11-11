package az.atlacademy.etaskify.service.impl;

import az.atlacademy.etaskify.dto.request.LoginRequest;
import az.atlacademy.etaskify.dto.response.TokenResponse;
import org.springframework.http.ResponseEntity;

public interface AuthenticationServiceImpl {

    ResponseEntity<TokenResponse> login(LoginRequest request);
    ResponseEntity<TokenResponse> tokenByRefreshToken(String token);

}
