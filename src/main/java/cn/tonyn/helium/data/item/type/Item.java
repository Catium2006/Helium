package cn.tonyn.helium.data.item.type;

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
    public boolean ������װ;
    public boolean ���Է���;
    public boolean ���Ժϳ�;
    public Item[] �䷽��;

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
