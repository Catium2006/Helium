package cn.tonyn.heliumclient;

import cn.tonyn.helium.Config;
import cn.tonyn.helium.OperationType.InternalCommand;
import cn.tonyn.helium.OperationType.Operations;
import cn.tonyn.log.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import oshi.SystemInfo;

import java.io.*;
import java.net.Socket;
import java.util.Date;
import java.util.Random;


public class HeliumClient {
    public String Name;
    public String HeliumServerAddress;
    public String HeliumServerName;
    public int Port;
    public Socket HeliumServerSocket;

    public boolean Connected;

    public static final String Version = Config.VERSION;

    String OS;
    String API;

    /**
     * create a HeliumClient
     * need a name
     *
     * @param name
     */
    public HeliumClient(String name, String api) {
        Name=name;
        SystemInfo systemInfo = new SystemInfo();
        OS = systemInfo.getOperatingSystem().toString();
        API=api;
    }

    /**
     * connect to server
     *
     * @param address
     * @param port
     */
    public void connect(String address , int port){
        Port = port;
        HeliumServerAddress = address;
        try {
            HeliumServerSocket = new Socket(address, port);
            Connected=true;
        }catch (IOException e){
            Connected=false;
        }
    }

    /**
     * get delay from client to server
     *
     * @return
     */
    public long getPings(){
        String json = buildRequest(Operations.InternalCommand, InternalCommand.Ping);
        String _json = sendAndReceive(json);
        JSONObject jsonObject0 = JSON.parseObject(json);
        JSONObject jsonObject1 = JSON.parseObject(_json);
        long delay = jsonObject1.getLong("Timestamp") - jsonObject0.getLong("Timestamp");
        HeliumServerName = jsonObject1.getString("ServerName");
        return delay;
    }

    /**
     * build a json message
     *
     * @param operationType
     * @param content
     * @return
     */
    public String buildRequest(String operationType,String content){
        int mark = new Random().nextInt(256);
        long t = (new Date()).getTime();
        String s="{\"ClientName\":\"" + Name + "\","+
                "\"HeliumVersion\":\"" + Config.VERSION + "\","+
                "\"Platform\":\"" + "JVM" + "\","+
                "\"SystemInfo\":\"" + OS +"\","+
                "\"Timestamp\":\"" + t + "\","+
                "\"API\":\"" + API + "\","+
                "\"OperationType\":\"" + operationType + "\","+
                "\"Content\":\"" + content +"\""+
                "\"OperationMark\":\"" + mark +"\"}";
        System.out.println("\r\n"+s+"\r\n");
        return s;
    }

    /**
     * send message to server and receive something(usually json format)
     * @param json
     * @return
     */
    public String sendAndReceive(String json){
        Logger.log("sending to server: "+json+"\r\n");
        try{
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(HeliumServerSocket.getOutputStream()),json.length());
            bw.write(json);
            bw.newLine();
            bw.flush();

            InputStream is = HeliumServerSocket.getInputStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String _json = br.readLine();
            Logger.log("receive from server: " + _json+"\r\n");
            return _json;
        }catch (IOException e){
            return null;
        }
    }


}
