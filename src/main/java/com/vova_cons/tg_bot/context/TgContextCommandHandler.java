package com.vova_cons.tg_bot.context;

import java.lang.reflect.Method;

/**
 * Created by anbu on 13.08.20.
 **/
public class TgContextCommandHandler {
    private Object object;
    private Method method;

    public TgContextCommandHandler(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    public void handle(long uid) throws Exception {
        method.invoke(object, uid);
    }
}
