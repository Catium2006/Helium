package cn.tonyn.helium.exceptions;


public class WrongPasswordException extends Exception{
    String NewPassword;
    String Username;
    long UID;

    public WrongPasswordException(String newPassword,long UID,String username){
        NewPassword = newPassword;
    }

    @Override
    public String toString(){
        String r = "WrongPassword!(UID="+UID+",Username="+Username+",NewPassword="+NewPassword+")";
        return null;
    }

    @Override
    public String getMessage() {
        return toString();
    }

}
