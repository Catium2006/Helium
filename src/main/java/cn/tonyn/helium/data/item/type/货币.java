package cn.tonyn.helium.data.item.type;

import cn.tonyn.helium.client.ClientHandler;
import cn.tonyn.log.Logger;

public class ���� extends Item{
    public float �һ�˰��=0.01f;
    public ����(){
        ���Ժϳ�=t;
        ���Խ���=f;
        ��Ϣ="���ֽ�һ��ɵ�Ӳͨ��,��ʹ����Ҳ������ʧ,���Թ����ϳ�,������Ҫ��˰"+ ClientHandler.Endl;
    }
    public static ���� byName(String name){
        return (����)Item.byName(name);
    }
}
