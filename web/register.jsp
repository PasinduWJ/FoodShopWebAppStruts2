<%-- 
    Document   : register
    Created on : Nov 10, 2021, 1:19:56 PM
    Author     : PASINDU-PC
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <title>Register Page</title>
    </head>
    <body>
        <%@include file="WEB-INF/header.jspf" %>
        <br><br>
        <br><br>
        <div class="container">
            <br><br>
            <h2>Registration page</h2>
            <br><br>

            <div class="alert alert-primary d-none" id="alertBox" role="alert">
               <p id="mid">  ${message} </p>
            </div>
            
               <s:form action="register">
            <div class="mb-3">
                <s:label cssClass="form-label"  value="User Name"/>
                <s:textfield cssClass="form-control" placeholder="Enter Last Name" name="userName"/>
            </div>
            <div class="mb-3">
                <s:label cssClass="form-label"  value="First Name"/>
                <s:textfield cssClass="form-control" placeholder="Enter Last Name" name="firstName"/>
            </div>
            <div class="mb-3">
                <s:label cssClass="form-label"  value="Last Name"/>
                <s:textfield cssClass="form-control" placeholder="Enter Last Name" name="lastName"/>
            </div>
            <div class="mb-3">
                <s:label cssClass="form-label" value="Phone Number"/>
                <s:textfield cssClass="form-control" placeholder="Enter Phone Number" name="number"/>
            </div>
            <div class="mb-3">
                <s:label for="exampleInputPassword1" cssClass="form-label" value="Password"/>
                <s:password maxLength="9"  cssClass="form-control" placeholder="Enter password" name="password"/>
            </div>

            <div class="mb-3">
                <s:label for="exampleInputPassword1" cssClass="form-label" value="Confirm Password"/>
                <s:password cssClass="form-control" placeholder="Enter Confirm password" name="con_password"/>
            </div>
            <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                <s:submit cssClass="btn btn-primary" value="Register"/>
            </div>
               </s:form>
            <s:a href="login.jsp">Login</s:a> 
        </div>
        <br><br>
        
        <script>
                 $(document).ready(function()  
                    {
                    $('#logOutIdClass').addClass("d-none");
                  var message = $('#mid').html();
                  if(message.length>5){
                      $('#alertBox').removeClass("d-none");
                      setTimeout(function(){  $('#alertBox').addClass("d-none"); }, 3000);
                  }
                });
                
                </script>
    </body>
</html>
