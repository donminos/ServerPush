package com.newinit.task;

import com.mongodb.BasicDBObject;
import com.neovisionaries.ws.client.WebSocket;
import com.neovisionaries.ws.client.WebSocketAdapter;
import com.neovisionaries.ws.client.WebSocketException;
import com.neovisionaries.ws.client.WebSocketFactory;
import com.newinit.jdbc.ActionDB;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 *
 * @author Carlos Cesar Rosas<face_less@hotmail.com>
 */
@Component
public class TaskReSendMessage {

    //@Scheduled(fixedDelay = 3600000, )
    @Scheduled(fixedDelay = 10000)
    public void run() {
        try {
            List<BasicDBObject> dbObjects = ActionDB.findMessageNotSend();
            for (BasicDBObject obj : dbObjects) {
                if (ActionDB.validateForToken(obj.getString("token"))) {
                    WebSocket websocket = new WebSocketFactory()
                            .createSocket("wss://www.tdmobile.com.mx/NotificationPush-1.2/pushNotification?info=Server=ABC")
                            .addListener(new WebSocketAdapter() {
                                @Override
                                public void onTextMessage(WebSocket ws, String message) {
                                    System.out.println("Received msg: " + message);
                                }
                            })
                            .connect();

                    websocket.sendText("{\"reenvio\": true,\"token\": \"" + obj.getString("token") + "\", \"msn\": \"" + obj.getString("mensaje") + "\"}");

                    websocket.disconnect();
                }
            }
        } catch (WebSocketException | IOException ex) {
            Logger.getLogger(TaskReSendMessage.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
