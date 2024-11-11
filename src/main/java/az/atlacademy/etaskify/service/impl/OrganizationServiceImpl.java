package az.atlacademy.etaskify.service.impl;

import az.atlacademy.etaskify.dto.request.OrganizationRequest;
import az.atlacademy.etaskify.dto.request.TaskRequest;
import az.atlacademy.etaskify.dto.response.MessageResponse;
import az.atlacademy.etaskify.dto.response.TaskResponse;
import az.atlacademy.etaskify.entity.OrganizationEntity;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

public interface OrganizationServiceImpl {

    ResponseEntity<MessageResponse> create(OrganizationRequest request);

    ResponseEntity<MessageResponse> createTask(TaskRequest request);

    ResponseEntity<Page<TaskResponse>> getAllAvailableTask(int pageNumber, int pageSize);
    OrganizationEntity getById(long id);

}
