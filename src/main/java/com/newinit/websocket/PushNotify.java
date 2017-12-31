package com.newinit.websocket;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.newinit.jdbc.ActionDB;
import java.util.*;
import java.util.concurrent.*;
import javax.websocket.*;
import javax.websocket.server.*;

@ServerEndpoint("/pushNotification")
public class PushNotify {

    //queue holds the list of connected clients
    private static Queue<Session> queue = new ConcurrentLinkedQueue<Session>();

    @OnMessage
    public void onMessage(Session session, String msg) {
        //provided for completeness, in out scenario clients don't send any msg.
        try {
            boolean sendMessage = false;
            ObjectMapper mapper = new ObjectMapper();
            ArrayList<Session> closedSessions = new ArrayList<>();
            Message mensaje = mapper.readValue(msg, Message.class);
            if (ActionDB.validateForSession(session.getId())) {
                if (!mensaje.isTodos()) {
                    for (Session sesion : queue) {
                        if (!sesion.isOpen()) {
                            System.err.println("Closed session: " + sesion.getId());
                            closedSessions.add(sesion);
                        } else if (sesion.getId().equals(ActionDB.findSessionForToken(mensaje.getToken()))) {
                            sesion.getBasicRemote().sendText(mensaje.getMsn());
                            ActionDB.insertMensaje(session.getId(), sesion.getId(), mensaje.getMsn());
                            sendMessage = true;
                        }
                    }

                } else {
                    for (Session sesion : queue) {
                        if (!sesion.isOpen()) {
                            System.err.println("Closed session: " + sesion.getId());
                            closedSessions.add(sesion);
                        } else {
                            sesion.getBasicRemote().sendText(mensaje.getMsn());
                            ActionDB.insertMensaje(session.getId(), sesion.getId(), mensaje.getMsn());
                            sendMessage = true;
                        }
                    }
                }
                if (!sendMessage) {
                    if (!mensaje.isReenvio()) {
                        ActionDB.insertMensajeNotSend(session.getId(), ActionDB.findSessionForToken(mensaje.getToken()), mensaje.getMsn());
                    }
                } else if (sendMessage) {
                    ActionDB.deleteMensajeNotSend(session.getId(), ActionDB.findSessionForToken(mensaje.getToken()), mensaje.getMsn());
                }

                queue.removeAll(closedSessions);
            }
            System.out.println("received msg " + msg + " from " + session.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void open(Session session) {
        queue.add(session);
        System.out.println("New session opened: " + session.getId());
        String[] param = session.getRequestURI().getQuery().split("=");
        ActionDB.updateSession(param[1], param[2], session.getId());
        ActionDB.updateSession(session.getId(), true);
    }

    @OnError
    public void error(Session session, Throwable t) {
        queue.remove(session);
        ActionDB.updateSession(session.getId(), false);
        System.err.println("Error on session " + session.getId());
    }

    @OnClose
    public void closedConnection(Session session) {
        queue.remove(session);
        ActionDB.updateSession(session.getId(), false);
        System.out.println("session closed: " + session.getId());
    }
}
