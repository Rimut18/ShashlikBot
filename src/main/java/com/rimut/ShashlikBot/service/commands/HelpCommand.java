package com.rimut.ShashlikBot.service.commands;


import com.rimut.ShashlikBot.service.UserService;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

@Service("/help")
public class HelpCommand extends Command {
    private final UserService userService;

    public HelpCommand(UserService userService) {
        this.userService = userService;
    }

    static final String HELP_TEXT = "This bot is created to demonstrate Spring capabilities.\n\n" +
            "You can execute commands from the main menu on the left or by typing a command:\n\n" +
            "Type /start to see a welcome message\n\n" +
            "Type /sticker to send sticker\n\n" +
            "Type /help to see this message again\n\n" +
            "Type /register to register";

    @Override
    public boolean exist(String str) {
        return str.equals("/help");
    }

    @Override
    public String explanation() {
        return "info how to use this bot";
    }

    @Override
    public SendMessage process(Update update) {
        long chatId = update.getMessage().getFrom().getId();
        return prepareAndSendMessage(chatId, HELP_TEXT);
    }

}
