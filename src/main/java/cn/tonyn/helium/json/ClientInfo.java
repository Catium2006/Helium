package cn.tonyn.helium.json;


import cn.tonyn.helium.client.Client;

/**
 *a ClientInfo json should include the things below.
 * @see Client
 *you can only get information from Client()
 * 没有什么用的类 仅用于说明
 *
 * 草 本来想写全英文的
 * 放弃了
 *
 */
public class ClientInfo {

    /**
     * anything you like
     */
    String ClientName;

    /**
     * version of Helium , must be "[Num].[Num].[Num](-[String])"
     */
    String HeliumVersion;

    /**
     * JVM or Android or C/C++ or Python ....
     */
    String Platform;

    /**
     * information of your OS , like "Ubuntu 20.04 desktop" , or anything else you like
     */
    String SystemInfo;

    /**
     * when was this message created
     */
    long Timestamp;

    /**
     * API which you are using , like QQ or Wechat or anything else
     */
    String API;

    /**
     * type of operation
     * @see cn.tonyn.helium.operation.Operations
     */
    String OperationType;

    /**
     * content of command
     */
    String Content;

    /**
     * mark of operation , in order to avoid conflicts
     * must 0 or above
     */
    String OperationMark;

}

