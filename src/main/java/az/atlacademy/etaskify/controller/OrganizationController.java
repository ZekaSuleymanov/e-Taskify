package az.atlacademy.etaskify.controller;


import az.atlacademy.etaskify.dto.request.OrganizationRequest;
import az.atlacademy.etaskify.dto.request.TaskRequest;
import az.atlacademy.etaskify.dto.response.MessageResponse;
import az.atlacademy.etaskify.dto.response.TaskResponse;
import az.atlacademy.etaskify.service.OrganizationService;
import az.atlacademy.etaskify.service.impl.OrganizationServiceImpl;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/organization")
@RequiredArgsConstructor
@SecurityRequirement(name = "bearerAuth")
public class OrganizationController {

    private final OrganizationServiceImpl organizationServiceImpl;

    @PostMapping
    public ResponseEntity<MessageResponse> create(@RequestBody @Valid OrganizationRequest request) {
        return organizationServiceImpl.create(request);
    }

    @PostMapping("/task")
    public ResponseEntity<MessageResponse> createTask(@RequestBody @Valid TaskRequest request){
        return organizationServiceImpl.createTask(request);
    }

    @GetMapping("/task")
    public ResponseEntity<Page<TaskResponse>> getAllAvailableTask(@RequestParam(required = false,defaultValue = "0") int pageNumber,
                                                                  @RequestParam(required = false,defaultValue = "10") int pageSize){
        return organizationServiceImpl.getAllAvailableTask(pageNumber,pageSize);
    }


}
