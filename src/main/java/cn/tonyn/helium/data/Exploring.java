package cn.tonyn.helium.data;

import cn.tonyn.helium.data.item.type.Item;

public class Exploring {
    public static Item[] A={i("小麦"),i("面包"),i("原木"),i("原石"),i("木板"),i("酵母"),i("木棍")};
    public static Item[] B={i("牛子"),i("原木"),i("原石"),i("酵母")};
    public static Item[] C={i("铁矿石"),i("岩浆")};
    static Item i(String name){
        return Item.byName(name);
    }
}
