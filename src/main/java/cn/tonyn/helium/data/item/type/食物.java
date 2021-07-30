package cn.tonyn.helium.data.item.type;

import cn.tonyn.log.Logger;

public class 食物 extends
        Item{
    public int 恢复生命值;
    public static 食物 byName(String name){
        return (食物)Item.byName(name);
    }
    public 食物(){
        可以吃=t;
        可以交易=t;
    }
}
