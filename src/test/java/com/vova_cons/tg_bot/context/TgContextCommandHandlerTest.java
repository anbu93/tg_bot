package com.vova_cons.tg_bot.context;

import org.junit.Assert;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * Created by anbu on 13.08.20.
 **/
public class TgContextCommandHandlerTest {
    private long actualUid = -1;

    @Test
    public void testHandleMethod() throws Exception {
        Method method = getClass().getDeclaredMethod("testMethod", long.class);
        TgContextCommandHandler commandHandler = new TgContextCommandHandler(this, method);
        long exceptedUid = 2637;
        commandHandler.handle(exceptedUid);
        Assert.assertEquals(exceptedUid, actualUid);
    }

    @Test
    public void testHandleInvalidMethod() throws Exception {
        Method method = getClass().getDeclaredMethod("invalidTestMethod");
        TgContextCommandHandler commandHandler = new TgContextCommandHandler(this, method);
        try {
            commandHandler.handle(123);
            Assert.fail("Excepted exception");
        } catch (Exception excepted) {}
    }

    @Test
    public void testHandleInvalidMoreArgsMethod() throws Exception {
        Method method = getClass().getDeclaredMethod("moreArgumentsTestMethod", long.class, String.class);
        TgContextCommandHandler commandHandler = new TgContextCommandHandler(this, method);
        try {
            commandHandler.handle(123);
            Assert.fail("Excepted exception");
        } catch (Exception excepted) {}
    }

    public void testMethod(long uid) {
        this.actualUid = uid;
    }

    public void invalidTestMethod() {}

    public void moreArgumentsTestMethod(long uid, String message) {}
}