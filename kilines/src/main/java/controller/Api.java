package controller;

import org.json.simple.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static spark.Spark.*;


public class Api {
    public void myMain() {
        Map<String, Integer> users = new HashMap<>();

        get("/login/:nick", (req, res)-> {
            String nick = req.params(":nick");
            return generateLoginRequest(users, nick).toJSONString();
        });
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
