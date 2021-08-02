package cn.tonyn.helium.user;

import cn.tonyn.helium.Config;
import cn.tonyn.helium.data.item.type.Item;
import cn.tonyn.helium.data.item.type.衣服;
import cn.tonyn.helium.data.item.type.装备;
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
    衣服 Clothing;
    装备 Equipment;

    /**
     * 发言总数
     */
    int Count;

    int Money;

    /**
     * 根据Uid获取用户对象,访问数据库
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
                user.Clothing = 衣服.byName(rs.getString("_clothing"));
                user.Equipment = 装备.byName(rs.getString("_equipment"));
            }
            user.Health=user.Health+user.Clothing.增加生命;
            user.Force=user.Force+user.Clothing.增加力量;
            user.Luck=user.Luck+user.Clothing.增加幸运;
            user.Health=user.Health+user.Equipment.增加生命;
            user.Force=user.Force+user.Equipment.增加力量;
            user.Luck=user.Luck+user.Equipment.增加幸运;
        }catch (SQLException e){
            Logger.log(e.getMessage(),"SQLException");
            user=null;
        }
        return user;
    }

    /**
     * 根据用户名获取用户对象,访问数据库
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
                user.Clothing = 衣服.byName(rs.getString("_clothing"));
                user.Equipment = 装备.byName(rs.getString("_equipment"));
            }
            user.Health=user.Health+user.Clothing.增加生命;
            user.Force=user.Force+user.Clothing.增加力量;
            user.Luck=user.Luck+user.Clothing.增加幸运;
            user.Health=user.Health+user.Equipment.增加生命;
            user.Force=user.Force+user.Equipment.增加力量;
            user.Luck=user.Luck+user.Equipment.增加幸运;
        }catch (SQLException e){
            Logger.log(e.getMessage(),"SQLException");
            user=null;
        }
        return user;
    }

    /**
     * 创建指定名字的用户并返回
     *
     * @param username
     * @return
     */
    public static User createNewUser(String username){
        int uid = SqlConnection.getLengthOf("_user");
        String sql="insert into _user(_uid,_username,_password,_banned,_experience,_health,_force,_permission,_count,_money,_luck,_equipment,_clothing) values("+uid+",'"+username+"','"+Config.OPERATING_CODE+"',0,0,128,16,1,0,0,0,'手套','破衣服')";
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
     * 改密码操作 需要原始密码确认
     * 原始密码必须为服务器操作码或用户密码
     * 服务器操作码:
     * @see Config
     * 抛出错密码异常
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

    public void setClothing(衣服 item){
        Clothing=item;
        String sql = "update _user set _clothing = '"+Clothing.toString()+"' where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    public void setEquipment(装备 item){
        Equipment=item;
        String sql = "update _user set _equipment = '"+Equipment.toString()+"' where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    /**
     * 封禁用户
     */
    public void ban(){
        banned = true;
        String sql = "update _user set _banned = 1 where _uid = "+Uid;
        SqlConnection.doSqlNoResult(sql);
    }

    /**
     * 取消封禁
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
