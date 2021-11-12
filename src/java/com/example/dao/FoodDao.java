package com.example.dao;

import com.example.model.FoodModel;
import com.example.util.DateTimeSetting;
import com.example.util.DbConnector;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


public class FoodDao {

    public static List<FoodModel> getAllFoodList() throws Exception {
        Connection conn = DbConnector.getConnection();
        ResultSet rs = null;
        List<FoodModel> foodList = new ArrayList();
        try {
            if (conn != null) {
                rs = conn.createStatement().executeQuery("select * from food where state = true");
                while (rs.next()) {
                    foodList.add(new FoodModel(
                            rs.getInt("id"), rs.getString("fName"), rs.getDouble("uPrice"),
                            rs.getInt("maxOrder"), rs.getBoolean("available"),
                            DateTimeSetting.setDateTime(rs.getTimestamp("uDateTime")), rs.getString("updateBy"),
                            rs.getBoolean("available")));
                }
                return foodList;
            }
        } catch (Exception e) {
            throw new Exception("Database Table error " );
        } finally {
            if (rs != null) 
                rs.close();
            if (conn != null) 
                conn.close();
            
        }
        return foodList;
    }
    
    public static List<FoodModel> getAllFoodListFor() throws Exception {
        Connection conn = DbConnector.getConnection();
        ResultSet rs = null;
        List<FoodModel> foodList = new ArrayList();
        try {
            if (conn != null) {
                rs = conn.createStatement().executeQuery("select * from food");
                while (rs.next()) {
                    foodList.add(new FoodModel(
                            rs.getInt("id"), rs.getString("fName"), rs.getDouble("uPrice"),
                            rs.getInt("maxOrder"), rs.getBoolean("available"),
                            DateTimeSetting.setDateTime(rs.getTimestamp("uDateTime")), rs.getString("updateBy"),
                            rs.getBoolean("available")));
                }
                return foodList;
            }
        } catch (Exception e) {
            throw new Exception("Database Table error " );
        } finally {
            if (rs != null) 
                rs.close();
            if (conn != null) 
                conn.close();
            
        }
        return foodList;
    }

    public static int getMaxOrdersByFoodId(int fId) throws Exception {
        Connection conn = DbConnector.getConnection();
        ResultSet rs = null;
        int maxOder = 0;
        try {
            rs = conn.createStatement().executeQuery("select maxOrder from food where id= " + fId );
            if (rs.next()) {
                maxOder = rs.getInt("maxOrder");
            }
        } catch (Exception e) {
            throw new Exception("Database Table error " );
        } finally {
            if (rs != null) 
                rs.close();
            if (conn != null) 
                conn.close();
            
        }
        return maxOder;
    }
    
    
       public static Boolean editFoodItem(FoodModel fModel) throws Exception{
        Connection conn = DbConnector.getConnection();
        try{
            if(conn != null){
                int rs = conn.createStatement().executeUpdate("UPDATE food SET fName = '"+fModel.getFoodName()+"', uPrice = "+fModel.getUnitPrice()+
                                                                ",maxOrder = "+fModel.getMaxOrder()+", available = "+fModel.getAvailable()+
                                                                ",uDateTime = '" + fModel.getUpDateTime() + "',updateBy = '"+ fModel.getUpdateBy() +"' where id ="+fModel.getId());

                if(rs>0)
                    return true;
            }
        }catch(SQLException e){
            throw new Exception("Database Table error "  );
        }finally{
            if(conn != null)
                conn.close();
        }
        return false;
    }
       
       public static Boolean addNewFood(FoodModel fModel) throws Exception{
        Connection conn = DbConnector.getConnection();
        int rs = 0;
        try{
            if(conn != null){
                rs = conn.createStatement().executeUpdate("insert into food(fName,uPrice,maxOrder,available,uDateTime,updateBy,state)"
                        + " values ('" + fModel.getFoodName()+ "'," + fModel.getUnitPrice() + "," + fModel.getMaxOrder()+
                        ",true,'"+fModel.getUpDateTime()+"','" + fModel.getUpdateBy() + "',true);");  

                if(rs>0)
                    return true;
            }
        }catch(SQLException e){
            throw new Exception("Database Table error "  );
        }finally{
            if(conn != null)
                conn.close();
        }
        return false;
    }
    public static Boolean deleteFoodItem(String adminName, String uDate, int id) throws Exception{
        Connection conn = DbConnector.getConnection();
        try{
            if(conn != null){
                int rs = conn.createStatement().executeUpdate("UPDATE food SET updateBy = '"+ adminName +"', uDateTime='"+ uDate +
                        "',state=false where id ="+ id +";");

                if(rs>0)
                    return true;
            }
        }catch(SQLException e){
            throw new Exception("Database Table error "  );
        }finally{
            if(conn != null)
                conn.close();
        }
        return false;
    }
    
}
