package cn.tonyn.helium.data.item.all;

import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.data.item.type.食物;

public class 啤酒 extends 食物 {
    public 啤酒(){
        Code = 2;
        Name="啤酒";
        信息="新鲜的啤酒";
        可以交易=f;
        使用效果="";
        恢复生命值=12;
        价值=60;
        Item i0=Item.byName("小麦");
        Item i1=Item.byName("酵母");
        可以合成=t;
        配方表=new Item[]{i0,i0,i0,i1};

    }
}
