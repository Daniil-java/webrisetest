package com.kuklin.webrisetest.models.mappers;

import com.kuklin.webrisetest.entities.ServicePlan;
import com.kuklin.webrisetest.models.ServicePlanDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServicePlanMapper {
    ServicePlanDto toDto(ServicePlan entity);
    ServicePlan toEntity(ServicePlanDto dto);

    List<ServicePlanDto> toDtoList(List<ServicePlan> entities);
    List<ServicePlan> toEntityList(List<ServicePlanDto> dtos);
}
