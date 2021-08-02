package cn.tonyn.helium.data.item.all;


import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.data.item.type.矿石;

public class 原石 extends 矿石 {
    public 原石(){
        Code=14;
        Name="原石";
        信息="石头";
        价值=20;
        燃烧消耗=50;
        熔炼结果= Item.i("石块");
    }
}
