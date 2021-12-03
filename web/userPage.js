var loger;
var messege;
var food = [];
var order = [];
var foodAdd = [];
var allOrders = [];

$(document).ready(function () {
    $('#logOutId').click(function () {
        sessionStorage.clear();
        $(location).attr('href', 'login.jsp');
    });
    $('#addOder').click(function () {
        $('#addForm').removeClass("d-none");
        $.ajax({
            type: "get",
            url: "orderPage/getOrders",
            data: {"userName": loger},
            success: function (data) {
                foodAdd = data["food"];
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

    $('#oderNow').click(function () {
        if (food) {
            var sendOder = [];
            food.forEach(function (dt) {
                if (($("#Q" + dt.id + "").text()) > 0) {
                    item = {};
                    item["foodId"] = dt.id;
                    item["quantity"] = $("#Q" + dt.id + "").text();
                    sendOder.push(item);
                }
            });
            $.ajax({
                type: "POST",
                async: true,
                url: "orderPage/addNewOrder",
                data: {"userName": loger, "newOrder": JSON.stringify(sendOder)},
                success: function (data) {
                    userLoad();
                    message = data["message"];
                    show("alert-info");
                    $('#totalPrice').html("0.00");
                    $('#addForm').addClass("d-none");
                },
                error: function (request, error) {
                    alert(" Can't do because: " + error);
                }
            });
        }
    });

    $('#editUserId').click(function () {
        $('#exampleModalLabel').html("View My Profile");
        clickEditUserProfile();
    });

});

function userLoad() {
    $.ajax({
        type: "GET",
        async: false,
        url: "userPage/getUser",
        data: {},
        success: function (data) {
            loger = data["userName"];
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
            url: "userPage/getFoodItems",
            data: {"userName": loger},
            success: function (data) {
                food = data["food"];
                order = data["order"];
                $("#gridtable").jqGrid("setGridParam", {datatype: 'json'}).trigger('reloadGrid');
//                buildOrderTable();
                totalBill();
            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });
    }
}

function buildFoodTable() {
    $('#foodTable').empty();
    if (foodAdd) {
        foodAdd.forEach(function (dt) {
            var orders = getCurrentOrdersId(dt.id);
            var avl = dt.maxOrder - orders;
            if (dt.available.toString() === 'true') {
                $('#foodTable').append(
                        "<tr>" +
                        "<td scope='row'></td>" +
                        "<td>" + dt.foodName + "</td>" +
                        "<td>" + dt.unitPrice + "</td>" +
                        "<td id=Q" + dt.id + ">0</td>" +
                        "<td>" + avl + " Only </td>" +
                        "<td>" +
                        "<i onclick= (clickAdd(" + dt.id + "," + dt.unitPrice + "," + dt.maxOrder + "," + orders + ")) class='fas fa-plus-square' style='color:green; font-size:30px; margin:5px;'></i>" +
                        "<i onclick= (clickRemove(" + dt.id + "," + dt.unitPrice + ")) class='fas fa-minus-square' style='color:red; font-size:30px; margin:5px;'></i>" +
                        "</td>" +
                        "</tr>"
                        );
            } else {
                $('#foodTable').append(
                        "<tr>" +
                        "<td scope='row'></td>" +
                        "<td>" + dt.foodName + "</td>" +
                        "<td>" + dt.unitPrice + "</td>" +
                        "<td>Not Awailable Today </td>" +
                        "<td></td>" +
                        "</td>"
                        );
            }
        });
    }
}
function buildOrderTable() {
    $('#orderTable').empty();
    order.forEach(function (dt) {
        if (dt.delivery.toString() === 'false') {
            $('#orderTable').append(
                    "<tr>" +
                    "<td scope='row'></td>" +
                    "<td>" + fNameGetById(dt.foodId) + "</td>" +
                    "<td>" + dt.upDateTime + "</td>" +
                    "<td> Wait </td>" +
                    "<td >" +
                    "<span id='edQ" + dt.id + "'> " + dt.quantity + "</span> " +
                    "<input id='inputEdit" + dt.id + "' class='d-none' type='number' min='0' style='width: 70px;' value=" + dt.quantity + ">" +
                    "<i id='btnEditDone" + dt.id + "' onclick= (clickEditDone(" + dt.id + "," + dt.foodId + "," + dt.quantity + ")) class='fas fa-check-square d-none' style='color:green; font-size:30px; margin:5px;'></i>" +
                    "<i id='btnEditClose" + dt.id + "' onclick= (clickEditClose(" + dt.id + ")) class='fas fa-window-close d-none' style='color:red; font-size:30px; margin:5px;'></i>" +
                    "</td>" +
                    "<td>" +
                    "<i onclick= (clickEdit(" + dt.id + ")) class='fas fa-pen-square' style='color:green; font-size:30px; margin:5px;'></i>" +
                    "<i onclick= (clickDelete(" + dt.id + ")) class='fas fa-trash-alt' style='color:red; font-size:30px; margin:5px;'></i>" +
                    "</td>"
                    + "</tr>"
                    );
        } else {
            $('#orderTable').append(
                    "<tr>" +
                    "<td scope='row'></td>" +
                    "<td>" + fNameGetById(dt.foodId) + "</td>" +
                    "<td>" + dt.upDateTime + "</td>" +
                    "<td> Send </td>" +
                    "<td >" + dt.quantity + "</td>" +
                    "<td><i class='fas fa-thumbs-up' style='color:gray; font-size:30px; margin:5px;'>  </i></td>" +
                    "</tr>"
                    );
        }
    }
    );
}

function clickEditDone(id, fId, oldQuantity) {
    $("#inputEdit" + id).addClass("d-none");
    $("#btnEditDone" + id).addClass("d-none");
    $("#btnEditClose" + id).addClass("d-none");
    $("#edQ" + id).removeClass("d-none");

    var quantity = $("#inputEdit" + id).val();
    $.ajax({
        type: "GET",
        url: "orderPage/editOrder",
        data: {"userName": loger, "id": id, "foodId": fId, "quantity": oldQuantity, "newOrder": quantity},
        success: function (data) {
            message = data["message"];
            if (data["order"]) {
                order = data["order"];
                buildOrderTable();
                totalBill();
            }
            show("alert-warning");
        },
        error: function (request, error) {
            console.log(arguments);
            alert(" Can't do because: " + error);
        }
    });
}

function clickEdit(id) {
    $("#inputEdit" + id).removeClass("d-none");
    $("#btnEditDone" + id).removeClass("d-none");
    $("#btnEditClose" + id).removeClass("d-none");
    $("#edQ" + id).addClass("d-none");
}

function clickEditClose(id) {
    $("#inputEdit" + id).addClass("d-none");
    $("#btnEditDone" + id).addClass("d-none");
    $("#btnEditClose" + id).addClass("d-none");
    $("#edQ" + id).removeClass("d-none");
}

function clickDelete(id) {
    if (confirm("Are You Sure Delete Order?")) {
        $.ajax({
            type: "GET",
            async: true,
            url: "orderPage/deleteOrder",
            data: {"id": id, "userName": loger},
            success: function (data) {
                message = data["message"];
                order = data["order"];
                buildOrderTable();
                totalBill();
                show("alert-warning");
            },
            error: function (request, error) {
                console.log(arguments);
                alert(" Can't do because: " + error);
            }
        });
    }
}

function clickAdd(id, uPrice, maxOrder, orders) {
    if (orders < maxOrder) {
        var i = $("#Q" + id + "").text();
        if (i < (maxOrder - orders)) {
            i++;
            $("#Q" + id + "").html(i);
            var tPrice = parseFloat($('#totalPrice').text());
            tPrice += uPrice;
            $('#totalPrice').html(tPrice.toFixed(2));
        }
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

function clickRemove(id, uPrice) {
    var i = $("#Q" + id + "").text();
    if (i > 0) {
        i--;
        $("#Q" + id + "").html(i);
        var tPrice = parseFloat($('#totalPrice').text());
        tPrice -= uPrice;
        $('#totalPrice').html(tPrice.toFixed(2));
    }
}

function totalBill() {
    var tBill = 0;
    if (order) {
        order.forEach(function (dt) {
            tBill += (dt.quantity * uPriceGetById(dt.foodId));
        });
    }
    $('#totalBill').html(tBill.toFixed(2));
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

function uPriceGetById(idd) {
    var uPrice = 0;
    food.forEach(function (dt) {
        if (parseInt(idd) === dt.id) {
            uPrice = dt.unitPrice;
        }
    });
    return uPrice;
}

function clickEditUserProfile() {
    $.ajax({
        type: "GET",
        async: true,
        url: "userPage/getCustomerDetails",
        data: {"userName": loger},
        success: function (data) {
            var customer = data["customer"];
            if (customer) {
                $("#exampleModal1").modal('show');
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
                customer.forEach(function (dt) {
                    $('.modal-body tbody:last').append(
                            "<tr id='userDetails" + dt.userName + "'><td scope='row'></td>" +
                            "<td>" +
                            "<h5>" + dt.userName + "</h5>" +
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
                            "</td></tr>"
                            );
                });
                $('.modal-body tbody:last').after(
                        "</tbody></table>");
            } else {
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
    var uName = userName;
    var fName = $("#userDetails" + userName).children('td').children("#inputfName").val();
    var lName = $("#userDetails" + userName).children('td').children("#inputlName").val();
    var phNumber = $("#userDetails" + userName).children('td').children("#inputNum").val();
    $.ajax({
        type: "GET",
        async: true,
        url: "userPage/editUserDetails",
        data: {"updateBy": loger, "usName": userName, "userName": uName, "firstName": fName, "lastName": lName,
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

function sendO(cellValue, options, rowObject) {
    if (!cellValue) {
        return 'Wait';
    }
    return 'Send';
}

function afCom(response, postdata, formid) {
    var res = $.parseJSON(response.responseText);
    message = res.message;
    userLoad();
    show("alert-info");
}

function show(color) {
    $('#alertBox').removeClass("d-none");
    $('#alertBox').addClass(color);
    $('#message').text(message);
    setTimeout(function () {
        $('#alertBox').addClass("d-none");
    }, 5000);
}