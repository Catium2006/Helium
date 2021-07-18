package cn.tonyn.helium;

import cn.tonyn.helium.OperationType.InternalCommand;
import cn.tonyn.helium.OperationType.Operations;
import cn.tonyn.helium.json.ClientInfo;
import cn.tonyn.helium.json.ServerReply;
import cn.tonyn.log.Logger;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.net.Socket;
import java.util.Date;

public class Client {
    public Socket Client_Socket;
    //informations of client
    /**
     * @see ClientInfo
     */
    public String Name;
    /**
     * @see ClientInfo
     */
    public String HeliumVersion;
    /**
     * @see ClientInfo
     */
    public String Platform;
    /**
     * @see ClientInfo
     */
    public String SystemInfo;
    /**
     * @see ClientInfo
     */
    public long Timestamp=0;
    /**
     * @see ClientInfo
     */
    public String API;
    /**
     * @see ClientInfo
     */
    public String OperationType;
    /**
     * @see ClientInfo
     */
    public String Content;
    /**
     * default is -1
     * @see ClientInfo
     */
    public int OperationMark=-1;
    /**
     * default is -1
     */
    public long Delay = -1;

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
            checkVersion();

        }catch (IOException e){
            e.printStackTrace();
            Logger.log(e.getMessage(),"IOException");
        }

    }

    /**
     * get information from json
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

        Date d = new Date();
        Delay = d.getTime() - Timestamp;

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
        send(buildReply(false, Operations.InternalCommand, InternalCommand.BadConnection));
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
        String[] clientVersion = HeliumVersion.split("\\.");
        String[] serverVersion = Config.VERSION.split("\\.");
        int major_c = Integer.valueOf(clientVersion[0]);
        int minor_c = Integer.valueOf(clientVersion[1]);
        int major_s = Integer.valueOf(serverVersion[0]);
        int minor_s = Integer.valueOf(serverVersion[1]);

        //we only need to check the major and Minor
        if(major_c==major_s&&minor_c<=minor_s){
            Logger.log("Check Version of "+Name+" :OK","Client");
            return true;
        }

        Logger.log("Check Version of "+Name+" :Not OK","Client");
        //send message to client
        String s=buildReply(false,Operations.InternalCommand,InternalCommand.BadVersion);
        send(s);
        return false;
    }

    /**
     * build a ServerReply.json
     * @see ServerReply
     * @param allowed
     * @param operationType
     * @param content
     * @return String
     */
    String buildReply(boolean allowed,String operationType,String content){
        long t = (new Date()).getTime();
        String s="{\"ServerName\":\"" + Config.SERVER_NAME + "\","+
                "\"HeliumVersion\":\"" + Config.VERSION + "\","+
                "\"Platform\":\"" + Config.PLATFORM + "\","+
                "\"Timestamp\":\"" + t + "\","+
                "\"Allowed\":\"" + allowed +"\","+
                "\"OperationOf\":\"" + OperationMark + "\","+
                "\"OperationType\":\"" + operationType + "\","+
                "\"Content\":\"" + content +"\"}";
        return s;
    }

    /**
     * MUST BE RUN AFTER getInfo()
     *
     * send json message to client (you can also send anything else , but if you do so , client may run into crash)
     * @param json
     * @return
     */
    boolean send(String json){
        Logger.log("Try to send to "+Name+" ...","Client");
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

    InputStream getInPutStream() throws IOException{
        return Client_Socket.getInputStream();
    }

    OutputStream getOutPutStream() throws IOException{
        return Client_Socket.getOutputStream();
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
