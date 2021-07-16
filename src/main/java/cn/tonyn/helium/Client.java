package cn.tonyn.helium;

import cn.tonyn.log.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;

public class Client {
    public Socket Client_Socket;

    //informations of client
    public String NAME;
    public String Platform;
    public String SystemInfo;
    public long Timestamp;
    public String API;
    public String OperationType;
    public String Content;
    public int OperationMark;

    public Client(Socket socket){
        Client_Socket = socket;
        try{
            //get informations from socket
            InputStream is = Client_Socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String recieve;
            recieve=br.readLine();
            Logger.log("Recieve from client "+recieve,"Client");

            //get informations from json
            getInfo(recieve);


        }catch (IOException e){
            e.printStackTrace();
            Logger.log(e.getMessage(),"IOException");
        }

    }

    /**
     * get informations from json
     * @param json
     */
    private void getInfo(String json) {
        JSONObject jsonObject = JSON.parseObject(json);
        NAME = jsonObject.getString("ClientName");
        Platform = jsonObject.getString("Platform");
        SystemInfo = jsonObject.getString("SystemInfo");
        Timestamp = jsonObject.getLong("Timestamp");
        API = jsonObject.getString("API");
        OperationType = jsonObject.getString("OperationType");
        Content = jsonObject.getString("Content");
        OperationMark = jsonObject.getInteger("OperationMark");


    }

    /**
     * send json message to client
     * @param json
     * @return
     */
    boolean send(String json){
        Logger.log("Try to sending to "+NAME+" ...","Client");
        try{
            OutputStream os = Client_Socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(json);
            bw.newLine();
            bw.flush();
            bw.close();

            Logger.log("Succeeded in sending to "+NAME+": "+json,"Client");
            return true;
        }catch (IOException e){
            e.printStackTrace();
            Logger.log(e.getMessage(),"IOException");
            Logger.log("Failed in sending to "+NAME);
            return false;
        }
    }

    /**
     * ban this client
     * @param reason
     */
    void ban(String reason){

    }

    /**
     * close connection of client
     */
    void close(){
        try{
            Client_Socket.close();
        }catch (IOException e){
            e.printStackTrace();
            Logger.log(e.getMessage(),"IOException");
        }

    }

}
