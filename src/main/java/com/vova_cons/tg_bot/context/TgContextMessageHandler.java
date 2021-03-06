package com.vova_cons.tg_bot.context;

import java.lang.reflect.Method;

/**
 * Created by anbu on 13.08.20.
 **/
public class TgContextMessageHandler {
    private Object object;
    private Method method;

    public TgContextMessageHandler(Object object, Method method) {
        this.object = object;
        this.method = method;
    }

    public void handle(long uid, String message) throws Exception {
        method.invoke(object, uid, message);
    }

    protected Object getObject() {
        return object;
    }

    protected Method getMethod() {
        return method;
    }
}
