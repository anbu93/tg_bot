package com.vova_cons.tg_bot.context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anbu on 13.08.20.
 **/
public class TgContext {
    private int id;
    private Map<String, TgContextCommandHandler> commandHandlers = new HashMap<>();
    private TgContextMessageHandler messageHandler;

    //region interface
    public int getId() {
        return id;
    }

    public void handleMessage(int uid, String message) {
        // TODO: 13.08.20 implement
    }
    //endregion


    //region build
    protected void setId(int id) {
        this.id = id;
    }

    protected void setCommandHandler(String command, TgContextCommandHandler handler) {
        commandHandlers.put(command, handler);
    }

    protected TgContextCommandHandler getCommandHandler(String command) {
        return commandHandlers.get(command);
    }

    protected TgContextMessageHandler getMessageHandler() {
        return messageHandler;
    }

    protected void setMessageHandler(TgContextMessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }
    //endregion
}
