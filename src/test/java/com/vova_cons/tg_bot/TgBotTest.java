package com.vova_cons.tg_bot;

import com.vova_cons.tg_bot.annotations.TgBotCommandHandler;
import com.vova_cons.tg_bot.annotations.TgBotContext;
import com.vova_cons.tg_bot.annotations.TgBotMessageHandler;
import com.vova_cons.tg_bot.context.TgContext;
import com.vova_cons.tg_bot.context.TgContextPreparerException;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by anbu on 14.08.20.
 **/
public class TgBotTest {
    @Test
    public void testContextGetter() throws TgContextPreparerException {
        TgBot bot = new TgBot();
        EmptyContext context = new EmptyContext();
        bot.registerContext(context);
        Assert.assertEquals(context, bot.getContext(123));
    }

    @Test
    public void testContextRegistration() throws Exception {
        TgBot bot = new TgBot();
        ContextWithCommand context = new ContextWithCommand();
        bot.registerContext(context);
        long uid = 38490;
        context.handleMessage(uid, "/start");
        Assert.assertEquals(uid, context.uid);
    }

    @TgBotContext(id = 123)
    public static class EmptyContext extends TgContext {}

    @TgBotContext(id = 273)
    public static class ContextWithCommand extends TgContext {
        long uid;

        @TgBotCommandHandler(command = "/start")
        public void handleCommand(long uid) {
            this.uid = uid;
        }
    }

    @TgBotContext(id = 238)
    public static class ContextWithMessageHandler extends TgContext {
        String message;

        @TgBotMessageHandler()
        public void handleMessage(long uid, String message) {
            this.message = uid + "@" + message;
        }
    }
}