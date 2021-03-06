package com.vova_cons.tg_bot.context;

import com.vova_cons.tg_bot.TgBot;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anbu on 13.08.20.
 **/
public class TgContext {
    private int id;
    private Map<String, TgContextCommandHandler> commandHandlers = new HashMap<>();
    private TgContextMessageHandler messageHandler;
    protected TgBot bot;

    //region interface
    public void setBot(TgBot bot) {
        this.bot = bot;
    }

    public int getId() {
        return id;
    }

    public void handleMessage(long uid, String message) throws Exception {
        if (commandHandlers.containsKey(message)) {
            TgContextCommandHandler commandHandler = commandHandlers.get(message);
            commandHandler.handle(uid);
        } else if (messageHandler != null) {
            messageHandler.handle(uid, message);
        }
    }

    public Collection<String> getComamnds() {
        return commandHandlers.keySet();
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
