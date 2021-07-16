package cn.tonyn.helium;

import cn.tonyn.log.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;

public class Client {
    public Socket Client_Socket;

    //informations of client
    /**
     * @see cn.tonyn.json.ClientInfo
     */
    public String Name;
    /**
     * @see cn.tonyn.json.ClientInfo
     */
    public String HeliumVersion;
    /**
     * @see cn.tonyn.json.ClientInfo
     */
    public String Platform;
    /**
     * @see cn.tonyn.json.ClientInfo
     */
    public String SystemInfo;
    /**
     * @see cn.tonyn.json.ClientInfo
     */
    public long Timestamp=0;
    /**
     * @see cn.tonyn.json.ClientInfo
     */
    public String API;
    /**
     * @see cn.tonyn.json.ClientInfo
     */
    public String OperationType;
    /**
     * @see cn.tonyn.json.ClientInfo
     */
    public String Content;
    /**
     * @see cn.tonyn.json.ClientInfo
     */
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

            checkNull();

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

        close();
        return false;
    }

    /**
     * MUST BE RUN AFTER getInfo()
     *
     * check if the Helium that client is using is fit to server
     * @return boolean
     */
    boolean checkVersion(){
        String[] clientVersion = HeliumVersion.split(".");
        String[] serverVersion = Config.VERSION.split(".");
        int major_c = Integer.valueOf(clientVersion[0]);
        int minor_c = Integer.valueOf(clientVersion[1]);
        int major_s = Integer.valueOf(serverVersion[0]);
        int minor_s = Integer.valueOf(serverVersion[1]);

        //we only need to check the major and Minor
        if(major_c==major_s&&minor_c<=major_s){
            return true;
        }
        return false;
    }

    /**
     * MUST BE RUN AFTER getInfo()
     *
     * send json message to client (you can also send anything else , but if you do so , client may run into crash)
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
     * MUST BE RUN AFTER getInfo()
     *
     * ban this client , need a reason , anything you like
     * @param reason
     */
    void ban(String reason){

    }

    /**
     * MUST BE RUN AFTER getInfo()
     *
     * close connection of client
     */
    void close(){
        try{
            Client_Socket.close();
        }catch (IOException e){
            Logger.log("Exception while disconnecting client:"+e.getMessage(),"IOException");
        }
    }

}
