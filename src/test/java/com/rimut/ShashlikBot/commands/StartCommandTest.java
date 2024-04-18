package com.rimut.ShashlikBot.commands;

import com.rimut.ShashlikBot.service.UserService;
import com.rimut.ShashlikBot.service.commands.Command;
import com.rimut.ShashlikBot.service.commands.StartCommand;
import com.vdurmont.emoji.EmojiParser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StartCommandTest {
    @Mock
    private UserService userService;

    @Mock
    List<Command> commands;

    @InjectMocks
    private StartCommand startCommand;


    @Test
    void startCommandExist(){
        assertTrue(startCommand.exist("/start"));
    }
    @Test
    void notStartCommandNotExist(){
        assertFalse(startCommand.exist("start"));
    }

    Update getUpdate(){
        Update update = new Update();
        Message message = new Message();
        message.setFrom(new User(480783437L, "Тимур", false));
        message.setChat(new Chat(480783437L, "private"));
        message.getChat().setFirstName("Тимур");
        update.setMessage(message);
        return update;
    }
    @Test
    void process(){
        String expectedStartText = EmojiParser.parseToUnicode("Hi, Тимур, nice to meet you! \n" +
                " Are you want a shashlik?" + " :star:" + " :star:" + " :star:");

        Update update = getUpdate();
        SendMessage actualResult = startCommand.process(update);

        verify(userService, times(1)).registerUser(update.getMessage());
        assertEquals(expectedStartText, actualResult.getText());
    }
}
