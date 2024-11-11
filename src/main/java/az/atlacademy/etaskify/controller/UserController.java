package az.atlacademy.etaskify.controller;

import az.atlacademy.etaskify.dto.request.PasswordRequest;
import az.atlacademy.etaskify.dto.request.UserRequest;
import az.atlacademy.etaskify.dto.response.MessageResponse;
import az.atlacademy.etaskify.dto.response.UserResponse;
import az.atlacademy.etaskify.service.impl.UserServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class UserController {

    private final UserServiceImpl userServiceImpl;
    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid UserRequest request){
        return userServiceImpl.create(request);
    }
    @PatchMapping
    public ResponseEntity<MessageResponse> confirm(@RequestParam String token,
                                                   @RequestBody @Valid PasswordRequest request){
        return userServiceImpl.confirm(request,token);
    }
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllOrgUser(){
        return userServiceImpl.getAllOrganizationUser();
    }

}