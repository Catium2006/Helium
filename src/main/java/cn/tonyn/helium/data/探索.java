package cn.tonyn.helium.data;

import cn.tonyn.helium.data.item.type.Item;

public class ̽�� {
    public static Item[] A={i("С��"),i("���"),i("ԭľ"),i("ԭʯ"),i("ľ��"),i("��ĸ"),i("ľ��")};
    public static Item[] B={i("ţ��"),i("ԭľ"),i("��ĸ")};
    public static Item[] C={i("ԭʯ"),i("̿")};
    static Item i(String name){
        return Item.byName(name);
    }
}
