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
            //����: 114514S_L_I_P_T���Ǳ���!
            //����Ϊ[0]=114514 , [1]=���Ǳ���!
            int uid = Integer.valueOf(msg_string[0]);
            String message = msg_string[1];
            User sender = User.byUid(uid);
            String reply = "";

            if(!sender.isBaned()){

                if(client.Content.startsWith(Message.GroupMessage)){
                    System.out.println("Ⱥ��Ϣ:"+message);
                    sender.setCount(sender.getCount()+1);
                    sender.setExperience(sender.getExperience()+1);
                    sender.setMoney(sender.getMoney()+1);

                    if(message.equals("*����")){
                        reply = reply +
                                Endl+sender.getUsername()+"������:"+
                                Endl+"UID: "+sender.getUid()+
                                Endl+"����ֵ: "+sender.getHealth()+
                                Endl+"����: "+sender.getForce()+
                                Endl+"����: "+sender.getExperience()+
                                Endl+"����: "+sender.getMoney()+
                                Endl+"�ܷ���: "+sender.getCount()+
                                Endl+"Ȩ��: "+sender.getPermission();
                    }

                }

                if(client.Content.startsWith(Message.PersonalMessage)){


                }

                //ִ�г�����,�����������
                if(sender.getHealth()<=0){
                    //reply�Լ�����,ʵ�ֶ����ظ��ĺϲ�
                    reply = reply+Endl+"���Ѿ�����,�Զ����˻��п۳�128�����64����";
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

