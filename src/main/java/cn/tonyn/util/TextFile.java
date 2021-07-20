package cn.tonyn.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import cn.tonyn.log.Logger;

public class TextFile {
    public static void write(String file,String str) {
        File textfile=new File(file);
        FileWriter writer = null;
        try {
            writer = new FileWriter(textfile , true);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(str);
            out.flush();
            out.close();
        } catch (IOException e) {
            Logger.log("IOException:"+e.getMessage(),"IOException");
        }
    }

    public static void write(File file, String str) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(file , true);
            BufferedWriter out = new BufferedWriter(writer);
            out.write(str);
            out.flush();
            out.close();
        } catch (IOException e) {
            Logger.log("IOException:"+e.getMessage(),"IOException");
            e.printStackTrace();
        }
    }

    public static String read(String file) {
        File textfile=new File(file);
        FileReader reader = null;
        try {
            reader = new FileReader(textfile);
            BufferedReader in = new BufferedReader(reader);
            String s=in.readLine();
            in.close();
            return s;

        } catch (IOException e) {
            Logger.log("IOException:"+e.getMessage(),"IOException");
            e.printStackTrace();
            return "";
        }
    }

    public static String read(File file) {
        FileReader reader = null;
        try {
            reader = new FileReader(file);
            BufferedReader in = new BufferedReader(reader);
            String s=in.readLine();
            in.close();
            return s;

        } catch (IOException e) {
            Logger.log("IOException:"+e.getMessage(),"IOException");
            e.printStackTrace();
            return "";
        }

    }

}
