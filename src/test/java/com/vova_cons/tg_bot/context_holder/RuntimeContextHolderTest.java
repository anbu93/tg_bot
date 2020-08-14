package com.vova_cons.tg_bot.context_holder;

import com.vova_cons.tg_bot.context_holder.ContextHolder;
import com.vova_cons.tg_bot.context_holder.RuntimeContextHolder;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by anbu on 14.08.20.
 **/
public class RuntimeContextHolderTest {
    private RuntimeContextHolder holder = new RuntimeContextHolder();

    @Test
    public void testDefaultValue() {
        Assert.assertEquals(ContextHolder.DEFAULT_VALUE, holder.getUserContext(26374));
    }

    @Test
    public void testSetContext() {
        long uid = 273489;
        int exceptedContext = 7;
        holder.setUserContext(uid, exceptedContext);
        Assert.assertEquals(exceptedContext, holder.getUserContext(uid));
    }
}