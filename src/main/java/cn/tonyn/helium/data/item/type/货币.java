package cn.tonyn.helium.data.item.type;

import cn.tonyn.helium.client.ClientHandler;
import cn.tonyn.log.Logger;

public class ���� extends Item{
    public float �һ�˰��=0.01f;
    public ����(){
        ���Ժϳ�=t;
        ��Ϣ="���ֽ�һ��ɵ�Ӳͨ��,��ʹ����Ҳ������ʧ,���Թ����ϳ�,������Ҫ��˰"+ ClientHandler.Endl;
    }
    public static ���� byName(String className){
        try {
            Class _class = Class.forName("cn.tonyn.helium.data.item.all."+className);
            Object object = _class.newInstance();
            return (����)object;
        } catch (ClassNotFoundException |InstantiationException|IllegalAccessException e) {
            Logger.log(e.getMessage(),"ClassException");
            return null;
        }
    }
}
