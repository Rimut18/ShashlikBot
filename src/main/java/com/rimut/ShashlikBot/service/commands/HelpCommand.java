package com.rimut.ShashlikBot.service.commands;


import com.rimut.ShashlikBot.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.rimut.ShashlikBot.service.TelegramBot.helpText;

@Service("/help")
public class HelpCommand extends Command {
    private final UserService userService;

    public HelpCommand(UserService userService) {
        this.userService = userService;
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
        return prepareAndSendMessage(chatId, String.valueOf(helpText));
    }

}
