package com.vova_cons.tg_bot.context;

import com.vova_cons.tg_bot.annotations.TgBotMessageHandler;
import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by anbu on 13.08.20.
 **/
public class TgContextMessageHandlerTest {
    private String actualMessage;

    @Test
    public void testHandleMethod() throws Exception {
        Method method = getClass().getDeclaredMethod("validMethod", long.class, String.class);
        TgContextMessageHandler commandHandler = new TgContextMessageHandler(this, method);
        long uid = 98738927;
        String message = "the test message";
        String exceptedMessage = uid + "@" + message;
        commandHandler.handle(uid, message);
        Assert.assertEquals(exceptedMessage, actualMessage);
    }

    @Test
    public void testHandleEmptyMethod() throws Exception {
        Method method = getClass().getDeclaredMethod("emptyMethod");
        TgContextMessageHandler commandHandler = new TgContextMessageHandler(this, method);
        try {
            commandHandler.handle(123, "message");
            Assert.fail("Excepted exception");
        } catch (Exception excepted) {}
    }

    @Test
    public void testHandleInvalidMoreArgsMethod() throws Exception {
        Method method = getClass().getDeclaredMethod("moreArgumentsMethod", long.class, String.class, Object.class);
        TgContextMessageHandler commandHandler = new TgContextMessageHandler(this, method);
        try {
            commandHandler.handle(123, "message");
            Assert.fail("Excepted exception");
        } catch (Exception excepted) {}
    }

    public void validMethod(long uid, String message) {
        this.actualMessage = uid + "@" + message;
    }

    public void emptyMethod() {}

    public void moreArgumentsMethod(long uid, String message, Object anything) {}
}