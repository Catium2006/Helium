package cn.tonyn;


import java.io.*;
import java.net.Socket;

public class Test {
    static String BadVersion = "{\"ClientName\": \"MyClient\" , \"HeliumVersion\":\"100.99.98\" , \"Platform\": \"JVM\" , \"SystemInfo\": \"Ubuntu Server 20.04\" , \"Timestamp\": \"114514\" , \"API\": \"QQ\" , \"OperationType\":  \"internal command\", \"Content\": \"ping\" , \"OperationMark\": \"1\"}";
    static String BadConnection = "{\"ClienName\": \"MyClient\" , \"Plform\": \"JVM\" , \"SysemInfo\": \"Ubuntu Server 20.04\" , \"Testamp\": \"114514\" , \"API\": \"QQ\" , \"OperationType\":  \"internal command\", \"Content\": \"ping\" , \"OperationMark\": \"1\"}";

    public static void main(String[] args){
        try{
            String s= BadVersion;
            Socket socket = new Socket("localhost",10060);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()),s.length());
            bw.write(s);
            bw.newLine();
            bw.flush();

            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            info = br.readLine();
            System.out.println("ServerReply: "+info);


            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
