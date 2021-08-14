package cn.tonyn.helium.data.item.type;

import static cn.tonyn.helium.client.ClientHandler.Endl;

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
    public boolean 可以合成;
    public boolean 可以装备;
    public boolean 可以穿戴;
    public Item[] 配方表;
    public int 合成数量=1;
    public int 燃烧时间=-1;
    public int 燃烧消耗=-1;
    public Item 熔炼结果;
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

    /**
     * just byName() but shorter
     * @param n
     * @return
     */
    public static Item i(String n){
        return byName(n);
    }

    public String getInfo(){
        String s = Name+":"+Endl+信息+Endl+"价值:"+价值+Endl;
        if(可以合成){
            s=s+"配方表:";
            for(Item i : 配方表){
                s=s+i+",";
            }
            s=s+Endl;
        }
        if(燃烧时间>0){
            s=s+"提供"+燃烧时间+"燃烧热量"+Endl;
        }
        if(燃烧消耗>0){
            s=s+"熔炼消耗"+燃烧消耗+"热量"+Endl;
        }

        return s;
    }

}
