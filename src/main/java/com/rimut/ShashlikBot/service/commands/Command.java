package com.rimut.ShashlikBot.service.commands;

import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

public abstract class Command {
    public abstract boolean exist(String str);
    public abstract String explanation();
    public abstract SendMessage process(Update update);
    public SendMessage prepareAndSendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        return message;
    }
}
