package cn.tonyn.helium;
    /*
    This class defines configs of Helium, you can edit all these below.

    */
public class Config {

    //Listening socket port , default is 10060.
    public static final int PORT = 10060;

    //Helium version , must be "[Num].[Num].[Num](-[String])"
    public static final String VERSION = "1.0.0";

    //Server name , DO NOT USE "Helium Main Server"
    public static String SERVER_NAME = "Helium Main Server";

    //MAX Number of users , default is 102400.
    public static final int MAX_USERS = 102400;

    //Default operator passcode , in order to do some setting on Helium , as you are the administrator.
    public static String OPERATING_CODE = "#*#*1234";

    public static String PLATFORM = "JVM";


}
