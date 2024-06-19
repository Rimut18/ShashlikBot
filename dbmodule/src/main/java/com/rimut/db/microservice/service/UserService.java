package com.rimut.db.microservice.service;

import com.rimut.db.microservice.dtos.UserDto;
import com.rimut.db.microservice.mappers.UserMapper;
import com.rimut.db.microservice.repository.UserRepository;
import com.rimut.db.microservice.model.User;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService<M extends UserMapper> {

    @Autowired
    private final UserRepository userRepository;

    private final M mapper;
    public UserService(UserRepository userRepository, M mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }
    public User findById(Long id){
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Entity with id %s not found", id)));
    }

    public UserDto getOne(Long id) {
         return toUserDto(findById(id));
    }

    public List<UserDto> findAll() {
        return userRepository.findAll().stream()
                .map(this::toUserDto)
                .collect(Collectors.toList());
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
    public UserDto registerUser(UserDto userDto){
        userRepository.save(this.toUser(userDto));
        return userDto;
    }

    public UserDto toUserDto(User user) {
        return mapper.ToDto(user);
    }

    public User toUser(UserDto userDto) {
        return mapper.ToEntity(userDto);
    }
}