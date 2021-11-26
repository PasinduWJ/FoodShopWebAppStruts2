
package com.example.dao;
import com.example.model.UserModel;
import com.example.util.DbConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


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
                    userModel.setFirstName(rs.getString("fName"));
                    userModel.setLastName(rs.getString("lName"));
                    userModel.setPhNumber(rs.getInt("pNumber"));
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
    public static UserModel getUserByUserNameFor(String userName) throws Exception{
        conn = DbConnector.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        UserModel userModel = new UserModel();
        try{
                stmt = conn.prepareStatement("Select * from user where uName=?");
                stmt.setString(1, userName);
                rs= stmt.executeQuery();

                if(rs.next()){
                    userModel.setUserName(rs.getString("uName"));
                    userModel.setFirstName(rs.getString("fName"));
                    userModel.setLastName(rs.getString("lName"));
                    userModel.setPhNumber(rs.getInt("pNumber"));
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
    
    public static List getAllUsers() throws Exception{
        conn = DbConnector.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<UserModel> userList = new ArrayList();
        try{
                stmt = conn.prepareStatement("Select * from user where state=true");
                rs= stmt.executeQuery();
                
                while(rs.next()){
                    userList.add(new UserModel(rs.getString("fName"),rs.getString("lName"),rs.getString("uName"),rs.getInt("pNumber")));
                    
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
        return userList;
    }
    
    public static Boolean deleteUserByAdmin(UserModel userModel) throws Exception{
        Connection conn = DbConnector.getConnection();
        try{
            if(conn != null){
                int rs = conn.createStatement().executeUpdate("UPDATE user SET state = false , uDateTime = '"+ userModel.getUpDateTime()+
                        "',updateBy = '"+userModel.getUpdateBy() +"' where uName = '" + userModel.getUserName()+"';" );
                if(rs>0)
                    return true;
            }
        }catch(Exception e){
            throw new Exception("Database Table error ");
        }finally{
            if(conn != null)
                conn.close();
        }
        return false;
    }
    
    public static Boolean editUserByAdmin(UserModel userModel, String uName) throws Exception{
        Connection conn = DbConnector.getConnection();
        try{
            if(conn != null){
                int rs = conn.createStatement().executeUpdate("UPDATE user SET uName='"+userModel.getUserName()+"', fName='"+userModel.getFirstName()+
                        "',lName='"+userModel.getLastName() +"',pNumber="+userModel.getPhNumber()+",  uDateTime = '"+ userModel.getUpDateTime()+
                        "',updateBy = '"+userModel.getUpdateBy() +"' where uName = '" + uName +"';" );
                if(rs>0)
                    return true;
            }
        }catch(Exception e){
            throw new Exception("Database Table error ");
        }finally{
            if(conn != null)
                conn.close();
        }
        return false;
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
