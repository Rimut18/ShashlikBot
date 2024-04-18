package com.rimut.ShashlikBot.commands;

import com.rimut.ShashlikBot.service.UserService;
import com.rimut.ShashlikBot.service.commands.RegisterCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class RegisterCommandTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private RegisterCommand registerCommand;


    @Test
    void registerCommandExist(){
        assertTrue(registerCommand.exist("/register"));
    }
    @Test
    void notregisterCommandNotExist(){
        assertFalse(registerCommand.exist("register"));
    }

    Update getUpdate(){
        Update update = new Update();
        Message message = new Message();
        message.setFrom(new User(480783437L, "Тимур", false));
        update.setMessage(message);
        return update;
    }

    @Test
    void process(){
        String expectedHelpText = "Do you really want to register?";

        Update update = getUpdate();
        SendMessage actualResult = registerCommand.process(update);

        assertEquals(expectedHelpText, actualResult.getText());
        assertEquals("480783437", actualResult.getChatId());
    }

}
