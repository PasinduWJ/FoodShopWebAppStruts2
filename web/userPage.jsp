
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %> 
<!DOCTYPE html>
<html>

    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/7eefacbee9.js" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
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
                <button type="button" class="btn btn-success" id="addOder"> <i class="fas fa-utensils" style="margin-right: 10px;"></i>  Add Order </button>
                <button type="button" class="btn btn-primary" data-bs-toggle="modal" data-bs-target="#exampleModal1" id="editUserId"><i class="fas fa-user-tie" style="margin-right: 10px;"></i> Edit My Profile </button>
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
            
            <div class="modal fade" id="exampleModal1" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
                <div class="modal-dialog modal-dialog-scrollable modal-lg">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h5 class="modal-title" id="exampleModalLabel">  </h5>
                            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                        </div>
                        <div class="modal-body">



                        </div>
                    </div>
                </div>
            </div>
           
    </body>
</html>
