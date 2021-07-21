package cn.tonyn.helium;

import cn.tonyn.helium.client.Client;
import cn.tonyn.helium.client.ClientHandler;
import cn.tonyn.log.Logger;
import cn.tonyn.util.TextFile;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Helium {
    public static void main(String[] args){

        //make necessary directories
        makeDirs();

        //start
        startHelium();

    }

    static void startHelium() {
        String config_json = "{\"ServerName\":\"Helium Server\",\"ListeningPort\":\"10060\",\"MaxUsers\":\"102400\",\"OperatingCode\":\"*#*#1234\",\"NumberOfUsers\":\"0\"}";
        File config_json_file =new File("Config.json");
        if(!config_json_file.isFile()){
            TextFile.write(config_json_file,config_json);
        }

        config_json = TextFile.read(config_json_file);
        JSONObject jsonObject = JSON.parseObject(config_json);
        Config.SERVER_NAME=jsonObject.getString("ServerName");
        Config.OPERATING_CODE=jsonObject.getString("OperatingCode");
        Config.PORT=jsonObject.getInteger("ListeningPort");
        Config.MAX_USERS=jsonObject.getInteger("MaxUsers");
        Config.NUMBER_OF_USERS=jsonObject.getInteger("NumberOfUsers");

        //print some information
        System.out.println("=====Helimu=====\r\n" +
                "v " + Config.VERSION + "\r\n" +
                "listening " + Config.PORT + "\r\n" +
                "we have "+Config.NUMBER_OF_USERS+" users" + "\r\n" +
                "server name is "+Config.SERVER_NAME
        );

        try{
            //get socket client
            ServerSocket serverSocket = new ServerSocket(10060);

            while(true){

                //for each connection , we put it into another handler , of course a new thread
                Socket client_socket = serverSocket.accept();
                Logger.log("Accept a client: "+client_socket.getInetAddress());
                new Thread(){
                    @Override
                    public void run(){
                        Client client =new Client(client_socket);
                        new ClientHandler(client);
                    }
                }.start();


            }
        }catch (IOException e){
            e.printStackTrace();
            Logger.log(e.getMessage(),"IOExcepton");
        }

    }
    static void makeDirs(){
        (new File("data/log")).mkdirs();
        (new File("data/users")).mkdirs();
    }

    static void setServerName(String name) {
        Config.SERVER_NAME=name;
    }

    static void setOperatingCode(String code){
        Config.OPERATING_CODE=code;
    }

    static void setMaxUsers(int maxUsers){
        Config.MAX_USERS=maxUsers;
    }



}
