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
    public String Operation;

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
     * @param str
     */
    private void getInfo(String str) {
        JSONObject jsonObject = JSON.parseObject(str);
        NAME = jsonObject.getString("ClientName");
        Platform = jsonObject.getString("Platform");
        SystemInfo = jsonObject.getString("SystemInfo");
        Timestamp = jsonObject.getLong("Timestamp");
        API = jsonObject.getString("API");
        Operation = jsonObject.getString("Operation");

    }

    /**
     * send json message to client
     * @param json
     * @return
     */
    boolean send(String json){
        Logger.log("Sending to "+NAME+" ...","Client");
        try{
            OutputStream os = Client_Socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(json);
            bw.newLine();
            bw.flush();
            bw.close();

            Logger.log("Succeeded","Client");
            return true;
        }catch (IOException e){
            e.printStackTrace();
            Logger.log(e.getMessage(),"IOException");
            Logger.log("Failed");
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
