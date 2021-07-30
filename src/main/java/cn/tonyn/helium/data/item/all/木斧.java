package cn.tonyn.helium.data.item.all;

import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.data.item.type.装备;

public class 木斧 extends 装备 {
    public 木斧(){
        Code=12;
        Name="木斧";
        信息="一种工具";
        可以交易=t;
        价值=40;
        合成数量=4;
        可以合成=t;
        配方表=new Item[]{Item.byName("木板"),Item.byName("木板"),Item.byName("木板"),Item.byName("木棍"),Item.byName("木棍")};
        燃烧时间=50;
    }
}
