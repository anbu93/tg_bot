package com.vova_cons.tg_bot.test;

import com.vova_cons.tg_bot.annotations.TgBotCommandHandler;
import com.vova_cons.tg_bot.annotations.TgBotContext;
import com.vova_cons.tg_bot.annotations.TgBotMessageHandler;
import com.vova_cons.tg_bot.context.TgContext;

/**
 * Created by anbu on 14.08.20.
 **/
@TgBotContext(id = ContextType.START)
public class StartContext extends TgContext {
    @TgBotCommandHandler(command = "/help")
    public void helpCommand(long user) {
        bot.sendMessage(user, "Use /start command for start.");
    }

    @TgBotCommandHandler(command = "/start")
    public void onStart(long user) {
        bot.changeContext(user, ContextType.MAIN);
        bot.sendMessage(user, "Hello! Use command for interaction with bot.");
    }

    @TgBotMessageHandler
    public void onReceiveAnyMessage(long user, String message) {
        bot.sendMessage(user, "Use /start command for start");
    }
}
