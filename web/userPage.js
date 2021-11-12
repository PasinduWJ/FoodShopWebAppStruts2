
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

});

function userLoad() {
    
//    const pro = new  Promise(success, error);
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
                console.log(data);
                buildOrderTable();
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
                        "<td>"+ avl  +" Only </td>" +
                        "<td>" +
                        "<i onclick= (clickAdd(" + dt.id + "," + dt.unitPrice + ","+ dt.maxOrder +"," + orders + ")) class='fas fa-plus-square' style='color:green; font-size:30px; margin:5px;'></i>" +
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
                        "<i id='btnEditDone" + dt.id + "' onclick= (clickEditDone(" + dt.id +  ","+ dt.foodId + ","+ dt.quantity+ ")) class='fas fa-check-square d-none' style='color:green; font-size:30px; margin:5px;'></i>" +
                        "<i id='btnEditClose" + dt.id + "' onclick= (clickEditClose(" + dt.id + ")) class='fas fa-window-close d-none' style='color:red; font-size:30px; margin:5px;'></i>" +
                        "</td>" +
                        "<td>" +
                        "<i onclick= (clickEdit(" + dt.id + ")) class='fas fa-pen-square' style='color:green; font-size:30px; margin:5px;'></i>" +
                        "<i onclick= (clickDelete(" + dt.id + ")) class='fas fa-trash-alt' style='color:red; font-size:30px; margin:5px;'></i>" +
                        "</td>"
                        + "</tr>"
                        );

            }else{
                $('#orderTable').append(
                        "<tr>" +
                        "<td scope='row'></td>" +
                        "<td>" + fNameGetById(dt.foodId) + "</td>" +
                        "<td>" + dt.upDateTime + "</td>" +
                        "<td> Send </td>" +
                        "<td >" + dt.quantity + "</td>" +
                        "<td></td>"+ 
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
    console.log(fId +"yyy"+ id);
    $.ajax({
        type: "GET",
        url: "orderPage/editOrder",
        data: {"userName":loger, "id": id, "foodId":fId, "quantity":oldQuantity, "newOrder": quantity},
        success: function (data) {
            message = data["message"];
            if(data["order"]){
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
            data: {"id": id,"userName":loger},
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
    if(orders < maxOrder){
        var i = $("#Q" + id + "").text();
        if(i<(maxOrder - orders)){
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
            if(id === dt.foodId){
                orders += dt.quantity ;
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

function show(color) {
    $('#alertBox').removeClass("d-none");
    $('#alertBox').addClass(color);
    $('#message').text(message);
    setTimeout(function(){  $('#alertBox').addClass("d-none"); }, 5000);
}
//
//function readCookie(name) {
//    var nameEQ = name + "=";
//    var ca = document.cookie.split(';');
//    for (var i = 0; i < ca.length; i++) {
//        var c = ca[i];
//        while (c.charAt(0) == ' ') c = c.substring(1, c.length);
//        if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length, c.length);
//    }
//    return null;
//}