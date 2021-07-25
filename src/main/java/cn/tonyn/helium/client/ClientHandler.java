package cn.tonyn.helium.client;

import cn.tonyn.helium.operation.InternalCommand;
import cn.tonyn.helium.operation.Message;
import cn.tonyn.helium.operation.Operations;
import cn.tonyn.helium.user.User;
import cn.tonyn.log.Logger;


public class ClientHandler {
    public static final String Endl = "E_N_D_L";
    public static final String Slipt = "S_L_I_P_T";
    public ClientHandler(Client client){
        Logger.log("Get a client: "+client.Name+". Delay is "+client.Delay);


        if(client.OperationType.equals(Operations.InternalCommand)){

            if(client.Content==InternalCommand.Ping){
                client.send(client.buildReply(true, Operations.InternalCommand,InternalCommand.Ping));
            }

            if(client.Content.startsWith(InternalCommand.CreateUser)){
                String username = client.Content.replace(InternalCommand.CreateUser,"");
                User u = User.createNewUser(username);
                if(u!=null){
                    String s = client.buildReply(true,InternalCommand.CreateUser,u.getUid()+"");
                    System.out.println(u);
                    client.send(s);
                }else {
                    String s = client.buildReply(true,InternalCommand.CreateUser,"-1024");
                    client.send(s);
                }

            }


        }

        if(client.OperationType.equals(Operations.Message)){
            String[] msg_string = client.Content.replace(Message.GroupMessage,"").split(Slipt);
            //例子: 114514S_L_I_P_T我是笨蛋!
            //解析为[0]=114514 , [1]=我是笨蛋!
            int uid = Integer.valueOf(msg_string[0]);
            String message = msg_string[1];
            User sender = User.byUid(uid);
            String reply = "";

            if(!sender.isBaned()){

                if(client.Content.startsWith(Message.GroupMessage)){
                    System.out.println("群消息:"+message);
                    sender.setCount(sender.getCount()+1);
                    sender.setExperience(sender.getExperience()+1);
                    sender.setMoney(sender.getMoney()+1);

                    if(message.equals("*属性")){
                        reply = reply +
                                Endl+sender.getUsername()+"的属性:"+
                                Endl+"UID: "+sender.getUid()+
                                Endl+"生命值: "+sender.getHealth()+
                                Endl+"力量: "+sender.getForce()+
                                Endl+"经验: "+sender.getExperience()+
                                Endl+"大洋: "+sender.getMoney()+
                                Endl+"总发言: "+sender.getCount()+
                                Endl+"权限: "+sender.getPermission();
                    }

                }

                if(client.Content.startsWith(Message.PersonalMessage)){


                }

                //执行常规检测,比如死亡检测
                if(sender.getHealth()<=0){
                    //reply自己增加,实现多条回复的合并
                    reply = reply+Endl+"你已经死了,自动从账户中扣除128大洋和64经验";
                    sender.setHealth(sender.getHealth()+128);
                    sender.setMoney(sender.getMoney()-128);
                    sender.setExperience(sender.getExperience()-128);

                }
                System.out.println(reply);
                String reply_for_send = client.buildReply(true,Operations.Message,reply);
                client.send(reply_for_send);

            }else{
                System.out.println(sender+"is banned.");
                String reply_for_send = client.buildReply(false,Operations.InternalCommand,InternalCommand.UserWasBanned+sender);
            }

        }

    }

}

