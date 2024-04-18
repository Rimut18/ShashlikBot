package com.rimut.ShashlikBot.commands;

import com.rimut.ShashlikBot.service.TelegramBot;
import com.rimut.ShashlikBot.service.UserService;
import com.rimut.ShashlikBot.service.commands.*;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CommandTest {



    @Mock
    private UserService userService;

    @Mock
    private TelegramBot bot;

//    @Test
//    void process(){
//        List<Command> commands = new ArrayList<>();
//        commands.add(new StartCommand(userService, new ArrayList<>()));
//        commands.add(new HelpCommand(bot));
//        commands.add(new RegisterCommand(userService));
//        commands.add(new StickerCommand(userService));
//
//        assertTrue(commands.stream()
//                .filter(clazz -> clazz.exist("/start"))
//                .findFirst()
//                .map(clazz -> clazz.exist("/start"))
//                .orElseGet(() -> false));
//
//        assertTrue(commands.stream()
//                .filter(clazz -> clazz.exist("/help"))
//                .findFirst()
//                .map(clazz -> clazz.exist("/help"))
//                .orElseGet(() -> false));
//
//        assertTrue(commands.stream()
//                .filter(clazz -> clazz.exist("/sticker"))
//                .findFirst()
//                .map(clazz -> clazz.exist("/sticker"))
//                .orElseGet(() -> false));
//
//        assertTrue(commands.stream()
//                .filter(clazz -> clazz.exist("/register"))
//                .findFirst()
//                .map(clazz -> clazz.exist("/register"))
//                .orElseGet(() -> false));
//    }

}
