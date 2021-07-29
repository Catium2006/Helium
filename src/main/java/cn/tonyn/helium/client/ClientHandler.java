package cn.tonyn.helium.client;

import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.data.item.type.货币;
import cn.tonyn.helium.data.item.type.食物;
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
            //例子: 114514S_L_I_P_T我是笨蛋!
            //解析为[0]=114514 , [1]=我是笨蛋!
            int uid = Integer.valueOf(msg_string[0]);
            String message = msg_string[1];
            User sender = User.byUid(uid);
            String reply = "";

            if(!sender.isBaned()){
                {//随机事件
                    if(OtherTools.randomBoolean(3,1000)){
                        reply = reply+"你摔倒了,生命值-16"+Endl;
                        sender.setHealth(sender.getHealth()-16);
                    }
                    if (OtherTools.randomBoolean(2,1000)){
                        reply = reply+"一辆车撞了你,生命值-128"+Endl;
                        sender.setHealth(sender.getHealth()-128);
                    }
                    if (OtherTools.randomBoolean(1,1000)){
                        reply = reply+"你被拉清单了,直接枪毙"+Endl;
                        sender.setHealth(0);
                    }
                    if(OtherTools.randomBoolean(5,10000)){
                        reply = reply+"时间老人给了许多宝贵的人生经验,经验+1024"+Endl;
                        sender.setExperience(sender.getExperience()+1024);
                    }
                    if(OtherTools.randomBoolean(5,10000)){
                        reply = reply+"避难所科技提供给你最新的医疗科技,你的生命大大提升了,生命+1024"+Endl;
                        sender.setHealth(sender.getHealth()+1024);
                    }
                }
                if(client.Content.startsWith(Message.GroupMessage)){
                    System.out.println("群消息:"+message);
                    sender.setCount(sender.getCount()+1);
                    sender.setExperience(sender.getExperience()+1);
                    sender.setMoney(sender.getMoney()+1);

                    if(message.equals("*属性")){
                        reply = reply +
                                sender.getUsername()+"的属性:"+
                                Endl+"UID: "+sender.getUid()+
                                Endl+"生命值: "+sender.getHealth()+
                                Endl+"力量: "+sender.getForce()+
                                Endl+"经验: "+sender.getExperience()+
                                Endl+"大洋: "+sender.getMoney()+
                                Endl+"总发言: "+sender.getCount()+
                                Endl+"权限: "+sender.getPermission()+Endl;
                    }

                    if(message.equals("*背包")){
                        Backpack backpack =new Backpack(sender);
                        reply = reply+sender.getUsername()+"的背包:"+Endl+backpack.toString()+Endl;

                    }

                    if(message.startsWith("*吃")){
                        String food = message.replace("*吃","");
                        food = food.replace(" ","");
                        if(食物.byName(food)!=null){
                            Backpack backpack = new Backpack(sender);
                            if(backpack.getNumberOf(食物.byName(food))>0){
                                backpack.addItem(食物.byName(food),-1);
                                //需要手动更新
                                backpack.update();
                                reply=reply+"你吃了一个"+ 食物.byName(food).Name+","+食物.byName(food).使用效果+",生命值+"+食物.byName(food).恢复生命值+Endl;
                                sender.setHealth(sender.getHealth()+食物.byName(food).恢复生命值);
                            }else {
                                reply=reply+"你没有这种东西!"+Endl;
                            }

                        }else{
                            reply=reply+"这种东西不能吃!"+Endl;
                        }
                    }

                    if(message.startsWith("*给 ")){
                        String[] all=message.split(" ");
                        String target=all[1];
                        String number=all[2];
                        String thing=all[3];
                        Backpack backpack_s = new Backpack(sender);
                        Backpack backpack_t = new Backpack(User.byUid(Integer.valueOf(target)));
                        if(Item.byName(thing)!=null){
                            if(sender.isAdmin()){
                                backpack_t.addItem(Item.byName(thing),Integer.parseInt(number));
                                //需要手动更新
                                backpack_t.update();
                                reply=reply+"已经给UID"+target+"了"+number+"个"+thing+Endl;
                            }else {
                                if(backpack_s.getNumberOf(Item.byName(thing))>0){
                                    if(Integer.parseInt(number)>=1){
                                        if(Integer.parseInt(target)!=sender.getUid()){
                                            backpack_s.addItem(Item.byName(thing),-1);
                                            //需要手动更新
                                            backpack_s.update();

                                            backpack_t.addItem(Item.byName(thing),Integer.parseInt(number));
                                            //需要手动更新
                                            backpack_t.update();
                                            reply=reply+"已经给UID"+target+"了"+number+"个"+thing;
                                        }else {
                                            reply=reply+"你有毛病吧,给自己?"+Endl;
                                        }

                                    }else {
                                        reply=reply+"你有毛病吧,负数?"+Endl;
                                    }

                                }else {
                                    reply=reply+"你没有这种东西!"+Endl;
                                }
                            }

                        }else {
                            reply=reply+thing+"是什么东西?"+Endl;
                        }
                    }

                    if(message.startsWith("*合成 ")){
                        String[] all=message.split(" ");
                        int number=Integer.parseInt(all[1]);
                        String thing=all[2];
                        Backpack backpack_s = new Backpack(sender);
                        Item item = Item.byName(thing);

                        if(item!=null){

                            if(item.可以合成){
                                if(number>=1){
                                    Item[] items = item.配方表;
                                    boolean success=true;
                                    for(int a=1;a<=number;a++){
                                        for(Item i:items){
                                            backpack_s.addItem(i,-1);
                                        }
                                        for(Item i:items){
                                            int n=backpack_s.getNumberOf(i);
                                            if(n<=0){
                                                success=false;
                                                reply=reply+"你缺少"+(-n)+"个"+i+Endl;
                                            }
                                        }
                                    }
                                    if(success){
                                        backpack_s.addItem(item,number);
                                        backpack_s.update();
                                        reply=reply+number+"个"+item+"已放入你的背包"+Endl;
                                    }
                                }else {
                                    reply=reply+"你有毛病吧,负数?"+Endl;
                                }
                            }else {
                                reply=reply+"没有这种物品的合成方式"+Endl;
                            }

                        }else {
                            reply=reply+thing+"是什么东西?"+Endl;
                        }

                    }

                    if(message.startsWith("*买 ")){
                        String[] all=message.split(" ");
                        int number=Integer.parseInt(all[1]);
                        String thing=all[2];
                        Backpack backpack_s = new Backpack(sender);
                        Item item = Item.byName(thing);
                        if(item.可以交易){
                            int value=item.价值;
                            if(number>=0){
                                if(value*number<=sender.getMoney()){
                                    sender.setMoney(sender.getMoney()-value);
                                    backpack_s.addItem(item,number);
                                    backpack_s.update();
                                    reply=reply+number+"个"+item+"已放入你的背包"+Endl;
                                }else{
                                    reply=reply+"你还需要"+(value*number-sender.getMoney())+"大洋才能买得起这些东西"+Endl;
                                }
                            }else {
                                reply = reply + "请到隔壁家卖东西!"+Endl;
                            }
                        }else {
                            reply=reply+"此物品不能交易!"+Endl;
                        }

                    }

                    if(message.startsWith("*卖 ")){
                        String[] all=message.split(" ");
                        int number=Integer.parseInt(all[1]);
                        String thing=all[2];
                        Backpack backpack_s = new Backpack(sender);
                        Item item = Item.byName(thing);
                        if(item.可以交易){
                            int value=item.价值-1;
                            if(number>=0){
                                if(backpack_s.getNumberOf(item)>=number){
                                    backpack_s.addItem(item,-number);
                                    backpack_s.update();
                                    reply=reply+"你卖出了"+number+"个"+item+",得到"+value*number+"大洋"+Endl;
                                    sender.setMoney(sender.getMoney()+value*number);
                                }else {
                                    reply=reply+"你有这么多东西吗?"+Endl;
                                }
                            }else {
                                reply = reply + "请到隔壁家买东西!"+Endl;
                            }
                        }else {
                            reply=reply+"此物品不能交易!"+Endl;
                        }

                    }

                    if(message.startsWith("*兑换 ")){
                        String[] all=message.split(" ");
                        int number=Integer.parseInt(all[1]);
                        String thing=all[2];
                        Backpack backpack_s = new Backpack(sender);
                        货币 hb = 货币.byName(thing);
                        int coast=(int) (number*hb.兑换税率+number*hb.价值);
                        if(number>0){
                            if(sender.getMoney()>= coast){
                                backpack_s.addItem(hb,number);
                                backpack_s.update();
                                reply=reply+"兑换成功!共消费"+coast+"(含税)"+Endl;

                            }else{
                                reply=reply+"余额不足!"+Endl;
                            }
                        }else {
                            reply=reply+"你搁这儿搁这儿呢?"+Endl;
                        }

                    }

                    if(message.startsWith("*探索")){
                        String s = message.replace("*探索","");
                        if(s.equals("")){
                            s=s+new Random().nextInt(100);
                        }
                        //取一个随机分钟数
                        int times = Math.abs(s.hashCode()%100);
                        Date date = new Date();
                        //算出结束时间
                        times=times*60*1000;
                        long timestamp = date.getTime()+times;
                        ResultSet rs = SqlConnection.getByUid("_explorer",sender.getUid());
                        if(rs!=null){
                            long l = -1;
                            try{
                                if(rs.next()){
                                    l=rs.getLong("_endtime");
                                    if(l==0){
                                        String sql="update _explorer set _endtime = '"+timestamp+"' where _uid = "+sender.getUid();
                                        SqlConnection.doSqlNoResult(sql);
                                        reply = reply+"你开始探索了,预计需要"+(times/1000/60)+"分钟"+Endl;
                                    }else {
                                        reply = reply+"你正在探索!"+Endl;
                                    }
                                }else {
                                    String sql="insert into _explorer(_uid,_endtime) values("+sender.getUid()+",'"+timestamp+"')";
                                    SqlConnection.doSqlNoResult(sql);
                                    reply = reply+"你开始探索了,预计需要"+(times/1000/60)+"分钟"+Endl;
                                }
                            }catch (SQLException e){
                                Logger.log(e.getMessage(),"SQLException");
                            }


                        }

                    }

                    if(message.equals("*整理探索结果")){
                        ResultSet rs = SqlConnection.getByUid("_explorer",sender.getUid());
                        long l0 = -1;
                        try{
                            if(rs.next()){
                                l0=rs.getLong("_endtime");
                                Logger.log(""+l0,"Debug");
                                if(l0!=0){
                                    Date date = new Date();
                                    long l1 = date.getTime();
                                    if(l1>=l0){
                                        Item[] items=new Item[4];
                                        int count = 0;
                                        for(Item i:items){
                                            if(OtherTools.randomBoolean(1,5)){
                                                items[count]=Item.byName("小麦");
                                            }
                                            if(OtherTools.randomBoolean(1,10)){
                                                items[count]=Item.byName("面包");
                                            }
                                            if(OtherTools.randomBoolean(1,20)){
                                                items[count]=Item.byName("啤酒");
                                            }
                                            if(OtherTools.randomBoolean(1,30)){
                                                items[count]=Item.byName("岩浆");
                                            }
                                            if(OtherTools.randomBoolean(1,40)){
                                                Item.byName("牛子");
                                            }
                                            if(items[count]==null){
                                                items[count]=Item.byName("小麦");
                                            }
                                            count++;
                                        }

                                        reply=reply+"你获得了:";
                                        for(Item i:items){
                                            reply=reply+i+",";
                                        }

                                        reply=reply+Endl;
                                        String sql = "update _explorer set _endtime = 0 where _uid = "+sender.getUid();
                                        SqlConnection.doSqlNoResult(sql);
                                        Backpack  backpack = new Backpack(sender);
                                        for(Item item:items){
                                            backpack.addItem(item,1);
                                        }
                                        backpack.update();
                                    }else{
                                        reply=reply+"探索还没有结束,还需要"+((l0-l1)/1000/60)+"分钟"+Endl;
                                    }
                                }else {
                                    reply=reply+"你没在探索"+Endl;
                                }

                            }else {
                                reply=reply+"你没在探索"+Endl;
                            }
                        }catch (SQLException e) {
                            Logger.log(e.getMessage(), "SQLException");
                        }

                    }

                    if(message.startsWith("*查看")){
                        message=message.replace(" ","");
                        message=message.replace("*查看","");
                        Item thing = Item.byName(message);
                        if(thing!=null){
                            reply=reply+thing+"的信息:"+thing.信息;
                        }
                    }


                    if(message.startsWith("# ")){
                        if(sender.isAdmin()){

                        }else{
                            reply=reply+"你没有管理员身份!"+Endl;
                        }
                    }

                }

                if(client.Content.startsWith(Message.PersonalMessage)){


                }

                //执行常规检测,比如死亡检测
                if(sender.getHealth()<=0){
                    //reply自己增加,实现多条回复的合并
                    reply = reply+Endl+"你已经死了,自动从账户中扣除四分之一钱和四分之一经验";
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

