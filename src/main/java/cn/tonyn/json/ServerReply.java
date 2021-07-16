package cn.tonyn.json;

import com.alibaba.fastjson.JSONObject;

/**
 *a ServerReply json should include the things below.
 */
public class ServerReply {
    //name of your server
    String ServerName;

    //version of Helium which server is using , must be "[Num].[Num].[Num](-[String])"
    String HeliumVersion;

    //default is JVM
    String Platform;

    //timestamp while building this message
    String Timestamp;

    //if we allowed the command that client send to us
    Boolean Allowed;

    //OperationMark that client send to us
    int OperationOf;

    //type of operation
    String OperationType;

    //content of message
    String Content;


}
