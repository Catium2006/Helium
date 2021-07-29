package cn.tonyn.helium.user;

import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.mysql.SqlConnection;
import cn.tonyn.log.Logger;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Backpack {
    //在数据库中的存储格式为
    //[Row 0] [,面包,火把,煤炭]
    //[Row 1] [,7,29,4]
    //前面都有逗号
    ArrayList<Item> Contents = new ArrayList<Item>();
    ArrayList<Integer> NumberOfItems = new ArrayList<Integer>();
    int Uid;

    public Backpack(User user){
        Uid = user.getUid();
        flush();
    }

    public static Backpack createBackpack(User user){
        String sql = "insert into _backpack(_uid,_item,_count) values("+user.getUid()+",',面包',',1')";
        SqlConnection.doSqlNoResult(sql);
        Backpack backpack = new Backpack(user);
        backpack.flush();
        return backpack;

    }

    public void addItem(Item item,int number){
        int location = locateItem(item);
        if(location!=-1){
            int tmp = NumberOfItems.get(location)+number;
            NumberOfItems.set(location,tmp);
        }else {
            Contents.add(item);
            NumberOfItems.add(number);
        }
    }

    public int getNumberOf(Item item){
        if(contains(item)){
            return NumberOfItems.get(locateItem(item));
        }
        return 0;
    }

    public boolean contains(Item item){
        int l=locateItem(item);
        if(l!=-1){
            return item.t;
        }return item.f;
    }

    int locateItem(Item item){
        int count=-1;
        for(Item i:Contents){
            count++;
            if(i.equals(item)){
                return count;
            }
        }
        return -1;
    }

    public void update(){
        StringBuilder sb0 = new StringBuilder();
        StringBuilder sb1 = new StringBuilder();
        int i =0;
        for(Item item:Contents){
            sb0.append(","+item);
            sb1.append(","+ NumberOfItems.get(i));
            i++;
        }
        String msg0=sb0.toString();
        String msg1=sb1.toString();
        String sql = "update _backpack set _item = '"+msg0+"',_count = '"+msg1+"' where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    public void flush(){
        ResultSet rs = SqlConnection.getByUid("_backpack",Uid);
        String _items="";
        String _numbers="";
        try{
            while (rs.next()){
                _items=rs.getString("_item");
                _numbers=rs.getString("_count");
            }
        }catch (SQLException e){
            Logger.log(e.getMessage());
        }

        String[] items = _items.split(",");
        String[] numbers = _numbers.split(",");
        for(int i=0;i<items.length-1;){
            i++;
            Contents.add(Item.byName(items[i]));
            NumberOfItems.add(Integer.valueOf(numbers[i]));
        }


    }

    @Override
    public String toString(){
        if(Contents.isEmpty()){
            System.out.println("EMPTY!");
        }
        StringBuilder sb = new StringBuilder();
        int count=-1;
        for(Item item:Contents){
            count++;
            sb.append(item+"*"+ NumberOfItems.get(count)+",");
        }
        return sb.toString();
    }
}
