package cn.tonyn;


import cn.tonyn.helium.client.HeliumClient;


public class Test {
    static String BadVersion = "{\"ClientName\": \"MyClient\" , \"HeliumVersion\":\"100.99.98\" , \"Platform\": \"JVM\" , \"SystemInfo\": \"Ubuntu Server 20.04\" , \"Timestamp\": \"114514\" , \"API\": \"QQ\" , \"OperationType\":  \"internal command\", \"Content\": \"ping\" , \"OperationMark\": \"1\"}";
    static String BadConnection = "{\"ClienName\": \"MyClient\" , \"Plform\": \"JVM\" , \"SysemInfo\": \"Ubuntu Server 20.04\" , \"Testamp\": \"114514\" , \"API\": \"QQ\" , \"OperationType\":  \"internal command\", \"Content\": \"ping\" , \"OperationMark\": \"1\"}";

    public static void main(String[] args){

        HeliumClient hc = new HeliumClient("Coishi","QQ");
        hc.connect("localhost",10060);
        System.out.println("delay is "+hc.getPings()+"ms");
    }
}
