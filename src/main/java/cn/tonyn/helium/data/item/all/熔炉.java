package cn.tonyn.helium.data.item.all;

import cn.tonyn.helium.data.item.type.Item;

public class 熔炉 extends Item {
    public 熔炉(){
        Code=15;
        Name="熔炉";
        可以合成=t;
        可以交易=t;
        价值=165;
        配方表=new Item[]{i("原石"),i("原石"),i("原石"),i("原石"),i("原石"),i("原石"),i("原石"),i("原石")};
    }
}
