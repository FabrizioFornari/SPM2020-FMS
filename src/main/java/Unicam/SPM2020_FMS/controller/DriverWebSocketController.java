package Unicam.SPM2020_FMS.controller;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ParkSpaces/push/{idDriver}")
public class DriverWebSocketController {
	
    private static final Set<Session> SESSIONS = ConcurrentHashMap.newKeySet();
    private static final Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
    
	@OnOpen
    public void onOpen(@PathParam("idDriver") String idDriver, Session session) {
		sessionsMap.put(idDriver, session);
        SESSIONS.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        SESSIONS.remove(session);
    }

    public static void sendBookChangedMessage(Integer idDriver, Integer newSpot) {
    	String sessionKey=idDriver.toString();
        synchronized (SESSIONS) {
            for (Session session : SESSIONS) {
                if (session.equals(sessionsMap.get(sessionKey)) && session.isOpen()) {
                	if(newSpot==0) {
                		session.getAsyncRemote().sendText("We're sorry, your reserved spot is no more available please if you still need it please try the park now function");
                	} else {
                		session.getAsyncRemote().sendText("Your reservation is changed, your new spot is " + newSpot);
                	}
                }
            }
        }
    }
    
    public static void sendBookClosedMessage(Integer idDriver) {
    	String sessionKey=idDriver.toString();
        synchronized (SESSIONS) {
            for (Session session : SESSIONS) {
                if (session.equals(sessionsMap.get(sessionKey)) && session.isOpen()) {
                	session.getAsyncRemote().sendText("You have an unused park reservation for today that has been closed with the minimum period");
                }
            }
        }
    }

}