package cn.tonyn.helium;

import cn.tonyn.log.Logger;

public class ClientHandler {
    public ClientHandler(Client client){
        Logger.log("Get a client: "+client.NAME);
        String Operation = client.OperationType;
        String API =client.API;
        String Platform = client.Platform;
        String SystemInfo = client.SystemInfo;
        long Timestamp = client.Timestamp;

    }
}
