package cn.tonyn.helium.data.item.all;


import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.data.item.type.矿石;

public class 铁矿石 extends 矿石 {
    public 铁矿石(){
        Code=13;
        Name="铁矿石";
        信息="富含Fe的矿石";
        价值=100;
        燃烧消耗=100;
        熔炼结果= Item.byName("铁锭");
    }
}
