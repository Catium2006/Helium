package cn.tonyn.helium.user;

import cn.tonyn.helium.Config;
import cn.tonyn.helium.exceptions.WrongPasswordException;
import cn.tonyn.helium.mysql.SqlConnection;
import cn.tonyn.log.Logger;


import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    long Uid;
    String Username;
    String Password;
    boolean banned;
    int Experience;
    int Health;
    int Force;

    public static User byUid(int uid){
        User user = new User();
        ResultSet rs = SqlConnection.getByUid("_user",uid);
        try{
            while(rs.next()){
                user.Uid = rs.getInt("_uid");
                user.Username = rs.getString("_username");
                user.Password = rs.getString("_password");
                user.banned = rs.getBoolean("_banned");
                user.Experience = rs.getInt("_experience");
                user.Health = rs.getInt("_health");
                user.Force = rs.getInt("_force");
            }
        }catch (SQLException e){
            Logger.log(e.getMessage(),"SQLException");
            user=null;
        }
        return user;
    }

    public static User byUsername(String username){
        User user = new User();
        ResultSet rs = SqlConnection.getByUsername("_user",username);
        try{
            while(rs.next()){
                user.Uid = rs.getInt("_uid");
                user.Username = rs.getString("_username");
                user.Password = rs.getString("_password");
                user.banned = rs.getBoolean("_banned");
                user.Experience = rs.getInt("_experience");
                user.Health = rs.getInt("_health");
                user.Force = rs.getInt("_force");

            }
        }catch (SQLException e){
            Logger.log(e.getMessage(),"SQLException");
            user=null;
        }
        return user;
    }

    public static User createNewUser(String username){
        int uid = SqlConnection.getLengthOf("_user") + 1;
        String sql="insert into _user(_uid,_username,_password,_banned,_experience,_health,_force,_permission,_count) values("+uid+",'"+username+"','"+Config.OPERATING_CODE+"',0,0,128,16,1,0,";
        if(SqlConnection.doSqlNoResult(sql)){
            User user = byUsername(username);
            return user;
        }else {
            return null;
        }
    }

    public String getUsername(){
        return Username;
    }

    public long getUid(){
        return Uid;
    }

    public int getHealth(){
        return Health;
    }
    public String getPassword(){
        return Password;
    }

    public void setUsername(String username){
        Username = username;
        String sql = "update _user set _username = '"+username+"' where _uid = "+Uid;
        SqlConnection.doSql(sql);
    }

    public void ban(){
        banned = true;
        String sql = "update _user set _banned = 1 where _uid = "+Uid;
        SqlConnection.doSql(sql);
    }

    public void cancelBan(){
        banned=false;
        String sql = "update _user set _banned = 0 where _uid = "+Uid;
        SqlConnection.doSql(sql);
    }

    public boolean isBaned(){
        return banned;
    }

    public void setPassword(String oldPassword , String newPassword) throws WrongPasswordException {
        if(oldPassword.equals(Password)||oldPassword.equals(Config.OPERATING_CODE)){
            Password = newPassword;
            String sql = "update _user set _password = '"+Password+"' where _uid = "+Uid;
            SqlConnection.doSql(sql);
        }else{
            throw new WrongPasswordException(newPassword,Uid,Username);
        }
    }


}
