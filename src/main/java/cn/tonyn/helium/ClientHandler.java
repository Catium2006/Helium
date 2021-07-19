package cn.tonyn.helium;

import cn.tonyn.helium.OperationType.InternalCommand;
import cn.tonyn.helium.OperationType.Operations;
import cn.tonyn.log.Logger;

public class ClientHandler {
    public ClientHandler(Client client){
        Logger.log("Get a client: "+client.Name+"\r\nDelay is "+client.Delay);
        client.send(client.buildReply(true, Operations.InternalCommand,InternalCommand.Ping));
    }
}
