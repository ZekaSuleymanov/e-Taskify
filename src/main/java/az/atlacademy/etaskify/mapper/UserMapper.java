package az.atlacademy.etaskify.mapper;


import az.atlacademy.etaskify.dto.request.UserRequest;
import az.atlacademy.etaskify.dto.response.UserResponse;
import az.atlacademy.etaskify.entity.UserEntity;
import org.apache.catalina.User;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;


@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy =ReportingPolicy.IGNORE )
public interface UserMapper {

    @Mappings({
            @Mapping(target = "id",ignore = true),
            @Mapping(target = "organization",ignore = true),
            @Mapping(target = "role",ignore = true),
            @Mapping(target = "enabled",ignore = true),
            @Mapping(target = "password",ignore = true),
    })
    UserEntity map(UserRequest request, @MappingTarget UserEntity userEntity);
    UserResponse map(UserEntity userEntity);



    UserRequest toDto (UserEntity userEntity);
    UserEntity toEntity(UserRequest userRequest);

}
