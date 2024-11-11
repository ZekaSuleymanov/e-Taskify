package az.atlacademy.etaskify.mapper;

import az.atlacademy.etaskify.dto.request.TaskRequest;
import az.atlacademy.etaskify.dto.response.TaskResponse;
import az.atlacademy.etaskify.entity.TaskEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.springframework.scheduling.config.Task;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TaskMapper {

    @Mapping(target = "users",ignore = true)
    TaskEntity map(TaskRequest request, @MappingTarget TaskEntity taskEntity);
    TaskResponse map(TaskEntity taskEntity);


    TaskRequest toDto(TaskEntity taskEntity);
    TaskEntity toEntity(TaskRequest taskRequest);


}
