package com.vova_cons.tg_bot.message_processor;

import java.util.Collection;

/**
 * Created by anbu on 14.08.20.
 **/
public interface MessageProcessor {
    void start();
    boolean sendMessage(long uid, String message, Collection<String> commands);
}
