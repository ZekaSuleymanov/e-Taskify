package az.atlacademy.etaskify.service.impl;

import az.atlacademy.etaskify.dto.request.PasswordRequest;
import az.atlacademy.etaskify.dto.request.UserRequest;
import az.atlacademy.etaskify.dto.response.MessageResponse;
import az.atlacademy.etaskify.dto.response.UserResponse;
import az.atlacademy.etaskify.entity.UserEntity;
import org.apache.catalina.User;
import org.springframework.http.ResponseEntity;

import java.util.List;



public interface UserServiceImpl{

    ResponseEntity<MessageResponse> create(UserRequest request);

    ResponseEntity<MessageResponse> confirm(PasswordRequest request, String token);
    List<UserEntity> findAllById(List<Long> id);
    long getOrgId();

    ResponseEntity<List<UserResponse>> getAllOrganizationUser();

}
