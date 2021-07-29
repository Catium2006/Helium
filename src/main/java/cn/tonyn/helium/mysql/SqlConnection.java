package cn.tonyn.helium.mysql;

import cn.tonyn.helium.Config;

import cn.tonyn.log.Logger;

import java.sql.*;

public class SqlConnection {
    static Connection conn;
    static Statement stmt;
    static String dburl="jdbc:mysql://"+Config.MYSQLSERVER+"/helium?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static void connect(){
        // ע�� JDBC ����
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // ������
            conn = DriverManager.getConnection(dburl, Config.MYSQLUSER,Config.MYSQLPASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * ִ��sql��䲢���ؽ��
     * @param sql
     * @return
     */
    public static ResultSet doSql(String sql){
        try{
            Logger.log("sending to MySql: "+sql,"MySql");
            //ִ��sql
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs==null){
                System.out.println("ERROR\r\n");
                return null;
            }else{
                return rs;
            }

        }catch(SQLException se){
            // ���� JDBC ����
            se.printStackTrace();
            return null;
        }catch(Exception e){
            // ���� Class.forName ����
            e.printStackTrace();
            return null;
        }finally {

        }
    }

    /**
     * ִ��Sql���,��Ҫ�󷵻�,����insert,update�ȷǼ�������
     * @param sql
     * @return
     */
    public static void doSqlNoResult(String sql){

        try{

            Logger.log("sending to MySql: "+sql,"MySql");
            //ִ��sql
            stmt = conn.createStatement();
            stmt.execute(sql);

        }catch(SQLException se){
            // ���� JDBC ����
            se.printStackTrace();
        }catch(Exception e){
            // ���� Class.forName ����
            e.printStackTrace();
        }
    }



    /**
     * ��ȡ�û������username
     * @param _table
     * @param _username
     * @return
     */
    public static ResultSet getByUsername(String _table,String _username){
        String sql = "select * from " + _table + " where _username = '"+_username+"'";
        return doSql(sql);
    }

    /**
     * ��ȡ�û������uid
     * @param _table
     * @param _uid
     * @return
     */
    public static ResultSet getByUid(String _table,int _uid){
        String sql = "select * from " + _table + " where _uid = "+_uid;
        ResultSet rs = doSql(sql);
        return rs;

    }

    /**
     * ��ȡ�� �����ڰ���_uid�ֶεı�
     * @param _table
     * @return
     */
    public static int getLengthOf(String _table){
        String sql = "select ifnull(max(_uid),0)+1 from "+_table;
        try {
            ResultSet rs = doSql(sql);
            while (rs.next()){
                return rs.getInt("ifnull(max(_uid),0)+1");
            }
            return -1;
        } catch (SQLException e) {
            Logger.log(e.getMessage(),"SQLException");
            return -1;
        }
    }

}
