package controller;

import model.GameState;
import model.Motorcycle;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


public class Api {
    public void loginUsersRequest() {

        GameState builder = null;
        Map<String, Integer> users = new HashMap<>();

        get("/login/:nick", (req, res)-> {
            String nick = req.params(":nick");
            return generateLoginRequest(users, nick).toJSONString();
        });

        get("/init", (req, res) -> generateInitRequest(builder).toJSONString());
    }

    private JSONObject generateInitRequest(GameState builder){
        JSONObject json = new JSONObject();
        json.put("type", "init");

        JSONArray array = new JSONArray();

        //
        for (Motorcycle motorcycle: builder.getMotorcycles()){
            JSONObject item = new JSONObject();

            //todo: use mapping
            item.put("nick", "Adma");

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
        boolean isOk = users.containsKey(nick);
        json.put("isOk", isOk);

        if(isOk){
            //todo: change token generation
            json.put("token", 33);


        }else {
            json.put("msg", "Nickname occupied");
        }
        return json;
    }

}
