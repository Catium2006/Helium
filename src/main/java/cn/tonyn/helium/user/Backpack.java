package cn.tonyn.helium.user;

import cn.tonyn.helium.data.item.type.Item;

import java.util.ArrayList;

public class Backpack {

    ArrayList<Item> Contents;
    ArrayList<Integer> NumberOfItem;

    public Backpack(User user){
        user.getUid();
    }

    public void addItem(Item item,int number){
        int location = locateItem(item);
        if(location!=-1){
            int tmp = NumberOfItem.get(location)+number;
            NumberOfItem.set(location,tmp);
        }else {
            Contents.add(item);
            NumberOfItem.add(number);
        }
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

    public void sync(){
        
    }
    public void flush(){

    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        int count=-1;
        for(Item item:Contents){
            count++;
            sb.append(item+"*"+ NumberOfItem.get(count)+",");
        }
        return sb.toString();
    }
}
