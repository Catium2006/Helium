package cn.tonyn.helium.data.item.all;

import cn.tonyn.helium.data.item.type.Item;

public class 木棍 extends Item {
    public 木棍(){
        Code=11;
        Name="木棍";
        信息="木棍能有什么信息?";
        可以交易=t;
        价值=5;
        合成数量=4;
        可以合成=t;
        配方表=new Item[]{Item.byName("木板"),Item.byName("木板")};
        燃烧时间=10;
    }
}
