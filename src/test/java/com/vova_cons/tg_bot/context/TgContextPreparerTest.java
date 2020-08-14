package com.vova_cons.tg_bot.context;

import com.vova_cons.tg_bot.annotations.TgBotCommandHandler;
import com.vova_cons.tg_bot.annotations.TgBotContext;
import com.vova_cons.tg_bot.annotations.TgBotMessageHandler;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by anbu on 13.08.20.
 **/
public class TgContextPreparerTest {
    private TgContextPreparer preparer = new TgContextPreparer();


    @Test
    public void testContextId() throws Exception {
        ContextId contextId = new ContextId();
        preparer.prepare(contextId);
        Assert.assertEquals(273, contextId.getId());
    }

    @TgBotContext(id = 273)
    public static class ContextId extends TgContext {}

    @Test
    public void testNotPresentAnnotationClass() {
        ContextNotPresentAnnotation context = new ContextNotPresentAnnotation();
        try {
            preparer.prepare(context);
            Assert.fail();
        } catch (TgContextPreparerException excepted) {
        } catch (Exception e) {
            throw e;
        }
    }

    public static class ContextNotPresentAnnotation extends TgContext {}

    @Test
    public void testCommandRegistration() throws Exception {
        ContextWithOneCommand context = new ContextWithOneCommand();
        preparer.prepare(context);
        Method exceptedMethod = ContextWithOneCommand.class.getDeclaredMethod("handleCommand", long.class);
        TgContextCommandHandler command = context.getCommandHandler("/start");
        Assert.assertEquals(context, command.getObject());
        Assert.assertEquals(exceptedMethod, command.getMethod());
    }

    @TgBotContext(id = 0)
    public static class ContextWithOneCommand extends TgContext {
        @TgBotCommandHandler(command = "/start")
        public void handleCommand(long uid) {}
    }

    @Test
    public void testPrivateCommandRegistration() throws Exception {
        ContextWithPrivateCommand context = new ContextWithPrivateCommand();
        preparer.prepare(context);
        Method exceptedMethod = ContextWithPrivateCommand.class.getDeclaredMethod("handleCommand", long.class);
        TgContextCommandHandler command = context.getCommandHandler("/start");
        Assert.assertEquals(context, command.getObject());
        Assert.assertEquals(exceptedMethod, command.getMethod());
    }

    @TgBotContext(id = 0)
    public static class ContextWithPrivateCommand extends TgContext {
        @TgBotCommandHandler(command = "/start")
        private void handleCommand(long uid) {}
    }

    @Test
    public void testMessageHandlerRegistration() throws Exception {
        ContextWithMessageHandler context = new ContextWithMessageHandler();
        preparer.prepare(context);
        Method exceptedMethod = ContextWithMessageHandler.class.getDeclaredMethod("onReceiveMessage", long.class, String.class);
        TgContextMessageHandler messageHandler = context.getMessageHandler();
        Assert.assertEquals(context, messageHandler.getObject());
        Assert.assertEquals(exceptedMethod, messageHandler.getMethod());
    }

    @TgBotContext(id = 0)
    public static class ContextWithMessageHandler extends TgContext {
        @TgBotMessageHandler
        private void onReceiveMessage(long uid, String message) {}
    }

    @Test
    public void testMessageHandlerInvalidSignatureLessArgs() throws Exception {
        ContextWithInvalidMessageHandlerLessArgs context = new ContextWithInvalidMessageHandlerLessArgs();
        try {
            preparer.prepare(context);
            Assert.fail();
        } catch (TgContextPreparerException excepted) {
            TgContextMessageHandler messageHandler = context.getMessageHandler();
            Assert.assertNull(messageHandler);
        } catch (Exception e) {
            throw e;
        }
    }

    @TgBotContext(id = 0)
    public static class ContextWithInvalidMessageHandlerLessArgs extends TgContext {
        @TgBotMessageHandler
        private void onReceiveMessage(long uid) {}
    }

    @Test
    public void testMessageHandlerInvalidSignatureNotValidParams() throws Exception {
        ContextWithInvalidMessageHandlerNotValidParams context = new ContextWithInvalidMessageHandlerNotValidParams();
        try {
            preparer.prepare(context);
            Assert.fail();
        } catch (TgContextPreparerException excepted) {
            TgContextMessageHandler messageHandler = context.getMessageHandler();
            Assert.assertNull(messageHandler);
        } catch (Exception e) {
            throw e;
        }
    }

    @TgBotContext(id = 0)
    public static class ContextWithInvalidMessageHandlerNotValidParams extends TgContext {
        @TgBotMessageHandler
        private void onReceiveMessage(int uid, String message) {}
    }

    @Test
    public void testCommandHandlerInvalidSignatureEmptyArgs() throws Exception {
        ContextWithInvalidCommandHandlerEmptyArgs context = new ContextWithInvalidCommandHandlerEmptyArgs();
        try {
            preparer.prepare(context);
            Assert.fail();
        } catch (TgContextPreparerException excepted) {
            TgContextCommandHandler handler = context.getCommandHandler("/start");
            Assert.assertNull(handler);
        } catch (Exception e) {
            throw e;
        }
    }

    @TgBotContext(id = 0)
    public static class ContextWithInvalidCommandHandlerEmptyArgs extends TgContext {
        @TgBotCommandHandler(command = "/start")
        private void onReceiveMessage() {}
    }

    @Test
    public void testCommandHandlerInvalidSignatureNotValidParams() throws Exception {
        ContextWithInvalidCommandHandlerNotValidParams context = new ContextWithInvalidCommandHandlerNotValidParams();
        try {
            preparer.prepare(context);
            Assert.fail();
        } catch (TgContextPreparerException excepted) {
            TgContextCommandHandler handler = context.getCommandHandler("/start");
            Assert.assertNull(handler);
        } catch (Exception e) {
            throw e;
        }
    }

    @TgBotContext(id = 0)
    public static class ContextWithInvalidCommandHandlerNotValidParams extends TgContext {
        @TgBotCommandHandler(command = "/start")
        private void onReceiveMessage(String uid) {}
    }
}