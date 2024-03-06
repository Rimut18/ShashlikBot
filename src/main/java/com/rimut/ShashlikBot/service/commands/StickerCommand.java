package com.rimut.ShashlikBot.service.commands;

import com.rimut.ShashlikBot.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service("/sticker")
public class StickerCommand extends Command {
    private final UserService userService;

    public StickerCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean exist(String str) {
        return str.equals("/sticker");
    }

    @Override
    public String explanation() {
        return "send sticker";
    }

    @Override
    public SendMessage process(Update update) {
        long chatId = update.getMessage().getFrom().getId();
        sendSticker(chatId);
        return prepareAndSendMessage(chatId, "Sticker!!!");
    }

    private void sendSticker(long chatId) {
        SendSticker sticker = new SendSticker();
        sticker.setChatId(String.valueOf(chatId));
        InputFile file = new InputFile("CAACAgIAAxkBAAEDpytl1eLGgIBZG_KlTIlMHQVFdeDdrwACYAQAAmvEygp-n8t1YZBpMzQE");
        sticker.setSticker(file);
        //Не понимаю как передать SendSticker дальше или как вызвать в этом методе execute(sticker)
    }

}
