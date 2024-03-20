package com.rimut.ShashlikBot.service;

import com.rimut.ShashlikBot.model.User;
import com.rimut.ShashlikBot.model.UserRepository;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Message;
import java.sql.Timestamp;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Optional<User> findById(Long id){
        return userRepository.findById(id);
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public void registerUser(Message msg) {
        if(findById(msg.getChatId()).isEmpty()){
            var chatId = msg.getChatId();
            var chat = msg.getChat();
            User user = new User();
            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));
            userRepository.save(user);
        }
    }
}
