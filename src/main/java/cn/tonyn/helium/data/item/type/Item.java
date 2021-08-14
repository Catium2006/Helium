package cn.tonyn.helium.data.item.type;

import static cn.tonyn.helium.client.ClientHandler.Endl;

/**
 * ��Ϊ̫�����Ծ�ȫ��������
 */

public class Item {
    public final boolean t = true;
    public final boolean f = false;
    public String Name;
    public int Code = -1;

    public String ʹ��Ч��;
    public String ��Ϣ;
    public int ��ֵ;
    public boolean ���Գ�;
    public boolean ���Խ���;
    public boolean ���Ժϳ�;
    public boolean ����װ��;
    public boolean ���Դ���;
    public Item[] �䷽��;
    public int �ϳ�����=1;
    public int ȼ��ʱ��=-1;
    public int ȼ������=-1;
    public Item �������;
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
        String s = Name+":"+Endl+��Ϣ+Endl+"��ֵ:"+��ֵ+Endl;
        if(���Ժϳ�){
            s=s+"�䷽��:";
            for(Item i : �䷽��){
                s=s+i+",";
            }
            s=s+Endl;
        }
        if(ȼ��ʱ��>0){
            s=s+"�ṩ"+ȼ��ʱ��+"ȼ������"+Endl;
        }
        if(ȼ������>0){
            s=s+"��������"+ȼ������+"����"+Endl;
        }

        return s;
    }

}
