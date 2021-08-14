package cn.tonyn.helium.data.item.all;

import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.data.item.type.衣服;

public class 铁甲 extends 衣服 {
    public 铁甲(){
        Code=24;
        Name="铁甲";
        信息="铁做的外壳(?)";
        配方表=new Item[]{
                Item.i("铁锭"),
                Item.i("铁锭"),
                Item.i("铁锭"),
                Item.i("铁锭"),
                Item.i("铁锭"),
                Item.i("铁锭"),
                Item.i("铁锭"),
                Item.i("铁锭")
        };
        增加生命=50;
        价值=new 铁锭().价值*8+20;
    }
}
