package com.vova_cons.tg_bot;

import com.vova_cons.tg_bot.annotations.TgBotContext;
import com.vova_cons.tg_bot.annotations.TgBotMessageHandler;
import com.vova_cons.tg_bot.context.TgContext;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by anbu on 13.08.20.
 **/
public class MessageHandleAnnotationTest {
    private MockContext context;
    private String actualMessage;

    @Before
    public void setUp() {
        context = new MockContext();
    }

    @Ignore(value = "Временно отключено")
    @Test
    public void testReceiveMessage() throws Exception {
        long uid = 123;
        String message = "test message";
        String exceptedMessage = uid + "@" + message;
        context.handleMessage(uid, message);
        Assert.assertEquals(exceptedMessage, actualMessage);
    }

    @TgBotContext(id = 0)
    public class MockContext extends TgContext {
        @TgBotMessageHandler
        public void onReceiveMessage(long uid, String message) {
            actualMessage = uid + "@" + message;
        }
    }
}
