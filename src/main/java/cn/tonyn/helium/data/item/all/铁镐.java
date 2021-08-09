package cn.tonyn.helium.data.item.all;

import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.data.item.type.装备;

public class 铁镐 extends 装备 {
    public 铁镐(){
        Code=22;
        Name="铁镐";
        可以合成=t;
        可以交易=f;
        价值=400;
        增加力量=15;
        增加幸运=15;
        配方表=new Item[]{
                Item.byName("铁锭"),
                Item.byName("铁锭"),
                Item.byName("铁锭"),
                Item.byName("木棍"),
                Item.byName("木棍")
        };
    }
}
