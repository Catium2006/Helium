package cn.tonyn.helium.data.item.type;

public class 装备 extends Item{
    public int 增加力量;
    public int 增加生命;
    public int 增加幸运;
    public 装备(){
        可以装备=t;
        可以合成=t;
    }
    public static 装备 byName(String name){
        return (装备)Item.byName(name);

    }
}
