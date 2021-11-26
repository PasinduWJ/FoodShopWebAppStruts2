var loger;
var messege;
var food = [];
var order = [];
var foodItems = [];
var allOrders = [];
var hasIn = false;
//var myModal;

$(document).ready(function () {

    $('#logOutId').click(function () {
        sessionStorage.clear();
        $(location).attr('href', 'login.jsp');
    });

    $('#todayItems').click(function () {
        $('#addForm').removeClass("d-none");
        $.ajax({
            type: "GET",
            async: true,
            url: "adminPage/getFoodItems",
            data: {"adminName": loger},
            success: function (data) {
                foodItems = data["food"];
                allOrders = data["allOrders"];
                buildFoodTable();
            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });
    });
    $('#order-can-btn').click(function () {
        $('#addForm').addClass("d-none");
    });



    $('#add-newItem-btn').click(function () {

        if (!hasIn) {

            hasIn = true;
            $('#foodTable').append(
                    "<tr id='newRowAdd' >" +
                    "<td scope='row'></td>" +
                    "<td >" +
                    "<input id='inputName' type='text' style='width: 230px;' >" +
                    "</td>" +
                    "<td>" +
                    "<input id='inputPrice' type='number' min=0 style='width: 100px;' >" +
                    "</td>" +
                    "<td>" +
                    "<input id='inputMaxOrder' type='number' min=0 style='width: 100px;' >" +
                    "</td>" +
                    "<td scope='row'></td>" +
                    "<td>" +
                    "<i onclick= clickNewFood() class='fas fa-plus-square' style='color:green; font-size:30px; margin:5px;'></i>" +
                    "</td>" +
                    "</tr>"
                    );
        }
    });

    $('#viewCusId').click(function () {
        $('#exampleModalLabel').html("All Customers");
        $.ajax({
            type: "GET",
            async: true,
            url: "userPage/getAllCustomers",
            data: {"adminName": loger},
            success: function (data) {
                var allCustomers = data["allCustomers"];
                if (allCustomers) {
                    AllCustomersTable(allCustomers);
                }else{
                    message = "Data Not Found";
                    show("alert-warning");
                }
            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });
    });

    $('#viewAdminId').click(function () {
        $('#exampleModalLabel').html("All Admin");
        $.ajax({
            type: "GET",
            async: true,
            url: "adminPage/getAllAdmin",
            data: {"adminName": loger},
            success: function (data) {
                var allAdmin = data["allAdmin"];
                if (allAdmin) {
                    AllAdminTable(allAdmin);
                }else{
                    message = "Data Not Found";
                    show("alert-warning");
                }
            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });
    });
    
    $('#addNewAdminId').click(function () {
        $('#exampleModalLabel').html("Add New Admin");
        AddNewAdmin();
    });
    
    $('#addNewAdminId').click(function () {
        $('#exampleModalLabel').html("Add New Admin");
        AddNewAdmin();
    });
    

    
});


function userLoad() {

    $.ajax({
        type: "GET",
        async: false,
        url: "adminPage/getAdmin",
        data: {},
        success: function (data) {
            loger = data["adminName"];
            sessionStorage.setItem("loger", loger);
        },
        error: function (request, error) {
            console.log(arguments);
            alert(" Can't do because: " + error);
        }
    });

    if (!loger) {
        $(location).attr('href', 'login.jsp');
    } else {

        $('#logOutIdClass').removeClass("d-none");
        $('#sessUserName').html(loger);
        $.ajax({
            type: "GET",
            async: true,
            url: "adminPage/getAllOrders",
            data: {"adminName": loger},
            success: function (data) {
                food = data["food"];
                allOrders = data["allOrders"];
                buildOrderTable();
            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });
    }
}

function buildOrderTable() {
    $('#orderTable').empty();
    allOrders.forEach(function (dt) {
        if (dt.delivery.toString() === 'false') {
            $('#orderTable').append(
                    "<tr>" +
                    "<td scope='row'></td>" +
                    "<td>" + fNameGetById(dt.foodId) + "</td>" +
                    "<td>" + dt.userName + "</td>" +
                    "<td>" + dt.upDateTime + "</td>" +
                    "<td>" + dt.quantity + "</td>" +
                    "<td>" +
                    "<i onclick= (clickSend(" + dt.id + ")) class='fas fa-paper-plane' style='color:blue; font-size:30px; margin:5px;'></i>" +
                    "<i onclick= (clickViewUser('" + dt.userName + "')) class='fas fa-user' style='color:green; font-size:30px; margin:5px;'></i>" +
                    "<i onclick= (clickDelete(" + dt.id + ")) class='fas fa-trash-alt' style='color:red; font-size:30px; margin:5px;'></i>" +
                    "</td>"
                    );

        } else {
            $('#orderTable').append(
                    "<tr>" +
                    "<td scope='row'></td>" +
                    "<td>" + fNameGetById(dt.foodId) + "</td>" +
                    "<td>" + dt.userName + "</td>" +
                    "<td>" + dt.upDateTime + "</td>" +
                    "<td>" + dt.quantity + "</td>" +
                    "<td>" +
                    "<i class='fas fa-thumbs-up' style='color:gray; font-size:30px; margin:5px;'>  </i>"+
                    "<i onclick= (clickViewUser('" + dt.userName+ "')) class='fas fa-user' style='color:green; font-size:30px; margin:5px;'></i>" +
                    "<i onclick= (clickDelete(" + dt.id + ")) class='fas fa-trash-alt' style='color:red; font-size:30px; margin:5px;'></i>" +
                    "</td>"
                    );
        }
    }
    );


}

function buildFoodTable() {
    hasIn = false;
    $('#foodTable').empty();
    if (foodItems) {
        foodItems.forEach(function (dt) {
            var orders = getCurrentOrdersId(dt.id);
            var avl = dt.maxOrder - orders;

            $('#foodTable').append(
                    "<tr id='foodTavleTR" + dt.id + "' >" +
                    "<td scope='row'></td>" +
                    "<td >" +
                    "<p>" + dt.foodName + "</p>" +
                    "<input id='inputName" + dt.id + "' class='d-none' type='text' style='width: 230px;' value='" + dt.foodName + "' >" +
                    "</td>" +
                    "<td>" +
                    "<p>" + dt.unitPrice + "</p>" +
                    "<input id='inputuPrice" + dt.id + "' class='d-none' type='number' min=0 style='width: 100px;' value='" + dt.unitPrice + "' >" +
                    "</td>" +
                    "<td>" +
                    "<p>" + dt.maxOrder + "</p>" +
                    "<input id='inputMaxOrder" + dt.id + "' class='d-none' type='number' min=0 style='width: 100px;' value='" + dt.maxOrder + "' >" +
                    "</td>" +
                    "<td>" + avl + " Only </td>"

                    );
            if (dt.available) {
                $('#foodTable td:last').after(
                        "<td> " +
                        "<div class='form-check form-switch'>" +
                        " <input id='inputAvailable" + dt.id + "' class='form-check-input' type='checkbox' role='switch' checked>" +
                        "</div> " +
                        "</td> "
                        );
            } else {
                $('#foodTable td:last').after(
                        "<td> " +
                        "<div class='form-check form-switch'>" +
                        " <input id='inputAvailable" + dt.id + "' class='form-check-input' type='checkbox' role='switch' >" +
                        "</div> " +
                        "</td> "
                        );
            }
            $('#foodTable td:last').after(
                    "<td>" +
                    "<i id='btnEditDone" + dt.id + "' onclick= (clickEditDone(" + dt.id + ")) class='fas fa-check-square d-none' style='color:blue; font-size:30px; margin:5px;'></i>" +
                    "<i id='btnEditClose" + dt.id + "' onclick= (clickEditClose(" + dt.id + ")) class='fas fa-window-close d-none' style='color:maroon; font-size:30px; margin:5px;'></i>" +
                    "<i id='btnEditClick" + dt.id + "' onclick= (clickEdit(" + dt.id + ")) class='fas fa-pen-square' style='color:green; font-size:30px; margin:5px;'></i>" +
                    "<i onclick= (clickDeleteFood(" + dt.id + ")) class='fas fa-trash-alt' style='color:red; font-size:30px; margin:5px;'></i>" +
                    "</td>" +
                    "</tr>"
                    );



        });
    }
}

function clickNewFood() {
    var foodName = $('#inputName').val();
    var unitPrice = $('#inputPrice').val();
    var maxOrder = $('#inputMaxOrder').val();
    if (foodName && unitPrice && maxOrder) {

        $.ajax({
            type: "GET",
            async: true,
            url: "adminPage/addFoodItem",
            data: {"adminName": loger, "foodName": foodName, "unitPrice": unitPrice,
                "maxOrder": maxOrder},
            success: function (data) {
                foodItems = data["food"];
                allOrders = data["allOrders"];
                message = data["message"];
                buildFoodTable();
                show("alert-info");
                hasIn = false;

            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });

    }

}
function getCurrentOrdersId(id) {
    var orders = 0;
    allOrders.forEach(function (dt) {
        if (id === dt.foodId) {
            orders += dt.quantity;
        }
    });
    return orders;
}

function clickEdit(id) {
    $("#foodTavleTR" + id).children('td').children('p').addClass('d-none');
    $("#foodTavleTR" + id).children('td').children('input').removeClass('d-none');
    $("#btnEditClick" + id).addClass('d-none');
    $("#btnEditDone" + id).removeClass('d-none');
    $("#btnEditClose" + id).removeClass('d-none');
}

function clickEditClose(id) {
    $("#foodTavleTR" + id).children('td').children('p').removeClass('d-none');
    $("#foodTavleTR" + id).children('td').children('input').addClass('d-none');
    $("#btnEditClick" + id).removeClass('d-none');
    $("#btnEditDone" + id).addClass('d-none');
    $("#btnEditClose" + id).addClass('d-none');
}

function fNameGetById(idd) {
    var fName = "Not Now";
    food.forEach(function (dt) {
        if (parseInt(idd) === dt.id) {
            fName = dt.foodName;
        }
    });
    return fName;
}

function clickEditDone(id) {
    var fName = $("#inputName" + id).val();
    var uPrice = $("#inputuPrice" + id).val();
    var maxOrder = $("#inputMaxOrder" + id).val();
    var available = false;
    if ($("#inputAvailable" + id).is(':checked')) {
        available = true;
    }
    $.ajax({
        type: "GET",
        async: true,
        url: "adminPage/editFoodItem",
        data: {"adminName": loger, "id": id, "foodName": fName, "unitPrice": uPrice,
            "maxOrder": maxOrder, "available": available},
        success: function (data) {
            foodItems = data["food"];
            allOrders = data["allOrders"];
            message = data["message"];
            buildFoodTable();
            show("alert-info");

        },
        error: function (request, error) {
            console.log(arguments);
            alert(" Can't do because: " + error);
        }
    });

}

function clickDelete(id) {
    if (confirm("Are You Sure Delete Order?")) {
        $.ajax({
            type: "GET",
            async: true,
            url: "orderPage/deleteOrderAdmin",
            data: {"id": id, "userName": loger},
            success: function (data) {
                foodItems = data["food"];
                allOrders = data["allOrders"];
                message = data["message"];
                buildOrderTable();
                show("alert-warning");
            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });
    }
}

function clickDeleteFood(id) {
    if (confirm("Are You Sure Delete Food?")) {
        $.ajax({
            type: "GET",
            async: true,
            url: "adminPage/deleteFood",
            data: {"id": id, "adminName": loger},
            success: function (data) {
                foodItems = data["food"];
                allOrders = data["allOrders"];
                message = data["message"];
                buildFoodTable();
                show("alert-warning");
            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });
    }
}
function clickViewUser(uName){
    $('#exampleModalLabel').html("Customers Details");
        $.ajax({
            type: "GET",
            async: true,
            url: "userPage/getCustomerDetails",
            data: {"userName": uName},
            success: function (data) {
                var customer = data["customer"];
                if (customer) {
                    $("#exampleModal1").modal('show');
                    AllCustomersTable(customer);
                }else{
                    $("#exampleModal1").modal('hide');
                    message = "Data Not Found";
                    show("alert-warning");
                }
            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });
}

function AllCustomersTable(allCustomers) {
    $('.modal-body').empty();
    $('.modal-body').append(
            "<table class='table table-striped'> " +
            "<thead><tr><th scope='col'> </th>" +
            "<th scope='col'> User Name </th>" +
            "<th scope='col'> First Name </th>" +
            "<th scope='col'> Last Name</th>" +
            "<th scope='col'> Phone Number </th>" +
            "<th scope='col'> Action </th>" +
            "</tr></thead><tbody >");
    allCustomers.forEach(function (dt) {
        $('.modal-body tbody:last').append(
                "<tr id='userDetails"+ dt.userName +"'><td scope='row'></td>" +
                "<td>" +
                    "<p>" + dt.userName + "</p>" +
                    "<input id=inputUserName class='d-none'   style='width: 100px;' value='" + dt.userName + "' >" +
                "</td>" +
                "<td>" +
                    "<p>" + dt.firstName + "</p>" +
                    "<input id=inputfName class='d-none'   style='width: 100px;' value='" + dt.firstName + "' >" +
                "</td>" +
                "<td>" +
                    "<p>" + dt.lastName + "</p>" +
                    "<input id=inputlName class='d-none'  style='width: 100px;' value='" + dt.lastName + "' >" +
                "</td>" +
                "<td>" + 
                    "<p>" + dt.phNumber + "</p>" +
                    "<input id=inputNum class='d-none' type='number' min=0 style='width: 100px;' value=" + dt.phNumber + " >" +
                "</td> <td>" +
                "<i id=Donebtn onclick= (clickEdituserDone('" + dt.userName + "')) class='fas fa-check-square d-none' style='color:green; font-size:30px; margin:5px;'></i>" +
                "<i id=Closebtn onclick=(clickEditCloseUser('" + dt.userName + "')) class='fas fa-window-close d-none' style='color:maroon; font-size:30px; margin:5px;'></i>" +
                "<i id=EditUserbtn onclick= (clickEditUser('" + dt.userName + "')) class='fas fa-pen-square' style='color:green; font-size:30px; margin:5px;'></i>" +
                "<i onclick= (clickDeleteUser('" + dt.userName + "')) class='fas fa-trash-alt' style='color:red; font-size:30px; margin:5px;'></i>" +
                "</td></tr>"
                );
    });
    $('.modal-body tbody:last').after(
            "</tbody></table>");

}

function clickEditUser(userName) {
    $("#userDetails" + userName).children('td').children('p').addClass('d-none');
    $("#userDetails" + userName).children('td').children('input').removeClass('d-none');
    $("#userDetails" + userName).children('td').children('#EditUserbtn').addClass('d-none');
    $("#userDetails" + userName).children('td').children('#Donebtn').removeClass('d-none');
    $("#userDetails" + userName).children('td').children('#Closebtn').removeClass('d-none');
}

function clickEditCloseUser(userName) {
    $("#userDetails" + userName).children('td').children('p').removeClass('d-none');
    $("#userDetails" + userName).children('td').children('input').addClass('d-none');
    $("#userDetails" + userName).children('td').children('#EditUserbtn').removeClass('d-none');
    $("#userDetails" + userName).children('td').children('#Donebtn').addClass('d-none');
    $("#userDetails" + userName).children('td').children('#Closebtn').addClass('d-none');
}
function clickEdituserDone(userName) {
    
    var uName =  $("#userDetails" + userName).children('td').children("#inputUserName").val();
    var fName =  $("#userDetails" + userName).children('td').children("#inputfName").val();
    var lName = $("#userDetails" + userName).children('td').children("#inputlName").val();
    var phNumber = $("#userDetails" + userName).children('td').children("#inputNum").val();
    $.ajax({
        type: "GET",
        async: true,
        url: "userPage/editUserDetails",
        data: {"updateBy": loger,"usName": userName,  "userName": uName, "firstName": fName, "lastName": lName,
            "phNumber": phNumber},
        success: function (data) {
            message = data["message"];
            $("#exampleModal1").modal('hide');
            show("alert-info");

        },
        error: function (request, error) {
            console.log(arguments);
            alert(" Can't do because: " + error);
        }
    });

}

function AddNewAdmin(){
    $('.modal-body').empty();
    $('.modal-body').append(
            "<div class='mb-3'>"+  
                "<label class='form-label'> Name</label>"+
                "<input class='form-control' id='adminNameId' required>"+
                "<div id='nameHelp' class='form-text d-none'>Please fill Admin Name.</div>"+
            "</div>"+
            "<div class='mb-3'>"+  
                "<label class='form-label'> Phone Number</label>"+
                "<input class='form-control' type='number' id='phNumberId' required>"+
                "<div id='numHelp' class='form-text d-none'>Please fill Phone Number.</div>"+
            "</div>"+
            "<div class='mb-3'>"+  
                "<label class='form-label'> Password</label>"+
                "<input class='form-control' type='password' id='passwordId' required >"+
                "<div id='passHelp' class='form-text d-none'>Please fill Password.</div>"+
            "</div>"+
            "<div class='mb-3'>"+  
                "<label class='form-label'> Confirm Password</label>"+
                "<input class='form-control' type='password' id='con_passwordId' required >"+
                "<div id='conPassHelp' class='form-text d-none'>Please fill Confirm Password.</div>"+
                "<div id='conPassMatch' class='form-text d-none'>Not Same Confirm Password.</div>"+
            "</div>"+
            "<div class='mb-3'>"+  
                "<label class='form-label'> Role</label>"+
                "<input class='form-control' id='adRoleId' required>"+
                "<div id='roleHelp' class='form-text d-none'>Please fill Role.</div>"+
            "</div>"+
            "<button id='adSubBtn' type='button' class='btn btn-primary'>Submit</button>"+
            "</div>"
            );
    
        $('#adSubBtn').click(function () {
            var aName = $('#adminNameId').val();
            var phNumber = $('#phNumberId').val();
            var password = $('#passwordId').val();
            var con_password = $('#con_passwordId').val();
            var role = $('#adRoleId').val();
            
            if(!aName){
                $('#nameHelp').removeClass('d-none');
            }else{
                $('#nameHelp').addClass('d-none');
            }
            if(!phNumber){
                $('#numHelp').removeClass('d-none');
            }else{
                $('#numHelp').addClass('d-none');
            }
            if(!password){
                $('#passHelp').removeClass('d-none');
            }else{
                $('#passHelp').addClass('d-none');
            }
            if(!con_password){
                $('#conPassHelp').removeClass('d-none');
            }else{
                $('#conPassHelp').addClass('d-none');
            }
            if(!role){
                $('#roleHelp').removeClass('d-none');
            }else{
                $('#roleHelp').addClass('d-none');
            }
            if(password === con_password){
                $('#conPassMatch').addClass('d-none'); 
                if(aName !==''  && phNumber!==''  && password !==''  && con_password !==''  && role !=='' ){
                    $.ajax({
                        type: "GET",
                        async: false,
                        url: "adminPage/addNewAdmin",
                        data: {"adModel.adminName": aName, "adModel.updateBy": loger, "adModel.phNumber": phNumber, "adModel.password":password,
                                "adModel.adRole":role},
                        success: function (data) {
                            message = data["message"];
                            $("#exampleModal1").modal('hide');
                            show("alert-warning");
                        },
                        error: function (request, error) {
                            console.log(arguments);
                            alert(" Can't do because: " + error);
                        }
                    });
                }
            }else{
                $('#conPassMatch').removeClass('d-none');
            }
                
            
            
            
        });
    
}

function AllAdminTable(allAdmin) {
    $('.modal-body').empty();
    $('.modal-body').append(
            "<table class='table table-striped'> " +
            "<thead><tr><th scope='col'> </th>" +
            "<th scope='col'> Admin Name </th>" +
            "<th scope='col'> Admin Role </th>" +
            "<th scope='col'> Phone Number </th>" +
            "<th scope='col'> Action </th>" +
            "</tr></thead><tbody >");
    allAdmin.forEach(function (dt) {
        $('.modal-body tbody:last').append(
                "<tr id='adminDetails"+ dt.adminName +"'><td scope='row'></td>" +
                "<td>" +
                    "<p>" + dt.adminName + "</p>" +
                    "<input id=inputName class='d-none'   style='width: 100px;' value='" + dt.adminName + "' >" +
                "</td>" +
                "<td>" +
                    "<p>" + dt.adRole + "</p>" +
                    "<input id=inputRole class='d-none'   style='width: 100px;' value='" + dt.adRole + "' >" +
                "</td>" +
                "<td>" +
                    "<p>" + dt.phNumber + "</p>" +
                    "<input id=inputphNum class='d-none' type='number' min=0   style='width: 100px;' value='" + dt.phNumber + "' >" +
                "</td>" );
        if (dt.adRole.toString() === "main" || dt.adminName === loger) {
            $('.modal-body td:last').after(
                    "<td>"+
                    "<i id=Donebtn onclick= (clickEditadminDone('" + dt.adminName + "')) class='fas fa-check-square d-none' style='color:green; font-size:30px; margin:5px;'></i>" +
                    "<i id=Closebtn onclick=(clickEditCloseadmin('" + dt.adminName + "')) class='fas fa-window-close d-none' style='color:maroon; font-size:30px; margin:5px;'></i>" +
                    "<i id=EditAdminbtn onclick= (clickEditadmin('" + dt.adminName + "')) class='fas fa-pen-square' style='color:green; font-size:30px; margin:5px;'></i>" +
                    "<i class='fas fa-user-tie' style='color:Green; font-size:30px; margin:5px;'></i>" +
                    "</td></tr>"
                    );
        } else {
            $('.modal-body td:last').after(
                    "<td>"+
                    "<i id=Donebtn onclick= (clickEditadminDone('" + dt.adminName + "')) class='fas fa-check-square d-none' style='color:green; font-size:30px; margin:5px;'></i>" +
                    "<i id=Closebtn onclick=(clickEditCloseadmin('" + dt.adminName + "')) class='fas fa-window-close d-none' style='color:maroon; font-size:30px; margin:5px;'></i>" +
                    "<i id=EditAdminbtn onclick= (clickEditadmin('" + dt.adminName + "')) class='fas fa-pen-square' style='color:green; font-size:30px; margin:5px;'></i>" +
                    "<i onclick= (clickDeleteAdmin('" + dt.adminName + "')) class='fas fa-trash-alt' style='color:red; font-size:30px; margin:5px;'></i>" +
                    "</td></tr>"
                    );
        }
    });
    $('.modal-body tbody:last').after(
            "</tbody></table>");

}


function clickEditadmin(adminName) {
    $("#adminDetails" + adminName).children('td').children('p').addClass('d-none');
    $("#adminDetails" + adminName).children('td').children('input').removeClass('d-none');
    $("#adminDetails" + adminName).children('td').children('#EditAdminbtn').addClass('d-none');
    $("#adminDetails" + adminName).children('td').children('#Donebtn').removeClass('d-none');
    $("#adminDetails" + adminName).children('td').children('#Closebtn').removeClass('d-none');
}

function clickEditCloseadmin(adminName) {
    $("#adminDetails" + adminName).children('td').children('p').removeClass('d-none');
    $("#adminDetails" + adminName).children('td').children('input').addClass('d-none');
    $("#adminDetails" + adminName).children('td').children('#EditAdminbtn').removeClass('d-none');
    $("#adminDetails" + adminName).children('td').children('#Donebtn').addClass('d-none');
    $("#adminDetails" + adminName).children('td').children('#Closebtn').addClass('d-none');
}
function clickEditadminDone(adminName) {
    var aName =  $("#adminDetails" + adminName).children('td').children("#inputName").val();
    var role = $("#adminDetails" + adminName).children('td').children("#inputRole").val();
    var phNumber = $("#adminDetails" + adminName).children('td').children("#inputphNum").val();
    $.ajax({
        type: "GET",
        async: true,
        url: "adminPage/editAdminDetails",
        data: {"adModel.updateBy": loger, "adminName": adminName, "adModel.adminName": aName, "adModel.adRole": role,"adModel.phNumber": phNumber},
        success: function (data) {
            message = data["message"];
            $("#exampleModal1").modal('hide');
            show("alert-info");

        },
        error: function (request, error) {
            console.log(arguments);
            alert(" Can't do because: " + error);
        }
    });

}
function clickDeleteAdmin(aName) {
    if (confirm("Are You Sure Delete This Admin?")) {
        $.ajax({
            type: "GET",
            async: false,
            url: "adminPage/deleteAdmin",
            data: {"adminName": aName, "updateBy": loger},
            success: function (data) {
                message = data["message"];
                $("#exampleModal1").modal('hide');
                show("alert-warning");
            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });
    }
}

function clickDeleteUser(uName) {
    if (confirm("Are You Sure Delete This Customer?")) {
        $.ajax({
            type: "GET",
            async: false,
            url: "userPage/deleteUser",
            data: {"userName": uName, "updateBy": loger},
            success: function (data) {
                message = data["message"];
                $("#exampleModal1").modal('hide');
                show("alert-warning");
            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });
    }
}

function clickSend(id) {
    if (confirm("Are You Sure Send This Order?")) {
        $.ajax({
            type: "GET",
            async: true,
            url: "orderPage/sendOrder",
            data: {"id": id, "userName": loger},
            success: function (data) {
                foodItems = data["food"];
                allOrders = data["allOrders"];
                message = data["message"];
                buildOrderTable();
                show("alert-warning");
            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });
    }
}

function show(color) {
    $('#alertBox').removeClass("d-none");
    $('#alertBox').addClass(color);
    $('#message').text(message);
    setTimeout(function () {
        $('#alertBox').addClass("d-none");
    }, 5000);
}

