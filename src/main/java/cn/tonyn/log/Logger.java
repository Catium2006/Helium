package cn.tonyn.log;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class Logger {

    public static void log(String event) {

        event="[Info] "+event;


        File log = new File("data/log/Info.log");
        Date date = new Date();
        SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));

        try {
            event=bjSdf.format(date)+"/"+event+System.getProperty("line.separator");
            System.out.print(event);
            FileWriter writer = null;
            writer = new FileWriter(log , true);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(event);
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("Error:"+e.getMessage());
            e.printStackTrace();
        }
    }
    public static void log(String event,String label) {
        event="["+label+"] "+event;


        File log = new File("data/log/"+label+".log");
        Date date = new Date();
        SimpleDateFormat bjSdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        bjSdf.setTimeZone(TimeZone.getTimeZone("Asia/Shanghai"));
        try {
            event=bjSdf.format(date)+"/"+event+System.getProperty("line.separator");
            System.out.print(event);

            FileWriter writer = null;
            writer = new FileWriter(log , true);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(event);
            out.flush();
            out.close();
        } catch (IOException e) {
            System.out.println("Error:"+e.getMessage());
            e.printStackTrace();
        }
    }

}
