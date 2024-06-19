package com.rimut.shashlikbot.service;

import com.rimut.db.microservice.dtos.UserDto;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.telegram.telegrambots.meta.api.objects.Message;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class RestToDb {
    private final RestTemplate restTemplate;

    public RestToDb(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void sendToDb(Message msg) {
        var chatId = msg.getChatId();
        ResponseEntity<UserDto> response = restTemplate.getForEntity("http://localhost:8082/users/" + chatId, UserDto.class);
        UserDto user = response.getBody();
        if (response.getStatusCode().is4xxClientError()) {
            var chat = msg.getChat();
            user = new UserDto();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            restTemplate.postForEntity("http://localhost:8082/users", user, UserDto.class);
        }
    }

    public List<UserDto> getAllUsers() {
        ResponseEntity<UserDto[]> response = restTemplate.getForEntity("http://localhost:8082/users", UserDto[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}
