package com.rimut.ShashlikBot.service;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.rimut.db.microservice.model.User;
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
        ResponseEntity<User> response = restTemplate.getForEntity("http://localhost:8082/users/" + chatId, User.class);
        User user = response.getBody();
        if (user == null) {
            var chat = msg.getChat();
            user = new User();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            restTemplate.postForEntity("http://localhost:8082/users", user, User.class);
        }

    }

    public List<User> getAllUsers() {
        ResponseEntity<User[]> response = restTemplate.getForEntity("http://localhost:8082/users", User[].class);
        return Arrays.asList(Objects.requireNonNull(response.getBody()));
    }
}
