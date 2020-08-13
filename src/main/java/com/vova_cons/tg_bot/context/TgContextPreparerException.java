package com.vova_cons.tg_bot.context;

/**
 * Created by anbu on 13.08.20.
 **/
public class TgContextPreparerException extends Exception {
    public TgContextPreparerException() {
    }

    public TgContextPreparerException(String message) {
        super(message);
    }

    public TgContextPreparerException(String message, Throwable cause) {
        super(message, cause);
    }

    public TgContextPreparerException(Throwable cause) {
        super(cause);
    }
}
