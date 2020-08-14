package com.vova_cons.tg_bot.test;

import com.vova_cons.tg_bot.annotations.TgBotCommandHandler;
import com.vova_cons.tg_bot.annotations.TgBotContext;
import com.vova_cons.tg_bot.annotations.TgBotMessageHandler;
import com.vova_cons.tg_bot.context.TgContext;

import java.util.Random;

/**
 * Created by anbu on 14.08.20.
 **/
@TgBotContext(id = ContextType.CUSTOM)
public class CustomContext extends TgContext {
    private Random random = new Random();

    @TgBotMessageHandler
    public void handleCustomDicesCount(long user, String message) {
        try {
            int count = Integer.parseInt(message);
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < count; i++) {
                sb.append("Dice ").append(i + 1).append(": ")
                        .append(random.nextInt(6) + 1).append("\n");
            }
            bot.changeContext(user, ContextType.MAIN);
            bot.sendMessage(user, sb.toString());
        } catch (Exception e) {
            bot.sendMessage(user, "Invalid number, try again input dices count");
        }
    }

    @TgBotCommandHandler(command = "/cancel")
    public void cancel(long user) {
        bot.changeContext(user, ContextType.MAIN);
        bot.sendMessage(user, "Custom dices cancelled");
    }
}
