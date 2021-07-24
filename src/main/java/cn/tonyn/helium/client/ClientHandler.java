package cn.tonyn.helium.client;

import cn.tonyn.helium.mysql.SqlConnection;
import cn.tonyn.helium.operation.InternalCommand;
import cn.tonyn.helium.operation.Operations;
import cn.tonyn.helium.user.User;
import cn.tonyn.log.Logger;


public class ClientHandler {
    public ClientHandler(Client client){
        Logger.log("Get a client: "+client.Name+". Delay is "+client.Delay);

        if(client.OperationType==Operations.InternalCommand){
            //if we get a internal command

            if(client.Content==InternalCommand.Ping){
                client.send(client.buildReply(true, Operations.InternalCommand,InternalCommand.Ping));
            }

            if(client.Content.startsWith(InternalCommand.CreateUser)){
                String username = client.Content.replace(InternalCommand.CreateUser+":","");
                User.createNewUser(username);
            }


        }

        if(client.OperationType==Operations.Message){


        }

    }
}
