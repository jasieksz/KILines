package server;

import model.Direction;
import model.GameState;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.HashMap;
import java.util.Map;

@WebSocket
public class UpdatesWebSocketHandler {

    private Map<Session, String> sessions = new HashMap<>();
    private GameState gameState;

    public UpdatesWebSocketHandler(GameState gameState){
        this.gameState = gameState;
    }

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String nick = this.getNick(user);
        //TODO check
        this.sessions.put(user, nick);
        System.out.println(nick);
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        this.sessions.remove(user);
        //TODO delete player form gameState aka kill him
        System.out.println("Deleting " + user + "for reason: " + reason + "(" + statusCode + ")");
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        String nick = getNick(user);
        System.out.println(message);
        this.gameState.changePlayerDirection("Jasiek", Direction.valueOf(message)); // TODO change 12 for nick
    }

    private String getNick(Session user){
        return user.getUpgradeRequest()
                .getCookies()
                .stream()
                .filter(c -> c.getName().equals("nick")).findFirst()
                .get()
                .toString();
    }

    public void broadcast(GameState state){
        sessions.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(String.valueOf(state));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
