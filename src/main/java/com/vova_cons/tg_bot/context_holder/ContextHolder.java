package com.vova_cons.tg_bot.context_holder;

/**
 * Created by anbu on 14.08.20.
 **/
public interface ContextHolder {
    int DEFAULT_VALUE = 0;

    int getUserContext(long uid);
    void setUserContext(long uid, int context);
}
