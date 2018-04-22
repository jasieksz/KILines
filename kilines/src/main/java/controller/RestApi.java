package controller;

import model.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import static spark.Spark.*;


public class RestApi {

    private GameState.InitializerBuilder builder = new GameState.InitializerBuilder(2)
            .boardX(640)
            .boardY(480);

    Map<String, Integer> users = new HashMap<>();

    public void loginUsersRequest() {

        get("/login/:nick", (req, res)-> {
            String nick = req.params(":nick");
            return generateLoginRequest(users, nick).toJSONString();
        });

        get("/init", (req, res) -> generateInitRequest(builder.build()).toJSONString());
    }

    private JSONObject generateInitRequest(GameState builder){
        JSONObject json = new JSONObject();
        json.put("type", "init");

        JSONArray array = new JSONArray();

        for (Motorcycle motorcycle: builder.getMotorcycles()){
            JSONObject item = new JSONObject();

            item.put("nick", getNickname(motorcycle));

            JSONObject jsonPos = new JSONObject();
            jsonPos.put("x", motorcycle.getPosition().getX());
            jsonPos.put("y", motorcycle.getPosition().getY());

            item.put("pos", jsonPos);
            array.add(item);
        }

        json.put("players", array);
        return json;
    }

    private JSONObject generateLoginRequest(Map<String, Integer> users, String nick){
        JSONObject json = new JSONObject();

        json.put("type", "login");
        boolean isOk = !users.containsKey(nick);
        json.put("isOk", isOk);

        if(isOk){
            int token = generateToken();
            json.put("token",token);
            users.put(nick,token);

            builder.addPlayer(new Point(28,12), new PlayerIdentifier(Color.BLUE, token));

        }else {
            json.put("msg", "Nickname occupied");
        }
        return json;
    }

    private int generateToken(){
        Random random = new Random();
        int token;
        do {
            token = random.nextInt(100);
        } while(users.containsValue(token));

        return token;
    }

    private String getNickname(Motorcycle motorcycle) {
        int playerId = motorcycle.getPlayerId().getPlayerId();
        boolean hasId = users.containsValue(playerId);
        String nickName = "";
        for (Map.Entry<String, Integer> entry : users.entrySet()) {
            if (entry.getValue().equals(playerId)) {
                nickName = entry.getKey();
            }
        }
        return nickName;
    }

}
