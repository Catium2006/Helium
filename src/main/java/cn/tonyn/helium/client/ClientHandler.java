package cn.tonyn.helium.client;

import cn.tonyn.helium.data.item.type.*;
import cn.tonyn.helium.data.̽��;
import cn.tonyn.helium.data.�ɿ�;
import cn.tonyn.helium.mysql.SqlConnection;
import cn.tonyn.helium.operation.InternalCommand;
import cn.tonyn.helium.operation.Message;
import cn.tonyn.helium.operation.Operations;
import cn.tonyn.helium.user.Backpack;
import cn.tonyn.helium.user.User;
import cn.tonyn.log.Logger;
import cn.tonyn.util.OtherTools;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Random;


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

            //�ظ��õ��ַ���
            String reply = "";

            //������ͨ���
            //���Լ� me
            message=message.replace("!m",sender.getUid()+"");
            //����û� random user
            message=message.replace("!ru",new Random().nextInt(SqlConnection.getLengthOf("_user"))+"");
            //����� random number
            message=message.replace("!rn",new Random().nextInt(1024)+"");


            if(!sender.isBaned()){
                {//����¼�
                    if(OtherTools.randomBoolean(3,1000)){

                        reply = reply+sender.getUsername()+":��ˤ����,����ֵ-16"+Endl;
                        sender.setHealth(sender.getHealth()-16);
                    }
                    if (OtherTools.randomBoolean(2,1000)){
                        reply = reply+sender.getUsername()+":һ����ײ����,����ֵ-128"+Endl;
                        sender.setHealth(sender.getHealth()-128);
                    }
                    if (OtherTools.randomBoolean(1,10000)){
                        reply = reply+sender.getUsername()+":�㱻���嵥��,ֱ��ǹ��"+Endl;
                        sender.setHealth(0);
                    }
                    if(OtherTools.randomBoolean(3,10000)){
                        reply = reply+sender.getUsername()+":ʱ�����˸�����౦�����������,����+1024"+Endl;
                        sender.setExperience(sender.getExperience()+1024);
                    }
                    if(OtherTools.randomBoolean(1,10000)){
                        reply = reply+sender.getUsername()+":�������Ƽ��ṩ�������µ�ҽ�ƿƼ�,����������������,����+1024"+Endl;
                        sender.setHealth(sender.getHealth()+1024);
                    }
                }

                if(client.Content.startsWith(Message.GroupMessage)){
                    System.out.println("Ⱥ��Ϣ:"+message);
                    sender.setCount(sender.getCount()+1);
                    sender.setExperience(sender.getExperience()+1);
                    if(OtherTools.randomBoolean(1,4)){
                        sender.setMoney(sender.getMoney()+1);
                    }
                    if(OtherTools.randomBoolean(1,2)){
                        sender.setHealth(sender.getHealth()-1);
                    }


                    if(message.equals("*����")){
                        reply = reply +
                                sender.getUsername()+"������:"+
                                Endl+"UID: "+sender.getUid()+
                                Endl+"����ֵ: "+sender.getHealth()+
                                Endl+"����: "+sender.getForce()+
                                Endl+"����: "+sender.getExperience()+
                                Endl+"����: "+sender.getMoney()+
                                Endl+"�ܷ���: "+sender.getCount()+
                                Endl+"Ȩ��: "+sender.getPermission()+
                                Endl+"�·�: "+sender.getClothing()+
                                Endl+"װ��: "+sender.getEquipment()+
                                Endl+"�ܷ���: "+sender.getCount()+Endl;
                    }

                    if(message.equals("*����")){
                        Backpack backpack =new Backpack(sender);
                        reply = reply+sender.getUsername()+"�ı���:"+Endl+backpack.toString()+Endl;

                    }

                    if(message.startsWith("*��")){
                        String food = message.replace("*��","");
                        food = food.replace(" ","");
                        if(Item.byName(food)!=null&&Item.byName(food).���Գ�){
                            Backpack backpack = new Backpack(sender);
                            if(backpack.getNumberOf(ʳ��.byName(food))>0){
                                backpack.addItem(ʳ��.byName(food),-1);
                                //��Ҫ�ֶ�����
                                backpack.update();
                                reply=reply+"�����һ��"+ ʳ��.byName(food).Name+","+ʳ��.byName(food).ʹ��Ч��+",����ֵ+"+ʳ��.byName(food).�ָ�����ֵ+Endl;
                                sender.setHealth(sender.getHealth()+ʳ��.byName(food).�ָ�����ֵ);
                            }else {
                                reply=reply+"��û�����ֶ���!"+Endl;
                            }

                        }else{
                            reply=reply+"���ֶ������ܳ�!"+Endl;
                        }
                    }

                    if(message.startsWith("*�� ")){
                        String[] all=message.split(" ");
                        String target=all[1];
                        String number=all[2];
                        String thing=all[3];
                        Backpack backpack_s = new Backpack(sender);
                        Backpack backpack_t = new Backpack(User.byUid(Integer.valueOf(target)));
                        if(Item.byName(thing)!=null){
                            if(sender.isAdmin()){
                                backpack_t.addItem(Item.byName(thing),Integer.parseInt(number));
                                //��Ҫ�ֶ�����
                                backpack_t.update();
                                reply=reply+"�Ѿ���UID"+target+"("+User.byUid(Integer.valueOf(target)).getUsername()+")��"+number+"��"+thing+Endl;
                            }else {
                                if(backpack_s.getNumberOf(Item.byName(thing))>0){
                                    if(Integer.parseInt(number)>=1){
                                        if(Integer.parseInt(target)!=sender.getUid()){
                                            backpack_s.addItem(Item.byName(thing),-1);
                                            //��Ҫ�ֶ�����
                                            backpack_s.update();

                                            backpack_t.addItem(Item.byName(thing),Integer.parseInt(number));
                                            //��Ҫ�ֶ�����
                                            backpack_t.update();
                                            reply=reply+"�Ѿ���UID"+target+"��"+number+"��"+thing;
                                        }else {
                                            reply=reply+"����ë����,���Լ�?"+Endl;
                                        }

                                    }else {
                                        reply=reply+"����ë����,����?"+Endl;
                                    }

                                }else {
                                    reply=reply+"��û�����ֶ���!"+Endl;
                                }
                            }

                        }else {
                            reply=reply+thing+"��ʲô����?"+Endl;
                        }
                    }

                    if(message.startsWith("*�ϳ� ")){
                        String[] all=message.split(" ");
                        int number=Integer.parseInt(all[1]);
                        String thing=all[2];
                        Backpack backpack_s = new Backpack(sender);
                        Item item = Item.byName(thing);
                        if(item!=null){

                            if(item.���Ժϳ�){
                                if(number>0){
                                    Item[] items = item.�䷽��;
                                    boolean success=true;
                                    String s="";
                                    for(int a=1;a<=number;a++){
                                        for(Item i:items){
                                            backpack_s.addItem(i,-1);
                                        }
                                        for(Item i:items){
                                            int n=backpack_s.getNumberOf(i);
                                            if(n<0){
                                                success=false;
                                                s="��ȱ��"+(-n)+"��"+i+Endl;
                                            }
                                        }

                                    }
                                    reply=reply+s;
                                    if(success){
                                        backpack_s.addItem(item,number*item.�ϳ�����);
                                        backpack_s.update();
                                        reply=reply+number*item.�ϳ�����+"��"+item+"�ѷ�����ı���"+Endl;
                                    }
                                }else {
                                    reply=reply+"�㵽�׺ϳɲ��ϳ�?"+Endl;
                                }
                            }else {
                                reply=reply+"û��������Ʒ�ĺϳɷ�ʽ"+Endl;
                            }

                        }else {
                            reply=reply+thing+"��ʲô����?"+Endl;
                        }

                    }

                    if(message.startsWith("*�� ")){
                        String[] all=message.split(" ");
                        int number=Integer.parseInt(all[1]);
                        String thing=all[2];
                        Backpack backpack_s = new Backpack(sender);
                        Item item = Item.byName(thing);
                        if(item!=null){
                            if(item.���Խ���){
                                int value=item.��ֵ;
                                if(number>=0){
                                    if(value*number<=sender.getMoney()){
                                        sender.setMoney(sender.getMoney()-value*number);
                                        backpack_s.addItem(item,number);
                                        backpack_s.update();
                                        reply=reply+number+"��"+item+"�ѷ�����ı���"+Endl;
                                    }else{
                                        reply=reply+"�㻹��Ҫ"+(value*number-sender.getMoney())+"��������������Щ����"+Endl;
                                    }
                                }else {
                                    reply = reply + "�뵽���ڼ�������!"+Endl;
                                }
                            }else {
                                reply=reply+"����Ʒ���ܽ���!"+Endl;
                            }
                        }else {
                            reply=reply+item+"��ʲô����?";
                        }


                    }

                    if(message.startsWith("*�� ")){
                        String[] all=message.split(" ");
                        int number=Integer.parseInt(all[1]);
                        String thing=all[2];
                        Backpack backpack_s = new Backpack(sender);
                        Item item = Item.byName(thing);
                        if(item!=null){
                            if(item.���Խ���){
                                int value=item.��ֵ-1;
                                if(number>=0){
                                    if(backpack_s.getNumberOf(item)>=number){
                                        backpack_s.addItem(item,-number);
                                        backpack_s.update();
                                        reply=reply+"��������"+number+"��"+item+",�õ�"+value*number+"����"+Endl;
                                        sender.setMoney(sender.getMoney()+value*number);
                                    }else {
                                        reply=reply+"������ô�ණ����?"+Endl;
                                    }
                                }else {
                                    reply = reply + "�뵽���ڼ�����!"+Endl;
                                }
                            }else {
                                reply=reply+"����Ʒ���ܽ���!"+Endl;
                            }
                        }
                    }

                    if(message.startsWith("*�һ� ")){
                        String[] all=message.split(" ");
                        int number=Integer.parseInt(all[1]);
                        String thing=all[2];
                        Backpack backpack_s = new Backpack(sender);
                        ���� hb = ����.byName(thing);
                        int coast=(int) (number*hb.�һ�˰��+number*hb.��ֵ);
                        if(number>0){
                            if(sender.getMoney()>= coast){
                                backpack_s.addItem(hb,number);
                                backpack_s.update();
                                sender.setMoney(sender.getMoney()-hb.��ֵ*number);
                                reply=reply+"�һ��ɹ�!������"+coast+"(˰��)"+Endl;

                            }else{
                                reply=reply+"����!"+Endl;
                            }
                        }else {
                            reply=reply+"�������������?"+Endl;
                        }
                    }

                    if(message.startsWith("*ȡ�� ")){
                        String[] all=message.split(" ");
                        int number=Integer.parseInt(all[1]);
                        String thing=all[2];
                        Backpack backpack_s = new Backpack(sender);
                        ���� hb = ����.byName(thing);
                        int add=(int) (number*hb.��ֵ-number*hb.�һ�˰��);
                        if(number>0){
                            backpack_s.addItem(hb,-number);
                            backpack_s.update();
                            sender.setMoney(sender.getMoney()+add);
                            reply=reply+"�һ��ɹ�,"+add+"(˰��)�Ѽ�������˻�";
                        }else {
                            reply=reply+"�������������?"+Endl;
                        }
                    }

                    if(message.startsWith("*̽��")){

                        String s = message.replace("*̽��","");
                        if(s.equals("")){
                            s=s+new Random().nextInt(100);
                        }
                        //ȡһ�����������
                        int times = Math.abs(s.hashCode()%100);
                        Date date = new Date();
                        //�������ʱ��ͼ���
                        String level="A";
                        if(times<=40){
                            level="A";
                        }
                        if(40<times&&times<=70){
                            level="B";
                        }
                        if(70<times&&times<=100){
                            level="C";
                        }
                        times=times*60*1000;
                        long timestamp = date.getTime()+times;
                        ResultSet rs = SqlConnection.getByUid("_explorer",sender.getUid());
                        if(rs!=null){
                            long l = -1;
                            try{
                                if(rs.next()){
                                    l=rs.getLong("_endtime");
                                    if(l==0){
                                        String sql0="update _explorer set _endtime = '"+timestamp+"' where _uid = "+sender.getUid();
                                        String sql1="update _explorer set _level = '"+level+"' where _uid = "+sender.getUid();
                                        SqlConnection.doSqlNoResult(sql0);
                                        SqlConnection.doSqlNoResult(sql1);
                                        reply = reply+"�㿪ʼ̽����,Ԥ����Ҫ"+(times/1000/60)+"����"+Endl;
                                    }else {
                                        reply = reply+"������̽��!"+Endl;
                                    }
                                }else {
                                    String sql="insert into _explorer(_uid,_endtime,_level) values("+sender.getUid()+",'"+timestamp+"','"+level+"')";
                                    SqlConnection.doSqlNoResult(sql);
                                    reply = reply+"�㿪ʼ̽����,Ԥ����Ҫ"+(times/1000/60)+"����"+Endl;
                                }
                            }catch (SQLException e){
                                Logger.log(e.getMessage(),"SQLException");
                            }


                        }

                    }

                    if(message.equals("*����̽�����")){
                        ResultSet rs = SqlConnection.getByUid("_explorer",sender.getUid());
                        long l0 = -1;
                        String level="A";
                        try{
                            if(rs.next()){
                                l0=rs.getLong("_endtime");
                                level=rs.getString("_level");
                                if(l0!=0){
                                    Date date = new Date();
                                    long l1 = date.getTime();
                                    if(l1>=l0){
                                        Item[] items=new Item[4];
                                        int count=0;
                                        for(Item i:items){
                                            if(level.equals("A")){
                                                i= ̽��.A[new Random().nextInt(̽��.A.length)];
                                            }
                                            if(level.equals("B")){
                                                i= ̽��.B[new Random().nextInt(̽��.B.length)];
                                            }
                                            if(level.equals("C")){
                                                i= ̽��.C[new Random().nextInt(̽��.C.length)];
                                            }
                                            items[count]=i;
                                            count++;
                                        }
                                        reply=reply+"������:";
                                        for(Item i:items){
                                            reply=reply+i+",";
                                        }

                                        reply=reply+Endl;
                                        String sql = "update _explorer set _endtime = 0 where _uid = "+sender.getUid();
                                        SqlConnection.doSqlNoResult(sql);
                                        Backpack  backpack = new Backpack(sender);
                                        for(Item item:items){
                                            int i = sender.getLuck()/10+1;
                                            backpack.addItem(item,i);
                                        }
                                        backpack.update();
                                    }else{
                                        reply=reply+"̽����û�н���,����Ҫ"+((l0-l1)/1000/60)+"����"+Endl;
                                    }
                                }else {
                                    reply=reply+"��û��̽��"+Endl;
                                }

                            }else {
                                reply=reply+"��û��̽��"+Endl;
                            }
                        }catch (SQLException e) {
                            Logger.log(e.getMessage(), "SQLException");
                        }

                    }

                    if(message.startsWith("*�鿴")){
                        message=message.replace(" ","");
                        message=message.replace("*�鿴","");
                        Item thing = Item.byName(message);
                        if(thing!=null){
                            reply=reply+thing.getInfo();
                        }
                    }


                    if(message.startsWith("*װ��")){
                        String thing = message.replace("*װ��","");
                        if(Item.byName(thing)!=null){
                            Backpack backpack_s=new Backpack(sender);
                            if(backpack_s.getNumberOf(Item.byName(thing))>0){
                                backpack_s.addItem(sender.getEquipment(),1);
                                sender.setEquipment(װ��.byName(thing));
                                backpack_s.addItem(sender.getEquipment(),-1);
                                backpack_s.update();
                            }else {
                                reply=reply+"��û�����ֶ���!"+Endl;
                            }

                        }else{
                            reply=reply+thing+"��ʲô?"+Endl;
                        }
                    }


                    if(message.startsWith("*��")){
                        String thing = message.replace("*��","");
                        if(Item.byName(thing)!=null){
                            Backpack backpack_s=new Backpack(sender);
                            if(backpack_s.getNumberOf(Item.byName(thing))>0){
                                backpack_s.addItem(sender.getClothing(),1);
                                sender.setClothing(�·�.byName(thing));
                                backpack_s.addItem(sender.getClothing(),-1);
                                backpack_s.update();
                            }else {
                                reply=reply+"��û�����ֶ���!"+Endl;
                            }

                        }else{
                            reply=reply+thing+"��ʲô?"+Endl;
                        }
                    }

                    if(message.startsWith("*���� ")){
                        Backpack backpack_s=new Backpack(sender);
                        String[] all=message.split(" ");
                        int number=Integer.parseInt(all[1]);
                        String thing=all[2];
                        Item item=Item.byName(thing);
                        if(backpack_s.contains(Item.byName("��¯"))){
                            if(item!=null){
                                if(item.ȼ������>=0){
                                    ResultSet rs = SqlConnection.getByUid("��¯",sender.getUid());
                                    int time=-1;
                                    try{
                                        if(rs.next()){
                                            time=rs.getInt("ʣ������");
                                        }else{
                                            reply=reply+"��¯ȼ�ϲ���"+Endl;
                                        }
                                    }catch (SQLException e){
                                        Logger.log(e.getMessage(),"SQLException");
                                    }
                                    if(backpack_s.getNumberOf(item)>=number){
                                        if(time>=(item.ȼ������*number)){
                                            time=time-(item.ȼ������*number);
                                            backpack_s.addItem(item.�������,number);
                                            backpack_s.addItem(item,-number);
                                            backpack_s.update();
                                            sender.setExperience(sender.getExperience()+10*number);
                                            String sql = "update ��¯ set ʣ������ = "+time+" where _uid = "+sender.getUid();
                                            SqlConnection.doSqlNoResult(sql);
                                            reply=reply+"�����ɹ�,�õ�"+number+"��"+item.�������+Endl+"ʣ������:"+time;

                                        }else {
                                            reply=reply+"��¯ȼ�ϲ���"+Endl;
                                        }
                                    }else {
                                        reply=reply+"��û���㹻�Ĳ���"+Endl;
                                    }
                                }else {
                                    reply=reply+"����Ʒ��������"+Endl;
                                }
                            }else {
                                reply=reply+thing+"��ʲô����?";
                            }
                        }else{
                            reply=reply+"��û����¯!"+Endl;
                        }
                    }

                    if(message.startsWith("*���ȼ�� ")){
                        Backpack backpack_s=new Backpack(sender);
                        String[] all=message.split(" ");
                        int number=Integer.parseInt(all[1]);
                        String thing=all[2];
                        Item item=Item.byName(thing);
                        if(backpack_s.contains(Item.byName("��¯"))){
                            if(item!=null){
                                if(item.ȼ��ʱ��>=0){
                                    if(backpack_s.getNumberOf(item)>=number){
                                        int time = item.ȼ��ʱ��*number;
                                        ResultSet rs = SqlConnection.getByUid("��¯",sender.getUid());
                                        try{
                                            if(rs.next()){
                                                time=time+rs.getInt("ʣ������");
                                                String sql = "update ��¯ set ʣ������ = "+time+" where _uid = "+sender.getUid();
                                                SqlConnection.doSqlNoResult(sql);
                                            }else{
                                                String sql = "insert into ��¯(_uid,ʣ������) values("+sender.getUid()+","+time+")";
                                                SqlConnection.doSqlNoResult(sql);
                                            }
                                        }catch (SQLException e){
                                            Logger.log(e.getMessage(),"SQLException");
                                        }
                                        reply=reply+"��ӳɹ�,ʣ������:"+time+Endl;
                                        backpack_s.addItem(item,-number);
                                        backpack_s.update();
                                    }else {
                                        reply=reply+"��û����ô�����ֶ���"+Endl;
                                    }
                                }else{
                                    reply=reply+"�ⲻ��һ��ȼ��"+Endl;
                                }
                            }else {
                                reply=reply+thing+"��ʲô����?"+Endl;
                            }
                        }else{
                            reply=reply+"��û����¯!"+Endl;
                        }
                    }

                    if(message.equals("*�ܷ������а�")){
                        String sql = "select * from _user order by _count DESC";
                        ResultSet rs = SqlConnection.doSql(sql);
                        String s="UID    �û�    �ܷ���"+Endl;
                        try{
                            int i = 0;
                            while(rs.next()&&i<10){
                                i++;
                                s=s+i+". "+rs.getInt("_uid")+"  "+rs.getString("_username")+"  "+rs.getInt("_count")+Endl;
                            }
                        }catch (SQLException e){
                            Logger.log(e.getMessage(),"SQLException");
                        }
                        reply=reply+s;

                    }

                    if(message.startsWith("*�ɿ�")){
                        int luck=0;

                        if(((װ��)sender.getEquipment()).����.equals("�ɿ�")){
                            luck=luck+((װ��)sender.getEquipment()).��������;
                        }
                        Item[] items=new Item[3];
                        if(luck>=80){
                            items[0]= �ɿ�.C[new Random().nextInt()];
                        }
                        if(luck>=40){
                            items[1]= �ɿ�.B[new Random().nextInt()];
                        }
                        if(luck>=0){
                            items[2]= �ɿ�.A[new Random().nextInt()];
                        }
                        reply=reply+"�㿪ʼ�ɿ���,��һ����Ҫ�೤ʱ��";
                    }


                    if(message.startsWith("# ")){
                        if(sender.isAdmin()){

                        }else{
                            reply=reply+"��û�й���Ա���!"+Endl;
                        }
                    }

                }

                if(client.Content.startsWith(Message.PersonalMessage)){


                }

                //ִ�г�����,�����������
                if(sender.getHealth()<=0){
                    //reply�Լ�����,ʵ�ֶ����ظ��ĺϲ�
                    reply = reply+Endl+"���Ѿ�����,�Զ����˻��п۳��ķ�֮һǮ���ķ�֮һ����";
                    sender.setHealth(sender.getHealth()+128);
                    sender.setMoney(sender.getMoney()-sender.getMoney()/4);
                    sender.setExperience(sender.getExperience()-sender.getExperience()/4);

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

