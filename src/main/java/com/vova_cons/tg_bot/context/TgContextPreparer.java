package com.vova_cons.tg_bot.context;

import com.vova_cons.tg_bot.annotations.TgBotCommandHandler;
import com.vova_cons.tg_bot.annotations.TgBotContext;
import com.vova_cons.tg_bot.annotations.TgBotMessageHandler;

import java.lang.reflect.Method;

/**
 * Created by anbu on 13.08.20.
 **/
public class TgContextPreparer {

    public void prepare(TgContext context) throws TgContextPreparerException {
        Class<? extends TgContext> type = context.getClass();
        prepareContextId(type, context);
        prepareCommandHandlers(type, context);
        prepareMessageHandlers(type, context);
    }

    private void prepareContextId(Class<? extends TgContext> type, TgContext context) throws TgContextPreparerException {
        if (type.isAnnotationPresent(TgBotContext.class)) {
            TgBotContext contextAnnotation = type.getAnnotation(TgBotContext.class);
            int id = contextAnnotation.id();
            context.setId(id);
        } else {
            throw new TgContextPreparerException("Context class not present @TgBotContext annotation");
        }
    }

    private void prepareCommandHandlers(Class<? extends TgContext> type, TgContext context) throws TgContextPreparerException {
        for(Method method : type.getDeclaredMethods()) {
            if (method.isAnnotationPresent(TgBotCommandHandler.class)) {
                TgBotCommandHandler commandHandlerAnnotiation = method.getAnnotation(TgBotCommandHandler.class);
                Class<?>[] parameters = method.getParameterTypes();
                String command = commandHandlerAnnotiation.name();
                if (parameters.length == 1 && parameters[0] == long.class) {
                    context.setCommandHandler(command, new TgContextCommandHandler(context, method));
                } else {
                    throw new TgContextPreparerException("Method " + method.getName() + " present @TgBotCommandHandler("
                            + command + ") annotation but invalid parameters. Must be (long)");
                }
            }
        }
    }

    private void prepareMessageHandlers(Class<? extends TgContext> type, TgContext context) throws TgContextPreparerException {
        for(Method method : type.getDeclaredMethods()) {
            if (method.isAnnotationPresent(TgBotMessageHandler.class)) {
                TgBotMessageHandler messageHandlerAnnotiation = method.getAnnotation(TgBotMessageHandler.class);
                Class<?>[] parameters = method.getParameterTypes();
                if (parameters.length == 2 && parameters[0] == long.class && parameters[1] == String.class) {
                    context.setMessageHandler(new TgContextMessageHandler(context, method));
                } else {
                    throw new TgContextPreparerException("Method " + method.getName() +
                            " present @TgBotMessageHandler annotation but invalid parameters. Must be (long, String)");
                }
            }
        }
    }
}
