package cn.tonyn.helium.data;

import cn.tonyn.helium.data.item.type.Item;

public class Exploring {
    public static Item[] A={i("С��"),i("���"),i("ԭľ"),i("ԭʯ"),i("ľ��"),i("��ĸ"),i("ľ��")};
    public static Item[] B={i("ţ��"),i("ԭľ"),i("ԭʯ"),i("��ĸ")};
    public static Item[] C={i("����ʯ"),i("�ҽ�")};
    static Item i(String name){
        return Item.byName(name);
    }
}
