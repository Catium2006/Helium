package cn.tonyn.helium.mysql;

import cn.tonyn.helium.Config;

import cn.tonyn.log.Logger;

import java.sql.*;

public class SqlConnection {
    static Connection conn;
    static Statement stmt;
    static String dburl="jdbc:mysql://"+Config.MYSQLSERVER+"/helium?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    public static void connect(){
        // 注册 JDBC 驱动
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            // 打开链接
            conn = DriverManager.getConnection(dburl, Config.MYSQLUSER,Config.MYSQLPASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }

    }

    /**
     * 执行sql语句并返回结果
     * @param sql
     * @return
     */
    public static ResultSet doSql(String sql){
        try{
            Logger.log("sending to MySql: "+sql,"MySql");
            //执行sql
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            if(rs==null){
                System.out.println("ERROR\r\n");
                return null;
            }else{
                return rs;
            }

        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
            return null;
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
            return null;
        }finally {

        }
    }

    /**
     * 执行Sql语句,不要求返回,用于insert,update等非检索操作
     * @param sql
     * @return
     */
    public static void doSqlNoResult(String sql){

        try{

            Logger.log("sending to MySql: "+sql,"MySql");
            //执行sql
            stmt = conn.createStatement();
            stmt.execute(sql);

        }catch(SQLException se){
            // 处理 JDBC 错误
            se.printStackTrace();
        }catch(Exception e){
            // 处理 Class.forName 错误
            e.printStackTrace();
        }
    }



    /**
     * 获取用户对象从username
     * @param _table
     * @param _username
     * @return
     */
    public static ResultSet getByUsername(String _table,String _username){
        String sql = "select * from " + _table + " where _username = '"+_username+"'";
        return doSql(sql);
    }

    /**
     * 获取用户对象从uid
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
     * 获取表长 仅限于包含_uid字段的表
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
