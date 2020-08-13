package com.vova_cons.tg_bot.context;

import com.vova_cons.tg_bot.annotations.TgBotCommandHandler;
import com.vova_cons.tg_bot.annotations.TgBotContext;
import com.vova_cons.tg_bot.annotations.TgBotMessageHandler;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by anbu on 13.08.20.
 **/
public class TgContextTest {
    private TgContextPreparer preparer = new TgContextPreparer();

    @Test
    public void testMessageHandler() throws Exception {
        MessageHandlerContext context = new MessageHandlerContext();
        preparer.prepare(context);
        long uid = 2738;
        String message = "the test message";
        context.handleMessage(uid, message);
        String exceptedMessage = uid + "@" + message;
        Assert.assertEquals(exceptedMessage, context.actualMessage);
    }

    @TgBotContext(id = 0)
    public static class MessageHandlerContext extends TgContext {
        String actualMessage;

        @TgBotMessageHandler
        public void onReceiveMessage(long uid, String message) {
            actualMessage = uid + "@" + message;
        }
    }
    @Test
    public void testCommandHandler() throws Exception {
        CommandHandlerContext context = new CommandHandlerContext();
        preparer.prepare(context);
        long uid = 2738;
        String message = "/start";
        context.handleMessage(uid, message);
        Assert.assertEquals(uid, context.uid);
    }

    @TgBotContext(id = 0)
    public static class CommandHandlerContext extends TgContext {
        long uid;

        @TgBotCommandHandler(name = "/start")
        public void handleStartCommand(long uid) {
            this.uid = uid;
        }
    }
}