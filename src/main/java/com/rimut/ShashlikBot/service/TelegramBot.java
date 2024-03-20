package com.rimut.ShashlikBot.service;

import com.rimut.ShashlikBot.config.BotConfig;
import com.rimut.ShashlikBot.model.User;
import com.rimut.ShashlikBot.service.commands.Command;
import com.rimut.ShashlikBot.service.commands.StickerCommand;
import com.vdurmont.emoji.EmojiParser;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import java.util.ArrayList;
import java.util.List;

@Component
public class TelegramBot extends TelegramLongPollingBot {

    private final UserService userService;
    private final List<Command> commands;
    @Autowired
    private StickerCommand stickerCommand;
    private final BotConfig config;
    private static String HELP_TEXT;
    static final String YES_BUTTON = "YES_BUTTON";
    static final String NO_BUTTON = "NO_BUTTON";
    @Autowired
    public TelegramBot(UserService userService, List<Command> commands, BotConfig config) {
        this.userService = userService;
        this.commands = commands;
        this.config = config;
        StringBuilder helpText = new StringBuilder();
        List<BotCommand> listOfCommands = new ArrayList<>();
        helpText.append("This bot is created to demonstrate Spring capabilities.\n\n" +
                "You can execute commands from the main menu on the left or by typing a command:\n\n");
        commands.forEach(clazz -> {
            String name = clazz.getClass().getAnnotation(Service.class).value();
            BotCommand botCommand = new BotCommand(name, clazz.explanation());
            listOfCommands.add(botCommand);
            helpText.append("Type " + name + " " + clazz.description());
            helpText.append("\n\n");
        });
        HELP_TEXT = helpText.toString();
        try {
            this.execute(new SetMyCommands(listOfCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {

        }
    }
    @PostConstruct
    public void init() {
        stickerCommand.setBot(this);
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    public String getHelpText() {
        return HELP_TEXT;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if(update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getFrom().getId();
            if(messageText.contains("/send") && config.getOwnerId() == chatId) {
                var textToSend = EmojiParser.parseToUnicode(messageText.substring(messageText.indexOf(" ")));
                var users = userService.findAll();
                for (User user : users){
                    executeMessage(prepareAndSendMessage(user.getChatId(), textToSend));
                }
            } else {
                SendMessage message = commands.stream()
                        .filter(clazz -> clazz.exist(update.getMessage().getText()))
                        .findFirst()
                        .map(clazz -> clazz.process(update))
                        .orElseGet(() -> prepareAndSendMessage(chatId, "Sorry, command was not recognized"));
                executeMessage(message);
            }
        } else if (update.hasCallbackQuery()) {
            String callbackData = update.getCallbackQuery().getData();
            long messageId = update.getCallbackQuery().getMessage().getMessageId();
            long chatId = update.getCallbackQuery().getMessage().getChatId();

            if(callbackData.equals(YES_BUTTON)){
                String text = "You pressed YES button";
                executeEditMessageText(text, chatId, messageId);
            }
            else if (callbackData.equals(NO_BUTTON)) {
                String text = "You pressed NO button";
                executeEditMessageText(text, chatId, messageId);
            }
        }
    }

    private void executeEditMessageText(String text, long chatId, long messageId) {
        EditMessageText message = new EditMessageText();
        message.setChatId(String.valueOf(chatId));
        message.setText(text);
        message.setMessageId((int) messageId);
        try {
            execute(message);
        } catch (TelegramApiException e) {
        }
    }

    private void executeMessage(SendMessage message){
        try {
            execute(message);
        } catch (TelegramApiException e) {
        }
    }

    private SendMessage prepareAndSendMessage(long chatId, String textToSend){
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);

        return message;
    }

}
