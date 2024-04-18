package com.rimut.ShashlikBot.commands;

import com.rimut.ShashlikBot.service.TelegramBot;
import com.rimut.ShashlikBot.service.UserService;
import com.rimut.ShashlikBot.service.commands.Command;
import com.rimut.ShashlikBot.service.commands.StickerCommand;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StickerCommandTest {
    @Mock
    private TelegramBot bot;
    @Mock
    UserService userService;
    @InjectMocks
    private StickerCommand stickerCommand;

    @Test
    void stickerCommandExist(){
        assertTrue(stickerCommand.exist("/sticker"));
    }
    @Test
    void notStickerCommandNotExist(){
        assertFalse(stickerCommand.exist("sticker"));
    }
    Update getUpdate(){
        Update update = new Update();
        Message message = new Message();
        message.setFrom(new User(480783437L, "Тимур", false));
        update.setMessage(message);
        return update;
    }
    @SneakyThrows
    @Test
    void process(){
        String expectedHelpText = "";

        Update update = getUpdate();
        stickerCommand.setBot(bot);
        SendMessage actualResult = stickerCommand.process(update);
        InputFile file = new InputFile("CAACAgIAAxkBAAEDpytl1eLGgIBZG_KlTIlMHQVFdeDdrwACYAQAAmvEygp-n8t1YZBpMzQE");

        verify(bot, times(1)).execute(new SendSticker("480783437", file));
        assertEquals(expectedHelpText, actualResult.getText());
        assertEquals("480783437", actualResult.getChatId());
    }
}
