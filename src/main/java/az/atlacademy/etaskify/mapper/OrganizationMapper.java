package az.atlacademy.etaskify.mapper;


import az.atlacademy.etaskify.dto.request.OrganizationRequest;
import az.atlacademy.etaskify.entity.OrganizationEntity;
import org.mapstruct.*;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING,
        unmappedSourcePolicy = ReportingPolicy.IGNORE,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrganizationMapper {

    @Mappings({
            @Mapping(target = "id",ignore = true),
    })
    OrganizationEntity map(OrganizationRequest request, @MappingTarget OrganizationEntity organization);

    OrganizationRequest toDto (OrganizationEntity organizationEntity);
    OrganizationEntity toEntity (OrganizationRequest organizationRequest);


}
