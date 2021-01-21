package Unicam.SPM2020_FMS.controller;


import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/ParkSpaces/push/{parkSpot}/{parkSpace}")
public class ParkingWebSocketController {
	
    private static final Set<Session> SESSIONS = ConcurrentHashMap.newKeySet();
    private static final Map<String, Session> sessionsMap = new ConcurrentHashMap<>();
    
	@OnOpen
    public void onOpen(@PathParam("parkSpot") Integer spot, @PathParam("parkSpace") Integer space, Session session) {
		sessionsMap.put(spot+"-"+space, session);
        SESSIONS.add(session);
    }

    @OnClose
    public void onClose(Session session) {
        SESSIONS.remove(session);
    }

    public static void sendExpiredMessage(Integer spot, Integer space) {
    	String sessionKey=spot+"-"+space;
        synchronized (SESSIONS) {
            for (Session session : SESSIONS) {
                if (session.equals(sessionsMap.get(sessionKey)) && session.isOpen()) {                	
                    session.getAsyncRemote().sendText("Unfortunately time is finished, please try again");
                }
            }
        }
    }

}