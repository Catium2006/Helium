package cn.tonyn.helium.data.item.type;

import cn.tonyn.log.Logger;

public class 食物 extends Item{
    public int 恢复生命值;
    public static 食物 byName(String className){
        try {
            Class _class = Class.forName("cn.tonyn.helium.data.item.all."+className);
            Object object = _class.newInstance();
            return (食物)object;
        } catch (ClassNotFoundException |InstantiationException|IllegalAccessException e) {
            Logger.log(e.getMessage(),"ClassException");
            return null;
        }
    }
    public 食物(){
        可以吃=t;
        可以交易=t;
    }
}
