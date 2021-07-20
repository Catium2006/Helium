package cn.tonyn.helium.user;

import cn.tonyn.helium.Config;
import cn.tonyn.helium.exceptions.WrongPasswordException;

public class User {
    String Username;
    long UID;
    String EMail;
    String Password;

    boolean baned;

    User(String username){
        setUsername(username);
        try{
            setPassword(Config.OPERATING_CODE,"");
        }catch (WrongPasswordException e){
            e.printStackTrace();
        }

    }

    public String getUsername(){
        return Username;
    }

    public long getUID(){
        return UID;
    }

    public String getEMail(){
        return EMail;
    }

    public String getPassword(){
        return Password;
    }

    public void setUsername(String username){
        Username = username;
    }

    public void setEMail(String eMail){
        EMail = eMail;
    }

    public void ban(){
        baned = true;
    }

    public void cancelBan(){
        baned=false;
    }

    public boolean isBaned(){
        return baned;
    }

    public void setPassword(String oldPassword , String newPassword) throws WrongPasswordException {
        if(oldPassword.equals(Password)||oldPassword.equals(Config.OPERATING_CODE)){
            Password = newPassword;
        }else{
            throw new WrongPasswordException(newPassword,UID,Username);
        }
    }

}
