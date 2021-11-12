
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %> 
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/7eefacbee9.js" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="userPage.js"></script>
        <title>User Page</title>
    </head>
    <body onload="userLoad()">
        <%@include file="WEB-INF/header.jspf" %>

        <br><br><br>
        <div class="container">

            <h1>Hello <samp id="sessUserName"> </samp> </h1>

            <div class="col-5 col-md-5 alert alert-info position-absolute top-10 end-0 d-none" id="alertBox" role="alert">
                <p id="message"> ${message}</p>
            </div>

            <br>
            <div class="d-grid gap-2 d-md-flex justify-content-md">
                <button type="button" class="btn btn-success" id="addOder"> Add Order </button>
            </div>
            <br>
           
            <div class="row d-none" id="addForm">
                <div class="container" style="margin-bottom: 100px; margin-top: 20px;">
                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th scope="col"> </th>
                                <th scope="col"> Name </th>
                                <th scope="col"> Unit Price </th>
                                <th scope="col"> Quantity </th>
                                <th scope="col"> Available </th>
                                <th scope="col"> Action </th>
                            </tr>
                        </thead>
                        <tbody id="foodTable">
                        </tbody>
                    </table>
                    <h4> Total = Rs. <samp class="fw-bold" id="totalPrice"> 0.00 </samp></h4>
                    <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                        <button type="button" class="btn btn-danger " id="order-can-btn"> Cancel </button>
                        <button type="button" class="btn btn-primary"id="oderNow"> Order Now </button>
                    </div>
                </div>

            </div>

            <div class="row">
                <div class="container" style="margin-bottom: 100px; margin-top: 20px;">

                    <table class="table table-striped">
                        <thead>
                            <tr>
                                <th scope="col"> </th>
                                <th scope="col"> Name </th>
                                <th scope="col">Order Date & Time</th>
                                <th scope="col"> Order Send </th>
                                <th scope="col"> Quantity </th>
                                <th scope="col"> Action </th>
                            </tr>
                        </thead>
                        <tbody id="orderTable">
                        </tbody>
                    </table>
                    <h4> Total Bill = Rs. <samp class="fw-bold" id="totalBill"> 0.00 </samp></h4>
                </div>
            </div>
        </div>
           
    </body>
</html>
