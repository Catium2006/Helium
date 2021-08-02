package cn.tonyn.helium.user;

import cn.tonyn.helium.Config;
import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.data.item.type.�·�;
import cn.tonyn.helium.data.item.type.װ��;
import cn.tonyn.helium.exceptions.WrongPasswordException;
import cn.tonyn.helium.mysql.SqlConnection;
import cn.tonyn.log.Logger;


import java.sql.ResultSet;
import java.sql.SQLException;

public class User {
    int Uid;
    String Username;
    String Password;
    boolean banned;
    int Experience;
    int Health;
    int Force;
    int Permission;
    int Luck;
    �·� Clothing;
    װ�� Equipment;

    /**
     * ��������
     */
    int Count;

    int Money;

    /**
     * ����Uid��ȡ�û�����,�������ݿ�
     * @param uid
     * @return
     */
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
                user.Permission = rs.getInt("_permission");
                user.Count = rs.getInt("_count");
                user.Money = rs.getInt("_money");
                user.Luck = rs.getInt("_luck");
                user.Clothing = �·�.byName(rs.getString("_clothing"));
                user.Equipment = װ��.byName(rs.getString("_equipment"));
            }
            user.Health=user.Health+user.Clothing.��������;
            user.Force=user.Force+user.Clothing.��������;
            user.Luck=user.Luck+user.Clothing.��������;
            user.Health=user.Health+user.Equipment.��������;
            user.Force=user.Force+user.Equipment.��������;
            user.Luck=user.Luck+user.Equipment.��������;
        }catch (SQLException e){
            Logger.log(e.getMessage(),"SQLException");
            user=null;
        }
        return user;
    }

    /**
     * �����û�����ȡ�û�����,�������ݿ�
     * @param username
     * @return
     */
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
                user.Permission = rs.getInt("_permission");
                user.Count = rs.getInt("_count");
                user.Money = rs.getInt("_money");
                user.Luck = rs.getInt("_luck");
                user.Clothing = �·�.byName(rs.getString("_clothing"));
                user.Equipment = װ��.byName(rs.getString("_equipment"));
            }
            user.Health=user.Health+user.Clothing.��������;
            user.Force=user.Force+user.Clothing.��������;
            user.Luck=user.Luck+user.Clothing.��������;
            user.Health=user.Health+user.Equipment.��������;
            user.Force=user.Force+user.Equipment.��������;
            user.Luck=user.Luck+user.Equipment.��������;
        }catch (SQLException e){
            Logger.log(e.getMessage(),"SQLException");
            user=null;
        }
        return user;
    }

    /**
     * ����ָ�����ֵ��û�������
     *
     * @param username
     * @return
     */
    public static User createNewUser(String username){
        int uid = SqlConnection.getLengthOf("_user");
        String sql="insert into _user(_uid,_username,_password,_banned,_experience,_health,_force,_permission,_count,_money,_luck,_equipment,_clothing) values("+uid+",'"+username+"','"+Config.OPERATING_CODE+"',0,0,128,16,1,0,0,0,'����','���·�')";
        SqlConnection.doSqlNoResult(sql);
        User user = byUsername(username);
        Backpack.createBackpack(user);
        return user;
    }


    public int getUid(){
        return Uid;
    }

    public String getUsername(){
        return Username;
    }

    public String getPassword(){
        return Password;
    }

    public int getExperience(){
        return Experience;
    }

    public int getHealth(){
        return Health;
    }

    public int getForce(){
        return Force;
    }

    public int getPermission(){
        return Permission;
    }

    public int getCount(){
        return Count;
    }

    public int getMoney(){
        return Money;
    }

    public Item getClothing(){
        return Clothing;
    }

    public Item getEquipment(){
        return Equipment;
    }

    public int getLuck(){return Luck;}

    public boolean isBaned(){
        return banned;
    }

    public boolean isAdmin(){
        if(getPermission()==127){
            return true;
        }
        return false;
    }

    public void setUsername(String username){
        Username = username;
        String sql = "update _user set _username = '"+username+"' where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    /**
     * ��������� ��Ҫԭʼ����ȷ��
     * ԭʼ�������Ϊ��������������û�����
     * ������������:
     * @see Config
     * �׳��������쳣
     *
     * @param oldPassword
     * @param newPassword
     * @throws WrongPasswordException
     */
    public void setPassword(String oldPassword , String newPassword) throws WrongPasswordException {
        if(oldPassword.equals(Password)||oldPassword.equals(Config.OPERATING_CODE)){
            Password = newPassword;
            String sql = "update _user set _password = '"+newPassword+"' where _uid = "+Uid;
            SqlConnection.doSqlNoResult(sql);
        }else{
            throw new WrongPasswordException(newPassword,Uid,Username);
        }
    }

    public void setForce(int force){
        Force = force;
        String sql = "update _user set _force = '"+force+"' where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    public void setHealth(int health){
        Health=health;
        String sql = "update _user set _health = '"+health+"' where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    public void setExperience(int experience){
        Experience=experience;
        String sql = "update _user set _experience = '"+experience+"' where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);

    }

    public void setPermission(int permission){
        Permission=permission;
        String sql = "update _user set _permission = '"+permission+"' where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    public void setCount(int count){
        Count=count;
        String sql = "update _user set _count = '"+count+"' where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    public void setMoney(int money){
        Money=money;
        String sql = "update _user set _money = '"+money+"' where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    public void setClothing(�·� item){
        Clothing=item;
        String sql = "update _user set _clothing = '"+Clothing.toString()+"' where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    public void setEquipment(װ�� item){
        Equipment=item;
        String sql = "update _user set _equipment = '"+Equipment.toString()+"' where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    /**
     * ����û�
     */
    public void ban(){
        banned = true;
        String sql = "update _user set _banned = 1 where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    /**
     * ȡ�����
     */
    public void cancelBan(){
        banned=false;
        String sql = "update _user set _banned = 0 where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    @Override
    public String toString(){
        String s = "User("+Uid+","+Username+")";
        return s;
    }

}
