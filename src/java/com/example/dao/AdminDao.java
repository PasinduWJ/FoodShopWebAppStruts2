
package com.example.dao;

import com.example.model.AdminModel;
import com.example.util.DateTimeSetting;
import com.example.util.DbConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdminDao {
     private static Connection conn = null;
    
    public static AdminModel getAdminByAdminName(String aName) throws Exception{
        conn = DbConnector.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        AdminModel adminModel = new AdminModel();
        try{
                stmt = conn.prepareStatement("Select * from admin where aName=?  and state=true");
                stmt.setString(1, aName);
                rs= stmt.executeQuery();
                if(rs.next()){
                    adminModel.setAdminName(rs.getString("aName"));
                    adminModel.setPhNumber(rs.getInt("pNumber"));
                    adminModel.setPassword(rs.getString("password"));
                    adminModel.setAdRole(rs.getString("aRole"));
                    adminModel.setState(rs.getBoolean("state"));
                    adminModel.setCrDateTime(DateTimeSetting.setDateTime(rs.getTimestamp("cDateTime")));
                    adminModel.setUpDateTime(DateTimeSetting.setDateTime(rs.getTimestamp("uDateTime")));
                    adminModel.setUpdateBy(rs.getString("updateBy"));
                    
                    return adminModel;
                }
        }catch(Exception e){
            throw new Exception("Database Table error " +e);
        }        
        finally{
            if (rs != null) 
                rs.close();
            if (stmt != null) 
                stmt.close();
            if (conn != null) 
                 conn.close();
        }
        return adminModel;
    }
    
    public static List getAllAdmin() throws Exception{
        conn = DbConnector.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<AdminModel> adinList = new ArrayList();
        try{
                stmt = conn.prepareStatement("Select * from admin where state=true");
                rs= stmt.executeQuery();
                
                while(rs.next()){
                    adinList.add(new AdminModel(rs.getString("aName"),rs.getInt("pNumber"),rs.getString("aRole")));
                    
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
        return adinList;
    }
    
    public static Boolean deleteAdmin(String adminName, String updateBy, String uDateTime) throws Exception{
        Connection conn = DbConnector.getConnection();
        try{
            if(conn != null){
                int rs = conn.createStatement().executeUpdate("UPDATE admin SET state = false , uDateTime = '"+ uDateTime +
                        "',updateBy = '"+ updateBy +"' where aName = '" + adminName +"';" );
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
    
    public static boolean registerAdmin(AdminModel aModel) throws Exception{
        conn = DbConnector.getConnection();
        
        boolean status = false;
        try{
                int rs = conn.createStatement().executeUpdate("Insert into admin values('"+ aModel.getAdminName() +
                        "'," + aModel.getPhNumber()+ ",'"+ aModel.getPassword() + "','"+ aModel.getAdRole() +"',true,'"+
                        aModel.getCrDateTime()+ "','"+ aModel.getCrDateTime()+"','"+ aModel.getUpdateBy()+"' );");
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
    
    public static Boolean editAdmin(AdminModel aModel, String aName) throws Exception{
        Connection conn = DbConnector.getConnection();
        try{
            if(conn != null){
                int rs = conn.createStatement().executeUpdate("UPDATE admin SET aName='"+aModel.getAdminName()+"', aRole='"+aModel.getAdRole()+
                        "', pNumber="+aModel.getPhNumber()+",  uDateTime = '"+ aModel.getUpDateTime()+
                        "',updateBy = '"+aModel.getUpdateBy() +"' WHERE aName = '" + aName +"';" );
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
    
    
}
