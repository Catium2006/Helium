package cn.tonyn.helium.data.item.type;

public class 衣服 extends Item{
    public int 增加力量=0;
    public int 增加生命=0;
    public int 增加幸运=0;
    public 衣服(){
        可以交易=t;
        可以合成=t;
        可以装备=t;
        可以穿戴=t;
    }
    public static 衣服 byName(String name){
        return (衣服)Item.byName(name);
    }
}
