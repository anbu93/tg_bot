package com.vova_cons.tg_bot.context_holder;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anbu on 14.08.20.
 **/
public class RuntimeContextHolder implements ContextHolder {
    private Map<Long, Integer> data = new HashMap<>();

    @Override
    public int getUserContext(long uid) {
        if (data.containsKey(uid)) {
            return data.get(uid);
        }
        return DEFAULT_VALUE;
    }

    @Override
    public void setUserContext(long uid, int context) {
        data.put(uid, context);
    }
}
