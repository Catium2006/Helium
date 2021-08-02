package cn.tonyn.helium.data.item.all;

import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.data.item.type.装备;

public class 石镐 extends 装备 {
    public 石镐(){
        Code=21;
        Name="石镐";
        信息="采矿工具";
        类型="采矿";
        增加力量=10;
        增加幸运=10;
        价值=80;
        配方表=new Item[]{Item.i("原石"),Item.i("原石"),Item.i("原石"),Item.i("木棍"),Item.i("木棍")};
    }
}
