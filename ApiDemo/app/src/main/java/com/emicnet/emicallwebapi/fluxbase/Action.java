package com.emicnet.emicallwebapi.fluxbase;

/**
 * Created by ntop on 18/12/15.
 */
public class Action<T> {
    private final String type;
    private final T data;

    public Action(String type, T data) {
        this.type = type;
        this.data = data;
    }

    public String getType() {
        return type;
    }

    public T getData() {
        return data;
    }
}
