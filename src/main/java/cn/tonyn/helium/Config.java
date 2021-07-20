package cn.tonyn.helium;
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
    public static int MAX_USERS = 102400;

    //Default operator passcode , in order to do some setting on Helium , as you are the administrator.
    public static String OPERATING_CODE = "*#*#1234";

    public static final String PLATFORM = "JVM";

    public static int NUMBER_OF_USERS = -1;


}
