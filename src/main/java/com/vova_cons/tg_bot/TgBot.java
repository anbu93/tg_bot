package com.vova_cons.tg_bot;

import com.vova_cons.tg_bot.context.TgContext;
import com.vova_cons.tg_bot.context.TgContextPreparer;
import com.vova_cons.tg_bot.context.TgContextPreparerException;
import com.vova_cons.tg_bot.context_holder.ContextHolder;
import com.vova_cons.tg_bot.context_holder.RuntimeContextHolder;
import com.vova_cons.tg_bot.message_processor.MessageProcessor;
import com.vova_cons.tg_bot.message_processor.TelegramMessageProcessor;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by anbu on 13.08.20.
 **/
public class TgBot {
    private ContextHolder contextHolder = new RuntimeContextHolder();
    private TgContextPreparer contextPreparer = new TgContextPreparer();
    private Map<Integer, TgContext> contextMap = new HashMap<>();
    private MessageProcessor messageProcessor;
    private int defaultContext = 0;


    //region settings
    public TgBot() {}

    public TgBot(String botId, String token) {
        messageProcessor = new TelegramMessageProcessor(this, botId, token);
    }

    public void setContextHolder(ContextHolder contextHolder) {
        this.contextHolder = contextHolder;
    }

    public void setMessageProcessor(MessageProcessor messageProcessor) {
        this.messageProcessor = messageProcessor;
    }

    public void registerContext(TgContext context) throws TgContextPreparerException {
        contextPreparer.prepare(context);
        context.setBot(this);
        contextMap.put(context.getId(), context);
    }

    public void setDefaultContext(int defaultContext) {
        this.defaultContext = defaultContext;
    }

    public void start() {
        messageProcessor.start();
    }
    //endregion


    //region interface
    public void onReceiveMessage(long uid, String message) throws Exception {
        int contextId = contextHolder.getUserContext(uid);
        if (contextId == ContextHolder.DEFAULT_VALUE) {
            contextId = defaultContext;
        }
        TgContext context = contextMap.get(contextId);
        if (context != null) {
            context.handleMessage(uid, message);
        } else {
            throw new Exception("Not found context by uid=" + uid);
        }
    }

    public void sendMessage(long uid, String message) {
        int contextId = contextHolder.getUserContext(uid);
        if (contextId == ContextHolder.DEFAULT_VALUE) {
            contextId = defaultContext;
        }
        TgContext context = contextMap.get(contextId);
        Collection<String> commands = context.getComamnds();
        messageProcessor.sendMessage(uid, message, commands);
    }

    public void changeContext(long uid, int context) {
        contextHolder.setUserContext(uid, context);
        // settings buttons
    }
    //endregion


    //region getters
    protected TgContext getContext(int id) {
        return contextMap.get(id);
    }
    //endregion
}
