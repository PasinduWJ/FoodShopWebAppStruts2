package com.example.action;

import com.example.dao.FoodDao;
import com.example.dao.OrderDao;
import com.example.model.GridUserOrderModel;
import com.example.model.OrderModel;
import com.example.util.DateTimeSetting;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import java.util.List;
import java.util.Map;

public class GridUserAction extends ActionSupport {

    private int rows = 0, page = 0, total = 0, records = 0;
    private String message;
    private OrderModel order;
    private String oper;
    private String id;
    private List<GridUserOrderModel> orderGridModel;

    @Override
    public String execute() throws Exception {
        Map<String, Object> session = ActionContext.getContext().getSession();
        String logName = session.get("logName").toString();
        int to = (getRows() * getPage());
        int from = to - getRows();
        this.orderGridModel = OrderDao.getOrderListGridByUserName(logName);
        this.records = getOrderGridModel().size();
        if (getOrderGridModel().size() < to) {
            to = getOrderGridModel().size();
        }
        this.orderGridModel = getOrderGridModel().subList(from, to);
        this.total = (int) getRecords() / getRows() + 1;
        return SUCCESS;
    }

    public String edit() {
        Map<String, Object> session = ActionContext.getContext().getSession();
        String logName = session.get("logName").toString();
        if (oper.equalsIgnoreCase("edit")) {
            try {
                if (order.getDelivery()) {
                    this.message = "Order Edit Faill.. Alredy send This Order ...";
                } else {
                    boolean valid = false;
                    int fId = order.getFoodId();
                    if (FoodDao.getMaxOrdersByFoodId(fId) > (OrderDao.getAllOrdersByFoodId(fId) + order.getQuantity() - OrderDao.getOrderByFoodId(order.getId()) - 1)) {
                        valid = true;
                    }
                    if (valid) {
                        OrderModel editOrder = new OrderModel(order.getId(), logName, order.getQuantity(), DateTimeSetting.getCurrentDateTime());
                        if (OrderDao.editOrder(editOrder)) {
                            this.message = "Order Edit Successful...";
                        } else {
                            this.message = "Order Edit Fail...";
                        }
                    } else {
                        this.message = "Order Edit Fail... Food Not Available Now..";
                    }
                }
            } catch (Exception e) {
                this.message = e.toString();
            }
        } else if (oper.equalsIgnoreCase("del")) {
            try {
                if (OrderDao.deleteOrderById(Integer.parseInt(id), logName, DateTimeSetting.getCurrentDateTime())) {
                    this.message = "Order Deelete Successfull...";
                } else {
                    this.message = "Order Deelete is Fail...";
                }
            } catch (Exception e) {
                this.message = e.toString();
            }
        }
        return SUCCESS;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getRecords() {
        return records;
    }

    public void setRecords(int records) {
        this.records = records;
    }

    public List<GridUserOrderModel> getOrderGridModel() {
        return orderGridModel;
    }

    public void setOrderGridModel(List<GridUserOrderModel> orderGridModel) {
        this.orderGridModel = orderGridModel;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOper() {
        return oper;
    }

    public void setOper(String oper) {
        this.oper = oper;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
