package com.vova_cons.tg_bot.test;

import com.vova_cons.tg_bot.annotations.TgBotCommandHandler;
import com.vova_cons.tg_bot.annotations.TgBotContext;
import com.vova_cons.tg_bot.annotations.TgBotMessageHandler;
import com.vova_cons.tg_bot.context.TgContext;

import java.util.Random;

/**
 * Created by anbu on 14.08.20.
 **/
@TgBotContext(id = ContextType.MAIN)
public class MainContext extends TgContext {
    private Random random = new Random();

    @TgBotCommandHandler(command = "/help")
    public void helpCommand(long user) {
        bot.sendMessage(user, "Use bot buttons for randomize dices");
    }

    @TgBotCommandHandler(command = "/dice6")
    public void randomizeDice6(long user) {
        bot.sendMessage(user, "Random value: " + (random.nextInt(6) + 1));
    }

    @TgBotCommandHandler(command = "/dice6x2")
    public void randomizeDice6x2(long user) {
        bot.sendMessage(user,
                "Dice 1: " + (random.nextInt(6) + 1) + "\n" +
                        "Dice 2: " + (random.nextInt(6) + 1));
    }

    @TgBotCommandHandler(command = "/dice6x4")
    public void randomizeDice6x4(long user) {
        bot.sendMessage(user,
                "Dice 1: " + (random.nextInt(6) + 1) + "\n" +
                        "Dice 2: " + (random.nextInt(6) + 1) + "\n" +
                        "Dice 3: " + (random.nextInt(6) + 1) + "\n" +
                        "Dice 4: " + (random.nextInt(6) + 1));
    }

    @TgBotCommandHandler(command = "/dice6x6")
    public void randomizeDice6x6(long user) {
        bot.sendMessage(user,
                "Dice 1: " + (random.nextInt(6) + 1) + "\n" +
                        "Dice 2: " + (random.nextInt(6) + 1) + "\n" +
                        "Dice 3: " + (random.nextInt(6) + 1) + "\n" +
                        "Dice 4: " + (random.nextInt(6) + 1) + "\n" +
                        "Dice 5: " + (random.nextInt(6) + 1) + "\n" +
                        "Dice 6: " + (random.nextInt(6) + 1));
    }

    @TgBotCommandHandler(command = "/custom")
    public void customCommand(long user) {
        bot.changeContext(user, ContextType.CUSTOM);
        bot.sendMessage(user, "Please input dices count");
    }

    @TgBotMessageHandler
    public void onReceiveAnyMessage(long user, String message) {
        bot.sendMessage(user, "Use command buttons for interaction with bot");
    }
}
