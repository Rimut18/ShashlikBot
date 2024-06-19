package com.rimut.db.microservice.mappers;

import com.rimut.db.microservice.dtos.UserDto;
import com.rimut.db.microservice.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper extends BaseMapper<User, UserDto> {
    @Override
    User ToEntity(UserDto userDto);

    @Override
    UserDto ToDto(User user);
}
