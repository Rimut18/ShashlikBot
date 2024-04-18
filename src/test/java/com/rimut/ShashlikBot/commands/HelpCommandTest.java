package com.rimut.ShashlikBot.commands;

import com.rimut.ShashlikBot.service.TelegramBot;
import com.rimut.ShashlikBot.service.commands.HelpCommand;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class HelpCommandTest {
    @Mock
    private TelegramBot bot;
    @InjectMocks
    private HelpCommand helpCommand;

    Update getUpdate(){
        Update update = new Update();
        Message message = new Message();
        message.setFrom(new User(480783437L, "Тимур", false));
        message.setText(bot.getHelpText());
        update.setMessage(message);
        return update;
    }
    @Test
    void process(){
        String expectedHelpText = "Help";
        when(bot.getHelpText()).thenReturn(expectedHelpText);

        Update update = getUpdate();
        SendMessage actualResult = helpCommand.process(update);

        assertEquals(expectedHelpText, actualResult.getText());
        assertEquals("480783437", actualResult.getChatId());
    }
}
