package com.kuklin.webrisetest.models.mappers;

import com.kuklin.webrisetest.entities.Subscription;
import com.kuklin.webrisetest.models.SubscriptionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class, ServicePlanMapper.class})
public interface SubscriptionMapper {
    SubscriptionDto toDto(Subscription entity);
    Subscription toEntity(SubscriptionDto dto);

    List<SubscriptionDto> toDtoList(List<Subscription> entities);
    List<Subscription> toEntityList(List<SubscriptionDto> dtos);
}
