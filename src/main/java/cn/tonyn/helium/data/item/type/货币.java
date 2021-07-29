package cn.tonyn.helium.data.item.type;

import cn.tonyn.helium.client.ClientHandler;
import cn.tonyn.log.Logger;

public class 货币 extends Item{
    public float 兑换税率=0.01f;
    public 货币(){
        可以合成=t;
        信息="用现金兑换成的硬通货,即使死亡也不会消失,可以购买或合成,购买需要交税"+ ClientHandler.Endl;
    }
    public static 货币 byName(String className){
        try {
            Class _class = Class.forName("cn.tonyn.helium.data.item.all."+className);
            Object object = _class.newInstance();
            return (货币)object;
        } catch (ClassNotFoundException |InstantiationException|IllegalAccessException e) {
            Logger.log(e.getMessage(),"ClassException");
            return null;
        }
    }
}
