package com.vova_cons.tg_bot.context;

import com.vova_cons.tg_bot.annotations.TgBotContext;

import static org.junit.Assert.*;

/**
 * Created by anbu on 13.08.20.
 **/
public class TgContextPreparerTest {
    private TgContext context = new TgContext();
    private TgContextPreparer preparer;



    @TgBotContext(id = 0)
    public static class Context1 extends TgContext {

    }
}