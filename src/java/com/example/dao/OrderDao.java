package com.example.dao;

import com.example.model.GridUserOrderModel;
import com.example.model.OrderModel;
import com.example.util.DateTimeSetting;
import com.example.util.DbConnector;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class OrderDao {

    public static boolean saveOrder(List<OrderModel> order) throws Exception {
        Connection conn = DbConnector.getConnection();
        int rs = 0;
        try {
            if (conn != null) {
                Iterator it = order.iterator();
                String s = "";
                while (it.hasNext()) {
                    OrderModel oModel = (OrderModel) it.next();
                    s = s + "('" + oModel.getUserName() + "'," + oModel.getFoodId() + "," + oModel.getQuantity()
                            + ",'" + oModel.getUpDateTime() + "','" + oModel.getUpDateTime() + "','" + oModel.getUserName() + "'),";
                }
                rs = conn.createStatement().executeUpdate("insert into fOrder(uName,fId,quantity,cDateTime,uDateTime,updateBy)"
                        + " values" + s.substring(0, s.length() - 1) + ";");
                if (rs > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            throw new Exception("Database Table error ");
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    public static List<OrderModel> getOrderListByUserName(String username) throws Exception {
        Connection conn = DbConnector.getConnection();
        List<OrderModel> orderList = new ArrayList();
        try {
            if (conn != null) {
                ResultSet rs = conn.createStatement().executeQuery("select * from fOrder where uName = '" + username + "' and state=true  order by uDateTime desc");
                while (rs.next()) {
                    orderList.add(new OrderModel(rs.getInt("id"), rs.getInt("fId"), rs.getInt("quantity"),
                            DateTimeSetting.setDateTime(rs.getTimestamp("uDateTime")), rs.getBoolean("delivery")));
                }
                return orderList;
            }
        } catch (Exception e) {
            throw new Exception("Database Table error ");
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return orderList;
    }

    public static List<GridUserOrderModel> getOrderListGridByUserName(String username) throws Exception {
        Connection conn = DbConnector.getConnection();
        List<GridUserOrderModel> orderList = new ArrayList();
        try {
            if (conn != null) {
                ResultSet rs = conn.createStatement().executeQuery("select * from fOrder LEFT JOIN food ON fOrder.fId = food.id  where fOrder.uName = '" + username +
                        "' and fOrder.state=true  order by fOrder.uDateTime desc");
                while (rs.next()) {
                    orderList.add(new GridUserOrderModel(rs.getString("fName"),rs.getInt("maxOrder"), new OrderModel(rs.getInt("id"), rs.getInt("fId"), rs.getInt("quantity"),
                            DateTimeSetting.setDateTime(rs.getTimestamp("uDateTime")), rs.getBoolean("delivery"))));
                }
                return orderList;
            }
        } catch (Exception e) {
            throw new Exception("Database Table error ");
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return orderList;
    }
    
    public static Boolean deleteOrderById(int id, String userName, String uDateTime) throws Exception {
        Connection conn = DbConnector.getConnection();
        try {
            if (conn != null) {
                int rs = conn.createStatement().executeUpdate("UPDATE fOrder SET uDateTime='" + uDateTime + "',updateBy='" + userName + "',state=false  where id = " + id);
                if (rs > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Database Table error ");
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    public static Boolean sendOrder(int id, String userName, String uDateTime) throws Exception {
        Connection conn = DbConnector.getConnection();
        try {
            if (conn != null) {
                int rs = conn.createStatement().executeUpdate("UPDATE fOrder SET uDateTime='" + uDateTime + "',updateBy='" + userName + "',delivery=true  where id = " + id);
                if (rs > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Database Table error ");
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    public static Boolean editOrder(OrderModel eOrder) throws Exception {
        Connection conn = DbConnector.getConnection();
        try {
            if (conn != null) {
                int rs = conn.createStatement().executeUpdate("UPDATE fOrder SET quantity = " + eOrder.getQuantity() + ", uDateTime = '" + eOrder.getUpDateTime()
                        + "',updateBy = '" + eOrder.getUserName() + "' where id = " + eOrder.getId());
                if (rs > 0) {
                    return true;
                }
            }
        } catch (SQLException e) {
            throw new Exception("Database Table error ");
        } finally {
            if (conn != null) {
                conn.close();
            }
        }
        return false;
    }

    public static JsonArray getAllOrders() throws Exception {
        Connection conn = DbConnector.getConnection();
        ResultSet rs = null;
        JsonArray allOrders = new JsonArray();
        try {
            rs = conn.createStatement().executeQuery("select fId, quantity from fOrder where state = true");
            while (rs.next()) {
                JsonObject job = new JsonObject();
                job.addProperty("foodId", rs.getInt("fId"));
                job.addProperty("quantity", rs.getInt("quantity"));
                allOrders.add(job);
            }
        } catch (Exception e) {
            throw new Exception("Database Table error ");
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return allOrders;
    }

    public static JsonArray getAllOrdersForAdmin() throws Exception {
        Connection conn = DbConnector.getConnection();
        ResultSet rs = null;
        JsonArray allOrders = new JsonArray();
        try {
            rs = conn.createStatement().executeQuery("select * from fOrder where state = true");
            while (rs.next()) {
                JsonObject job = new JsonObject();
                job.addProperty("id", rs.getInt("id"));
                job.addProperty("foodId", rs.getInt("fId"));
                job.addProperty("quantity", rs.getInt("quantity"));
                job.addProperty("userName", rs.getString("uName"));
                job.addProperty("upDateTime", rs.getString("uDateTime"));
                job.addProperty("delivery", rs.getBoolean("delivery"));
                allOrders.add(job);
            }
        } catch (Exception e) {
            throw new Exception("Database Table error ");
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return allOrders;
    }

    public static int getAllOrdersByFoodId(int fId) throws Exception {
        Connection conn = DbConnector.getConnection();
        ResultSet rs = null;
        int currentOders = 0;
        try {
            rs = conn.createStatement().executeQuery("select quantity from fOrder where fId= " + fId + " and state=true");
            while (rs.next()) {
                currentOders += rs.getInt("quantity");
            }
        } catch (Exception e) {
            throw new Exception("Database Table error ");
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return currentOders;
    }
    
    public static int getOrderByFoodId(int id) throws Exception {
        Connection conn = DbConnector.getConnection();
        ResultSet rs = null;
        int currentOders = 0;
        try {
            rs = conn.createStatement().executeQuery("select quantity from fOrder where id= " + id + " and state=true");
            if (rs.next()) {
                currentOders = rs.getInt("quantity");
            }
        } catch (Exception e) {
            throw new Exception("Database Table error ");
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return currentOders;
    }
    
}
