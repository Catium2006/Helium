package cn.tonyn.helium.client;

import cn.tonyn.helium.mysql.SqlConnection;
import cn.tonyn.helium.operation.InternalCommand;
import cn.tonyn.helium.operation.Message;
import cn.tonyn.helium.operation.Operations;
import cn.tonyn.helium.user.User;
import cn.tonyn.log.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;


public class ClientHandler {
    public ClientHandler(Client client){
        Logger.log("Get a client: "+client.Name+". Delay is "+client.Delay);



        if(client.OperationType.equals(Operations.InternalCommand)){
            //if we get a internal command
            System.out.println("there is a INTERNALCOMMAND");

            if(client.Content==InternalCommand.Ping){
                client.send(client.buildReply(true, Operations.InternalCommand,InternalCommand.Ping));
            }

            if(client.Content.startsWith(InternalCommand.CreateUser)){
                String username = client.Content.replace(InternalCommand.CreateUser,"");
                User.createNewUser(username);
            }


        }

        if(client.OperationType.equals(Operations.Message)){
            System.out.println("there is a MESSAGE");
            if(client.Content.startsWith(Message.GroupMessage)){
                String[] msg_string = client.Content.replace(Message.GroupMessage,"").split("S_L_I_P_T");
                //例子: 114514S_L_I_P_T我是笨蛋!
                //解析为[0]=114514 , [1]=我是笨蛋!
                int uid = Integer.valueOf(msg_string[0]);
                String message = msg_string[1];

                User sender = User.byUid(uid);
                sender.setCount(sender.getCount()+1);

            }

            if(client.Content.startsWith(Message.PersonalMessage)){
                String[] msg_string = client.Content.replace(Message.GroupMessage,"").split("S_L_I_P_T");
                //例子: 114514S_L_I_P_T我是笨蛋!
                //解析为[0]=114514 , [1]=我是笨蛋!
                int uid = Integer.valueOf(msg_string[0]);
                String message = msg_string[1];


            }


        }



    }
}
