package com.rimut.ShashlikBot.service.commands;

import com.rimut.ShashlikBot.service.RestToDb;
import com.rimut.ShashlikBot.service.TelegramBot;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendSticker;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service("/sticker")
public class StickerCommand extends Command {
    private TelegramBot bot;
    public StickerCommand() {
    }

    public void setBot(TelegramBot bot) {
        this.bot = bot;
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
    public String description() {
        return "to send sticker";
    }

    @Override
    public SendMessage process(Update update) {
        long chatId = update.getMessage().getFrom().getId();
        try {
            bot.execute(sendSticker(chatId));
        } catch (TelegramApiException e) {
        }
        return prepareAndSendMessage(chatId, "");
    }

    private SendSticker sendSticker(long chatId) {
        SendSticker sticker = new SendSticker();
        sticker.setChatId(String.valueOf(chatId));
        InputFile file = new InputFile("CAACAgIAAxkBAAEDpytl1eLGgIBZG_KlTIlMHQVFdeDdrwACYAQAAmvEygp-n8t1YZBpMzQE");
        sticker.setSticker(file);
        return sticker;
    }
}
