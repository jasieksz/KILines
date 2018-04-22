package server;

import model.Direction;
import model.GameState;
import model.Motorcycle;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@WebSocket
public class UpdatesWebSocketHandler {

    private Map<Session, String> sessions = new ConcurrentHashMap<>();
    private GameState gameState;

    public UpdatesWebSocketHandler(GameState gameState){
        this.gameState = gameState;
    }

    @OnWebSocketConnect
    public void onConnect(Session user) throws Exception {
        String nick = this.getNick(user);
        //TODO check
        gameState.getGameUserByNickname(nick);
        this.sessions.put(user, nick);
        System.out.println("new connection: " + nick);
        System.out.println("session map: " + sessions.entrySet());
    }

    @OnWebSocketClose
    public void onClose(Session user, int statusCode, String reason) {
        this.sessions.remove(user);

        try {
            String nick = this.getNick(user);
            gameState.getGameUserByNickname(nick).ifPresent(e -> e.setAlive(false));

            System.out.println("Deleting " + user + "for reason: " + reason + "(" + statusCode + ")");
            System.out.println("seesion map: " + sessions.entrySet());
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @OnWebSocketMessage
    public void onMessage(Session user, String message) {
        try {
            String nick = getNick(user);
            System.out.println("got message: " + message);
            this.gameState.changePlayerDirection(nick, Direction.valueOf(message));
        }catch (Exception e) {
            user.close();
        }
    }

    private String getNick(Session user) throws Exception {
        return user.getUpgradeRequest()
                .getCookies()
                .stream()
                .filter(c -> c.getName().equals("nick"))
                .findFirst()
                .orElseThrow(() -> new Exception("dsfa"))
                .toString();
    }

    public void broadcast(String message){
        sessions.keySet().stream().filter(Session::isOpen).forEach(session -> {
            try {
                session.getRemote().sendString(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
