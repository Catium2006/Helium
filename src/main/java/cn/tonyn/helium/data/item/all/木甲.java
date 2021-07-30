package cn.tonyn.helium.data.item.all;

import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.data.item.type.衣服;

public class 木甲 extends 衣服 {
    public 木甲(){
        Code=7;
        Name="木甲";
        价值=150;
        增加生命=30;
        信息="木头做的装甲,增加30生命值";
        使用效果="看起来不是很结实";
        Item i= Item.byName("木板");
        可以合成=t;
        配方表=new Item[]{i,i,i,i,i,i,i,i};
        燃烧时间=50;
    }
}
