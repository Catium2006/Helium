package cn.tonyn.helium.data.item.type;


public class Item {
    public String Name;
    public int Code = -1024;
    public String Type;

    public boolean _Tradable;
    public boolean _Eatable;
    public boolean _Wearable;


    public boolean equals(Item item){
        if(item.Code==Code){
            return true;
        }
        return false;
    }

    public boolean is(String type){
        if(type==Type){
            return true;
        }
        return false;
    }

}
