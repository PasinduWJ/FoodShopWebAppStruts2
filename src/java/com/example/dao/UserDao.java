
package com.example.dao;
import com.example.model.UserModel;
import com.example.util.DbConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class UserDao {
    
    private static Connection conn = null;
    
    public static UserModel getUserByUserName(String userName) throws Exception{
        conn = DbConnector.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UserModel userModel = new UserModel();
        try{
                stmt = conn.prepareStatement("Select * from user where uName=? and state=true");
                stmt.setString(1, userName);
                rs= stmt.executeQuery();

                if(rs.next()){
                    userModel.setUserName(rs.getString("uName"));
                    userModel.setPassword(rs.getString("password"));
                    return userModel;
                }
        }catch(Exception e){
            throw new Exception("Database Table error ");
        }        
        finally{
            if (rs != null) 
                rs.close();
            if (stmt != null) 
                stmt.close();
            if (conn != null) 
                 conn.close();
        }
        return userModel;
    }
    
    public static boolean registerUser(UserModel userModel) throws Exception{
        conn = DbConnector.getConnection();
        
        boolean status = false;
        try{
                int rs = conn.createStatement().executeUpdate("Insert into user values('"+ userModel.getUserName() +
                        "','"+ userModel.getFirstName()+ "','"+ userModel.getLastName()+ "',"+ userModel.getPhNumber()+
                        ",'"+ userModel.getPassword() + "',true,'"+ userModel.getCrDateTime()+
                       "','"+ userModel.getCrDateTime()+"','"+ userModel.getUserName() +"' );");
                if(rs>0)
                    status = true;
        }catch(Exception e){
            throw new Exception("User Name Already Exeist" );
        }        
        finally{
            if(conn != null)
                conn.close();
        }
        return status;
    }
}
