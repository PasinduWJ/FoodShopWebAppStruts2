
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %> 
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <title>Login Page</title>
    </head>
    <body onload="pageLoad()">
        <%@include file="WEB-INF/header.jspf" %>
        <br><br>

        <div class="container">
            <h2>Login page</h2>
            <br><br>

            <div class="col-5 col-md-5 alert alert-info position-absolute top-5 end-0 d-none" id="alertBox" role="alert">
                <p id="message"> ${message}</p>
            </div>

            <s:form action="loginAcc">
                <div class="form-check form-switch">
                    <s:checkbox cssClass="form-check-input" label=" Admin " role="switch" id="adminLogCheck" name="adminSwitch"/>
                </div>
                <div class="mb-3">
                    <s:textfield label="User Name "  key="userName" value="" cssClass="form-control" id="userNameid"/>
                </div>
                <div class="mb-3">
                    <s:password label="Password " key="password"  cssClass="form-control" id="password"/>
                </div>
                <div class="mb-3">
                    <s:checkbox cssClass="form-check-input" label="Remember Me? " name="remember"/>
                </div>

                <div class="d-grid gap-2 d-md-flex justify-content-md-end">
                    <s:submit cssClass="btn btn-primary"/>
                </div>
            </s:form>
            Not registered yet ,Please Sign Up
            <a href ="register.jsp">Sign Up</a>
        </div>

        <script>

            $(document).ready(function () {

                var message = $('#message').html();
                if (message.length > 5) {
                    $('#alertBox').removeClass("d-none");
                    setTimeout(function () {
                        $('#alertBox').addClass("d-none");
                    }, 3000);
                }
            });

            function pageLoad() {


                var userName = readCookie("logName");
//                     var password = readCookie("password");
                console.log(userName);
                if (userName === null) {
                    $('#logOutIdClass').addClass("d-none");
//                         $.('#userNameid').val("sd");
//                         $.('#password').val(password);

                }
            }

            function readCookie(name) {
                var nameEQ = name + "=";
                var ca = document.cookie.split(';');
                for (var i = 0; i < ca.length; i++) {
                    var c = ca[i];
                    while (c.charAt(0) == ' ')
                        c = c.substring(1, c.length);
                    if (c.indexOf(nameEQ) == 0)
                        return c.substring(nameEQ.length, c.length);
                }
                return null;
            }

        </script>
    </body>
</html>
