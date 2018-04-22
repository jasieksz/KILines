package server;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import model.GameState;
import model.Motorcycle;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;
import org.json.simple.JSONObject;
import sun.jvm.hotspot.HelloWorld;

import java.util.LinkedList;
import java.util.List;

@WebSocket
public class UpdatesWebSocketHandler {
    private String sender, msg;
    private List<Session> sessions = new LinkedList<>();

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        this.sessions.add(user);
        System.out.println(user);
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        //String nick = somehowGetNicknameFromMessage();
        //HelloWorld.isOccupied
        /* parse maessage nad affect players somehow */
    }

    public void broadcastState(BoardState state){
        sessions.stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(String.valueOf(state));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
