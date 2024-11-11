package az.atlacademy.etaskify.service;

import az.atlacademy.etaskify.config.AuthenticationDetails;
import az.atlacademy.etaskify.dto.request.LoginRequest;
import az.atlacademy.etaskify.dto.response.ExceptionResponse;
import az.atlacademy.etaskify.dto.response.TokenResponse;
import az.atlacademy.etaskify.entity.TokenEntity;
import az.atlacademy.etaskify.entity.UserEntity;
import az.atlacademy.etaskify.enums.TokenType;
import az.atlacademy.etaskify.exception.NotFoundException;
import az.atlacademy.etaskify.exception.UserNotFoundException;
import az.atlacademy.etaskify.repository.TokenRepository;
import az.atlacademy.etaskify.repository.UserRepository;
import az.atlacademy.etaskify.service.impl.AuthenticationServiceImpl;
import az.atlacademy.etaskify.util.TokenUtil;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements AuthenticationServiceImpl {
    private final AuthenticationManager authenticationManager;
    private final TokenUtil tokenUtil;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final TokenRepository tokenRepository;
    @Override
    public ResponseEntity<TokenResponse> login(LoginRequest request) {
        UsernamePasswordAuthenticationToken authentication = (UsernamePasswordAuthenticationToken) authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );
        AuthenticationDetails details = (AuthenticationDetails) authentication.getDetails();

        Map<String, Object> authorities = getAuthorities(authentication.getAuthorities());
        authorities.put("orgId",details.getOrganizationId());

        String jasonWebToken = tokenUtil.generateToken(details.getUsername(), details.getUserId(), authorities);

        UserEntity userEntity = getUserById(details.getUserId());

        String refreshToken = tokenService.generateAndSaveRefreshToken(userEntity);

        return new ResponseEntity<>(TokenResponse.of(jasonWebToken, refreshToken), HttpStatus.OK);
    }
    @Override
    public ResponseEntity<TokenResponse> tokenByRefreshToken(String token) {
        TokenEntity findedToken = tokenRepository.findByTokenTypeAndNameAndAvailable(TokenType.REFRESH, token, true).orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of("NOT_FOUND", HttpStatus.NOT_FOUND), token));

        findedToken.isUsableOrElseThrow();

        UserEntity userEntity = findedToken.getUserEntity();
        String refreshToken = tokenService.generateAndSaveRefreshToken(userEntity);
        Map<String, Object> extraClaims = getAuthorities(userEntity.getAuthorities());

        extraClaims.put("orgId",userEntity.getOrganizationEntity().getId());

        String jasonWebToken = tokenUtil.generateToken(userEntity.getEmail(), userEntity.getId(), extraClaims);

        return new ResponseEntity<>(TokenResponse.of(jasonWebToken, refreshToken), HttpStatus.OK);
    }
    private Map<String, Object> getAuthorities(Collection<? extends GrantedAuthority> grantedAuthorities) {

        Map<String, Object> extraClaims = new HashMap<>();
        List<String> authorities = grantedAuthorities.stream()
                .map(GrantedAuthority::getAuthority)
                .toList();

        extraClaims.put("authorities", authorities);
        return extraClaims;
    }
    private UserEntity getUserById(long id){
        return  userRepository.findById(id).orElseThrow(() -> UserNotFoundException.of(ExceptionResponse.of("NOT_FOUND", HttpStatus.NOT_FOUND)));

    }
}