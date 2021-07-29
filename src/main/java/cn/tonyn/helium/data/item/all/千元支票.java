package cn.tonyn.helium.data.item.all;

import cn.tonyn.helium.data.item.type.货币;

public class 千元支票 extends 货币 {

    public 千元支票(){
        兑换税率=0.02f;
        Code = 6;
        Name = "千元支票";
        信息 = 信息+"价值一千元";
        价值 = 1000;
    }
}
