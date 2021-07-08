package cn.tonyn.helium;

import cn.tonyn.log.Logger;

public class ClientHandler {
    public ClientHandler(Client client){
        Logger.log("Get a client: "+client.NAME);
    }
}
