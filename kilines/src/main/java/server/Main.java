package server;

import controller.RestApi;

public class Main {
    public static void main(String[] args) {
        Server server = new Server();

        RestApi api = new RestApi(server.getGameState());
        api.loginUsersRequest(server);
        server.run(api);
    }
}
