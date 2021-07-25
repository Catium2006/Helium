package cn.tonyn.heliumclient;

import cn.tonyn.helium.operation.Message;

public class EventBuilder {
    public static String buildMessageEvent(int uid,String message){
        String s= Message.GroupMessage+uid+"S_L_I_P_T"+message;
        return s;
    }

}
