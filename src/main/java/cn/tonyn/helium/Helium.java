package cn.tonyn.helium;

import cn.tonyn.helium.client.Client;
import cn.tonyn.helium.client.ClientHandler;
import cn.tonyn.helium.mysql.SqlConnection;
import cn.tonyn.helium.user.User;
import cn.tonyn.log.Logger;
import cn.tonyn.util.TextFile;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Helium {
    /**
     * 入口
     * @param args
     */
    public static void main(String[] args){

        //make necessary directories
        makeDirs();

        //start
        startHelium();

    }

    /**
     * 真正开始执行
     */
    static void startHelium() {
        String config_json = "{\"ServerName\":\"Helium Server\",\"ListeningPort\":\"10060\",\"OperatingCode\":\"*#*#1234\",\"MySqlServer\":\"localhost:3306\",\"MySqlUser\":\"helium\",\"MySqlPassword\":\"password\"}";
        File config_json_file =new File("Config.json");
        if(!config_json_file.isFile()){
            TextFile.write(config_json_file,config_json);
        }

        config_json = TextFile.read(config_json_file);
        JSONObject jsonObject = JSON.parseObject(config_json);
        Config.SERVER_NAME=jsonObject.getString("ServerName");
        Config.OPERATING_CODE=jsonObject.getString("OperatingCode");
        Config.PORT=jsonObject.getInteger("ListeningPort");

        Config.MYSQLSERVER=jsonObject.getString("MySqlServer");
        Config.MYSQLUSER=jsonObject.getString("MySqlUser");
        Config.MYSQLPASSWORD=jsonObject.getString("MySqlPassword");

        Logger.log("connecting to mysql server...");
        //print some information
        System.out.println("=====Helimu=====\r\n" +
                "v " + Config.VERSION + "\r\n" +
                "listening " + Config.PORT + "\r\n" +
                "we have "+SqlConnection.getLengthOf("_user")+" users" + "\r\n" +
                "server name is "+Config.SERVER_NAME + "\r\n" +
                "using MySql: "+Config.MYSQLSERVER
        );

        test();



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

    /**
     * 建目录
     */
    static void makeDirs(){
        (new File("data/log")).mkdirs();
        (new File("data/users")).mkdirs();
    }


    /**
     * 修改操作密码
     * @param code
     */
    static void setOperatingCode(String code){
        Config.OPERATING_CODE=code;
    }

    static void test(){
        for(int i=0;i<=4;i++){
            User.createNewUser("test"+i);
        }

    }




}
