
package com.example.dao;

import com.example.model.AdminModel;
import com.example.util.DateTimeSetting;
import com.example.util.DbConnector;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
                    adminModel.setRole(rs.getString("aRole"));
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
}
