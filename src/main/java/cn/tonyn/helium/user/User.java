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

    User ByUid(int uid){
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

    User ByUsername(String username){
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
    }

    public void cancelBan(){
        banned=false;
    }

    public boolean isBaned(){
        return banned;
    }

    public void setPassword(String oldPassword , String newPassword) throws WrongPasswordException {
        if(oldPassword.equals(Password)||oldPassword.equals(Config.OPERATING_CODE)){
            Password = newPassword;
        }else{
            throw new WrongPasswordException(newPassword,Uid,Username);
        }
    }


}
