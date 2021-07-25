package cn.tonyn.helium.mysql;

import cn.tonyn.helium.Config;

import cn.tonyn.log.Logger;

import java.sql.*;

public class SqlConnection {

    /**
     * ִ��sql��䲢���ؽ��
     * @param sql
     * @return
     */
    public static ResultSet doSql(String sql){
        Connection conn = null;
        Statement stmt = null;
        String dburl="jdbc:mysql://"+Config.MYSQLSERVER+"/helium?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        try{
            // ע�� JDBC ����
            Class.forName("com.mysql.cj.jdbc.Driver");

            // ������
            Logger.log("sending to MySql: "+sql,"MySql");
            conn = DriverManager.getConnection(dburl, Config.MYSQLUSER,Config.MYSQLPASSWORD);

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
        }
    }

    /**
     * ִ��Sql���,��Ҫ�󷵻�,����insert,update�ȷǼ�������
     * @param sql
     * @return
     */
    public static void doSqlNoResult(String sql){
        Connection conn = null;
        Statement stmt = null;
        String dburl="jdbc:mysql://"+Config.MYSQLSERVER+"/helium?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
        try{
            // ע�� JDBC ����
            Class.forName("com.mysql.cj.jdbc.Driver");

            // ������
            Logger.log("sending to MySql without result: "+sql,"MySql");
            conn = DriverManager.getConnection(dburl, Config.MYSQLUSER,Config.MYSQLPASSWORD);

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
        return doSql(sql);
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
