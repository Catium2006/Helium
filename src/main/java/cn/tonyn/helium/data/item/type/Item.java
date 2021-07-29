package cn.tonyn.helium.data.item.type;

/**
 * 因为太懒所以就全点中文了
 */

public class Item {
    public final boolean t = true;
    public final boolean f = false;
    public String Name;
    public int Code = -1;

    public String 使用效果;
    public String 信息;
    public int 价值;
    public boolean 可以吃;
    public boolean 可以交易;
    public boolean 可以武装;
    public boolean 可以防御;
    public boolean 可以合成;
    public Item[] 配方表;

    public boolean equals(Item item){
        if(item.Code==Code){
            return true;
        }
        return false;
    }


    @Override
    public String toString(){
        return Name;
    }

    public static Item byName(String className){
        try {
            Class _class = Class.forName("cn.tonyn.helium.data.item.all."+className);
            Object object = _class.newInstance();
            return (Item)object;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }


}
