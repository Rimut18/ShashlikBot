package com.rimut.ShashlikBot.commands;

import com.rimut.ShashlikBot.service.TelegramBot;
import com.rimut.ShashlikBot.service.UserService;
import com.rimut.ShashlikBot.service.commands.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandTest {

    @Autowired
    private UserService userService;

    @Autowired
    private TelegramBot bot;

    @Test
    void process(){
        List<Command> commands = new ArrayList<>();
        commands.add(new StartCommand(userService, new ArrayList<>()));
        commands.add(new HelpCommand(bot));
        commands.add(new RegisterCommand(userService));
        commands.add(new StickerCommand(userService));

        assertTrue(commands.stream()
                .filter(clazz -> clazz.exist("/start"))
                .findFirst()
                .map(clazz -> clazz.exist("/start"))
                .orElseGet(() -> false));

        assertTrue(commands.stream()
                .filter(clazz -> clazz.exist("/help"))
                .findFirst()
                .map(clazz -> clazz.exist("/help"))
                .orElseGet(() -> false));

        assertTrue(commands.stream()
                .filter(clazz -> clazz.exist("/sticker"))
                .findFirst()
                .map(clazz -> clazz.exist("/sticker"))
                .orElseGet(() -> false));

        assertTrue(commands.stream()
                .filter(clazz -> clazz.exist("/register"))
                .findFirst()
                .map(clazz -> clazz.exist("/register"))
                .orElseGet(() -> false));
    }

}
