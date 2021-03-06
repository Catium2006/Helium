package cn.tonyn.helium;

import cn.tonyn.util.TextFile;

import java.io.File;

/*
    This class defines configs of Helium, you can edit all these below.

    */
public class Config {

    //Listening socket port , default is 10060.
    public static int PORT = 10060;

    //Helium version , must be "[Num].[Num].[Num](-[String])"
    public static final String VERSION = "0.1.0";

    //Server name , DO NOT USE "Helium Main Server"
    public static String SERVER_NAME = "Helium Main Server";

    //MAX Number of users , default is 102400.


    //Default operator passcode , in order to do some setting on Helium , as you are the administrator.
    public static String OPERATING_CODE = "*#*#1234";

    public static final String PLATFORM = "JVM";



    public static String MYSQLSERVER = "localhost:3306";

    public static String MYSQLUSER = "helium";

    public static String MYSQLPASSWORD = "";

    /**
     * write config to file
     */
    static void writeConfig(){
        String json="{\"ServerName\":\"" + Config.SERVER_NAME+"\"," +
                "\"ListeningPort\":\"" + Config.PORT + "\"," +
                "\"OperatingCode\":\"" + Config.OPERATING_CODE + "\"}";
        File f = new File("Config.json");
        f.delete();
        TextFile.write(f,json);
    }


}
