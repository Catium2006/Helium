package cn.tonyn.helium.data.item.type;

public class 矿石 extends Item{
    public 矿石(){
        可以交易=t;
    }
    public static 矿石 byName(String name){
        return (矿石)Item.byName(name);
    }
}
