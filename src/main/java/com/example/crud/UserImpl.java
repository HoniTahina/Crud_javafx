package com.example.crud;

import java.sql.ResultSet;

public class UserImpl implements IUser{
    private DB db=new DB();
    @Override
    public User seConnecter(String userName, String password) {
        User user = null;
        String sql = "SELECT * FROM user WHERE userName = ? AND password = ?";
        try{
            db.initPrepar(sql);

            db.getPstm().setString(1,userName);
            db.getPstm().setString(2,password);

            ResultSet rs = db.ExecuteSelect();
            if(rs.next()){
                user = new User();
                user.setUserName(rs.getString("userName"));
                user.setPassword(rs.getString("password"));
            }
            db.closeConnection();
        }catch (Exception e){
            e.printStackTrace();
        }
        return user;
    }
}
