package com.newinit.websocket;

/**
 *
 * @author ceasar
 */
public class Message {

    private boolean reenvio;
    private String token;
    private String msn;
    private boolean todos;

    public boolean isReenvio() {
        return reenvio;
    }

    public void setReenvio(boolean reenvio) {
        this.reenvio = reenvio;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public boolean isTodos() {
        return todos;
    }

    public void setTodos(boolean todos) {
        this.todos = todos;
    }
}
