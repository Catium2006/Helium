package cn.tonyn.helium.data.item.type;

import cn.tonyn.log.Logger;

public class ʳ�� extends Item{
    public int �ָ�����ֵ;
    public static ʳ�� byName(String className){
        try {
            Class _class = Class.forName("cn.tonyn.helium.data.item.all."+className);
            Object object = _class.newInstance();
            return (ʳ��)object;
        } catch (ClassNotFoundException |InstantiationException|IllegalAccessException e) {
            Logger.log(e.getMessage(),"ClassException");
            return null;
        }
    }
    public ʳ��(){
        ���Գ�=t;
        ���Խ���=t;
    }
}
