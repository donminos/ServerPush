package com.newinit.controller;

import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketFactory;

/**
 *
 * @author ceasar
 */
public class TestAPP {

    public static void main(String[] args) throws Exception {

        WebSocket websocket = new WebSocketFactory()
                .createSocket("ws://www.tdmobil.ml:8080/NotificationPush-1.0/pushNotification?info=Server")
                .addListener(new WebSocketAdapter() {
                    @Override
                    public void onTextMessage(WebSocket ws, String message) {
                        System.out.println("Received msg: " + message);
                    }
                })
                .connect();
        
        websocket.sendText("{'reenvio': false,'token': 'PruebaTelefono', 'msn': 'info'}");
        
        websocket.disconnect();
        // Don't forget to call disconnect() after use.
        // websocket.disconnect();
    }
}
