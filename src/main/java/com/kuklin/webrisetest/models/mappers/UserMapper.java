package com.kuklin.webrisetest.models.mappers;

import com.kuklin.webrisetest.entities.User;
import com.kuklin.webrisetest.models.UserDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserDto toDto(User entity);
    User toEntity(UserDto dto);

    List<UserDto> toDtoList(List<User> entities);
    List<User> toEntityList(List<UserDto> dtos);
}
