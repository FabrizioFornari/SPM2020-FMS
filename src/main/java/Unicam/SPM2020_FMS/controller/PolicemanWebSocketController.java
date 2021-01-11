package Unicam.SPM2020_FMS.controller;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import Unicam.SPM2020_FMS.service.SchedulerService;

@ServerEndpoint("/reservationsToCheck/push")
public class PolicemanWebSocketController {
	
    public SchedulerService schedulerService = SchedulerService.getBean(SchedulerService.class);
    private static final Set<Session> SESSIONS = ConcurrentHashMap.newKeySet();
    private static Boolean scheduled = false;
    
	@OnOpen
    public void onOpen(Session session) {
        SESSIONS.add(session);
        if (!scheduled) {
	    	schedulerService.schedulePoliceChecking();
	    	scheduled=true;
		}
    }

    @OnClose
    public void onClose(Session session) {
        SESSIONS.remove(session);
    }

    public static void sendAll(String text) {
        synchronized (SESSIONS) {
            for (Session session : SESSIONS) {
                if (session.isOpen()) {
                    session.getAsyncRemote().sendText(text);
                }
            }
        }
    }

}