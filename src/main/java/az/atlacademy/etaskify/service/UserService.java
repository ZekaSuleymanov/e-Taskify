package az.atlacademy.etaskify.service;


import az.atlacademy.etaskify.config.AuthenticationDetails;
import az.atlacademy.etaskify.dto.request.PasswordRequest;
import az.atlacademy.etaskify.dto.request.UserRequest;
import az.atlacademy.etaskify.dto.response.ExceptionResponse;
import az.atlacademy.etaskify.dto.response.MessageResponse;
import az.atlacademy.etaskify.dto.response.UserResponse;
import az.atlacademy.etaskify.entity.OrganizationEntity;
import az.atlacademy.etaskify.entity.Role;
import az.atlacademy.etaskify.entity.TokenEntity;
import az.atlacademy.etaskify.entity.UserEntity;
import az.atlacademy.etaskify.enums.TokenType;
import az.atlacademy.etaskify.exception.NotFoundException;
import az.atlacademy.etaskify.mapper.UserMapper;
import az.atlacademy.etaskify.repository.OrganizationRepository;
import az.atlacademy.etaskify.repository.RoleRepository;
import az.atlacademy.etaskify.repository.TokenRepository;
import az.atlacademy.etaskify.repository.UserRepository;
import az.atlacademy.etaskify.service.impl.UserServiceImpl;
import az.atlacademy.etaskify.util.EmailUtil;
import az.atlacademy.etaskify.util.MessageUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@RequiredArgsConstructor

public class UserService implements UserServiceImpl{

    private final MessageUtil messageUtil;
    private final TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleRepository roleRepository;
    private final EmailUtil emailUtil;
    private final EmailService emailService;
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    @Override
    @Transactional
    public ResponseEntity<MessageResponse> create(UserRequest userRequest) {
        OrganizationEntity organizationEntity = organizationRepository.findById(getOrgId()).orElseThrow(()->
                NotFoundException.of(ExceptionResponse.of("NOT_SAME_PASSWORD", HttpStatus.NOT_FOUND),getOrgId()));

        Role roleUser = roleRepository.findByName("ROLE_USER").orElseThrow(() ->
                NotFoundException.of(ExceptionResponse.of("NOT_FOUND", HttpStatus.NOT_FOUND), "ROLE_USER"));

        UserEntity userEntity = userMapper.map(userRequest, new UserEntity(organizationEntity, roleUser));

        userRepository.save(userEntity);

        String confirmationToken = tokenService.generateAndSaveConfirmationToken(userEntity);

        String subject = emailUtil.confirmEmailSubjectBuilder(confirmationToken,userEntity.getUsername());

        emailService.sendTo(userEntity.getEmail(),subject);

        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage("MESSAGE_SUCCESSFULLY","ROLE_USER" ),HttpStatus.NOT_FOUND));
    }

    @Override
    @Transactional
    public ResponseEntity<MessageResponse> confirm(PasswordRequest request, String token) {
        TokenEntity foundedToken = tokenRepository.findByTokenTypeAndNameAndAvailable(TokenType.CONFIRMATION, token, true).orElseThrow(() -> NotFoundException.of(ExceptionResponse.of(
                "NOT_FOUND",HttpStatus.NOT_FOUND), token));
        foundedToken.isUsableOrElseThrow();

        UserEntity userEntity = foundedToken.getUserEntity();
        userEntity.setPassword(passwordEncoder.encode(request.getPassword()));
        userEntity.setEnabled(true);
        foundedToken.unusable();
        return ResponseEntity.ok(MessageResponse.of(messageUtil.getMessage("MESSAGE_SUCCESSFULLY","ROLE_USER"), HttpStatus.NOT_FOUND));
    }

    public List<UserEntity> findAllById(List<Long> id){
        return userRepository.findAllById(id);
    }
    public long getOrgId(){
        return ((AuthenticationDetails) SecurityContextHolder.getContext().getAuthentication().getDetails()).getOrganizationId();
    }

    @Override
    public ResponseEntity<List<UserResponse>> getAllOrganizationUser() {
        OrganizationEntity organizationEntity = organizationRepository.findById(getOrgId()).orElseThrow(() -> NotFoundException.of(ExceptionResponse.of("NOT_FOUND", HttpStatus.NOT_FOUND),"ROLE_USER"));
        List<UserEntity> allUsers = userRepository.findByOrganizationAndEnabled(organizationEntity, true);
        List<UserResponse> userResponses = allUsers.stream()
                .map(userMapper::map)
                .toList();
        return ResponseEntity.ok(userResponses);
    }



}
