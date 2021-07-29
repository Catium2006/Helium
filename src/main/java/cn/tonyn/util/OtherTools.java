package cn.tonyn.util;

import java.util.Random;

public class OtherTools {
    public static boolean randomBoolean(int howmuch, int all){
        howmuch *=10;
        all *=10;
        int i = new Random().nextInt(all);
        if(i<=howmuch)return true;
        return false;
    }

}
