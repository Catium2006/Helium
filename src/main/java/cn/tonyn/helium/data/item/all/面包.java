package cn.tonyn.helium.data.item.all;

import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.data.item.type.食物;

public class 面包 extends 食物 {
    public 面包(){
        使用效果 = "有点硬";
        信息 = "小麦做的面包";
        Code = 1;
        Name = "面包";
        价值=35;
        恢复生命值=5;
        可以吃 = t;
        可以合成 = t;
        小麦 i = new 小麦();
        配方表 = new Item[]{i, i, i};
    }
}
