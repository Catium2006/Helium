package cn.tonyn;


import java.io.*;
import java.net.Socket;

public class Test {
    public static void main(String[] args){
        try{
            String s="{\"ClientName\": \"MyClient\" , \"Platform\": \"JVM\" , \"SystemInfo\": \"Ubuntu Server 20.04\" , \"Timestamp\": \"114514\" , \"API\": \"QQ\" , \"Operation\":  \"ping\"}";
            Socket socket = new Socket("localhost",10060);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()),s.length());
            bw.write(s);
            bw.newLine();
            bw.flush();

            try{
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
            InputStream is = socket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String info = null;
            info = br.readLine();
            System.out.println("我是客户端,服务器返回信息:"+info);

            bw.close();
        }catch (IOException e){
            e.printStackTrace();
        }


    }
}
