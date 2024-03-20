package com.rimut.ShashlikBot.service.commands;


import com.rimut.ShashlikBot.service.TelegramBot;
import com.rimut.ShashlikBot.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service("/help")
public class HelpCommand extends Command {
    private final TelegramBot bot;
    public HelpCommand(@Lazy TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public boolean exist(String str) {
        return str.equals("/help");
    }

    @Override
    public String explanation() {
        return "info how to use this bot";
    }

    @Override
    public String description() {
        return "to see this message again";
    }

    @Override
    public SendMessage process(Update update) {
        long chatId = update.getMessage().getFrom().getId();
        return prepareAndSendMessage(chatId, bot.getHelpText());
    }

}
