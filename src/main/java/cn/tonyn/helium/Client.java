package cn.tonyn.helium;

import cn.tonyn.log.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;

public class Client {
    public Socket Client_Socket;

    //informations of client
    public String Name;
    public String HeliumVersion;
    public String Platform;
    public String SystemInfo;
    public long Timestamp=0;
    public String API;
    public String OperationType;
    public String Content;
    public int OperationMark=-1;

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
        Name = jsonObject.getString("ClientName");
        HeliumVersion = jsonObject.getString("HeliumVersion");
        Platform = jsonObject.getString("Platform");
        SystemInfo = jsonObject.getString("SystemInfo");
        Timestamp = jsonObject.getLong("Timestamp");
        API = jsonObject.getString("API");
        OperationType = jsonObject.getString("OperationType");
        Content = jsonObject.getString("Content");
        OperationMark = jsonObject.getInteger("OperationMark");


    }

    /**
     * MUST BE RUN AFTER getInfo()
     *
     * check if we got a complete Client
     * if true , everything is right
     * if false , that means we got a Client which has problems , we will disconnect it
     *
     * @return boolean
     */
    boolean checkNull(){
        if(
                Name!=null&&
                HeliumVersion!=null&&
                Platform!=null&&
                SystemInfo!=null&&
                Timestamp!=0&&
                API!=null&&
                OperationType!=null&&
                Content!=null&&
                OperationMark>=0

        ){
            return true;
        }
        return false;
    }

    /**
     * send json message to client
     * @param json
     * @return
     */
    boolean send(String json){
        Logger.log("Try to sending to "+Name+" ...","Client");
        try{
            OutputStream os = Client_Socket.getOutputStream();
            OutputStreamWriter osw = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(osw);
            bw.write(json);
            bw.newLine();
            bw.flush();
            bw.close();

            Logger.log("Succeeded in sending to "+Name+": "+json,"Client");
            return true;
        }catch (IOException e){
            e.printStackTrace();
            Logger.log(e.getMessage(),"IOException");
            Logger.log("Failed in sending to "+Name);
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
