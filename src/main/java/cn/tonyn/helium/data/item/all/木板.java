package cn.tonyn.helium.data.item.all;

import cn.tonyn.helium.data.item.type.Item;

public class 木板 extends Item {
    public 木板(){
        Code=10;
        Name="木板";
        信息="木材";
        可以交易=t;
        价值=10;
        合成数量=4;
        可以合成=t;
        配方表=new Item[]{Item.byName("原木")};
        燃烧时间=20;
    }
}
