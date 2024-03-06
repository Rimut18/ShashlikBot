package com.rimut.ShashlikBot.service.commands;

import com.rimut.ShashlikBot.model.User;
import com.rimut.ShashlikBot.service.UserService;
import com.vdurmont.emoji.EmojiParser;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service("/start")
public class StartCommand extends Command {
    private final UserService userService;

    public StartCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean exist(String str) {
        return str.equals("/start");
    }

    @Override
    public String explanation() {
        return "welcome to the club, buddy";
    }

    @Override
    public String description() {
        return "to see a welcome message";
    }

    @Override
    public SendMessage process(Update update) {
        long chatId = update.getMessage().getFrom().getId();
        registerUser(update.getMessage());
        return startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
    }

    private SendMessage startCommandReceived(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode("Hi, " + name + ", nice to meet you! \n Are you want a shashlik?" + " :star:" + " :star:" + " :star:");
        SendMessage message = prepareAndSendMessage(chatId, answer);
        message.setReplyMarkup(createReplyKeyboardMarkup());

        return message;
    }

    private ReplyKeyboardMarkup createReplyKeyboardMarkup() {
        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();
        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();
        row.add("/sticker");
        keyboardRows.add(row);

        row = new KeyboardRow();
        row.add("/register");
        row.add("/help");
        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);
        return keyboardMarkup;
    }

    private void registerUser(Message msg) {

        if(userService.findById(msg.getChatId()).isEmpty()){
            var chatId = msg.getChatId();
            var chat = msg.getChat();

            User user = new User();

            user.setChatId(chatId);
            user.setFirstName(chat.getFirstName());
            user.setLastName(chat.getLastName());
            user.setUserName(chat.getUserName());
            user.setRegisteredAt(new Timestamp(System.currentTimeMillis()));

            userService.save(user);
        }
    }
}
