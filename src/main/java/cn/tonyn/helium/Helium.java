package cn.tonyn.helium;

import cn.tonyn.log.Logger;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Helium {
    public static void main(String[] args){
        //print some information
        System.out.println("=====Helimu=====\r\n" +
                "v " + Config.VERSION + "\r\n" +
                "listening " + Config.PORT
        );

        //make necessary directories
        makeDirs();

        //start
        startHelium();

    }

    static void startHelium() {
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
        (new File("data/user")).mkdirs();

    }

    static void setServerName(String name){
        Config.SERVER_NAME=name;
    }
    static void setOperatingCode(String code){
        Config.OPERATING_CODE=code;
    }

}
