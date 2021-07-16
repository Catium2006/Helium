package cn.tonyn.json;
/**
 A ClientInfo json should include the things below.
 */
public class ClientInfo {
    //anything you like
    String ClientName;

    //JVM or Android or C/C++ or Python ....
    String Platform;

    //information of your OS , like "Ubuntu 20.04 desktop" , or anything else you like
    String SystemInfo;

    //when was this message created
    long Timestamp;

    //API which you are using , like QQ or Wechat or anything else
    String API;

    //type of command of this client , we have "internal command" or "message"
    String OperationType;

    //content of command
    String Content;

    //mark of operation , in order to avoid conflicts
    String OperationMark;
}

