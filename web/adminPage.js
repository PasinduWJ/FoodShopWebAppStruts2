var loger;
var messege;
var food = [];
var order = [];
var foodItems = [];
var allOrders = [];
var hasIn = false;

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

        if(!hasIn){

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
                    "</td>"+
                    "<td scope='row'></td>" +
                    "<td>"+
                    "<i onclick= clickNewFood() class='fas fa-plus-square' style='color:green; font-size:30px; margin:5px;'></i>" +
                    "</td>"+
                    "</tr>"
                    );
        }
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
                console.log(data);
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
                        "<td>" + dt.quantity + "</td>"+
                        "<td>"+
                        "<i onclick= (clickSend(" + dt.id + ")) class='fas fa-paper-plane' style='color:blue; font-size:30px; margin:5px;'></i>" +
                        "<i onclick= (clickDelete(" + dt.id + ")) class='fas fa-trash-alt' style='color:red; font-size:30px; margin:5px;'></i>"+
                        "</td>"
                        );

            }else{
                $('#orderTable').append(
                        "<tr>" +
                        "<td scope='row'></td>" +
                        "<td>" + fNameGetById(dt.foodId) + "</td>" +
                        "<td>" + dt.userName + "</td>" +
                        "<td>" + dt.upDateTime + "</td>" +
                        "<td>" + dt.quantity + "</td>"+
                        "<td>"+
                        "<i onclick= (clickDelete(" + dt.id + ")) class='fas fa-trash-alt' style='color:red; font-size:30px; margin:5px;'></i>"+
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
                    "<tr id='foodTavleTR"+ dt.id+"' >" +
                    "<td scope='row'></td>" +
                    
                    "<td >" +
                    "<p>"+ dt.foodName+"</p>"+
                    "<input id='inputName" + dt.id + "' class='d-none' type='text' style='width: 230px;' value='"+dt.foodName+"' >" +
                    "</td>" +
                    
                    "<td>" + 
                    "<p>"+ dt.unitPrice+"</p>"+
                    "<input id='inputuPrice" + dt.id + "' class='d-none' type='number' min=0 style='width: 100px;' value='"+dt.unitPrice+"' >" +
                    "</td>" +
                    
                    "<td>" + 
                    "<p>"+ dt.maxOrder+"</p>"+
                    "<input id='inputMaxOrder" + dt.id + "' class='d-none' type='number' min=0 style='width: 100px;' value='"+dt.maxOrder+"' >" +
                    "</td>" +

                    "<td>" + avl + " Only </td>"
                    
                    );
            if (dt.available) {
                $('#foodTable td:last').after(
                        "<td> " +
                        "<div class='form-check form-switch'>"+
                        " <input id='inputAvailable" + dt.id + "' class='form-check-input' type='checkbox' role='switch' checked>" +
                        "</div> "+
                        "</td> "
                        );
            } else {
                $('#foodTable td:last').after(
                        "<td> " +
                        "<div class='form-check form-switch'>"+
                        " <input id='inputAvailable" + dt.id + "' class='form-check-input' type='checkbox' role='switch' >" +
                        "</div> "+
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

function clickNewFood(){
    var foodName = $('#inputName').val();
    var unitPrice = $('#inputPrice').val();
    var maxOrder = $('#inputMaxOrder').val();
    if(foodName && unitPrice && maxOrder){
        
        $.ajax({
        type: "GET",
        async: true,
        url: "adminPage/addFoodItem",
        data: {"adminName":loger, "foodName":foodName, "unitPrice": unitPrice,
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

function clickEdit(id){
$("#foodTavleTR"+id).children('td').children('p').addClass('d-none');    
$("#foodTavleTR"+id).children('td').children('input').removeClass('d-none'); 
$("#btnEditClick"+id).addClass('d-none'); 
$("#btnEditDone"+id).removeClass('d-none'); 
$("#btnEditClose"+id).removeClass('d-none'); 
}

function clickEditClose(id){
$("#foodTavleTR"+id).children('td').children('p').removeClass('d-none');    
$("#foodTavleTR"+id).children('td').children('input').addClass('d-none'); 
$("#btnEditClick"+id).removeClass('d-none'); 
$("#btnEditDone"+id).addClass('d-none'); 
$("#btnEditClose"+id).addClass('d-none'); 
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

function clickEditDone(id){
     var fName = $("#inputName" + id).val();
     var uPrice = $("#inputuPrice" + id).val();
     var maxOrder = $("#inputMaxOrder" + id).val();
     var available = false;
     if($("#inputAvailable"+id).is(':checked')){
         available = true;
     }
    $.ajax({
        type: "GET",
        async: true,
        url: "adminPage/editFoodItem",
        data: {"adminName":loger, "id": id,"foodName":fName, "unitPrice": uPrice,
            "maxOrder": maxOrder,"available":available},
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
            data: {"id": id,"userName":loger},
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
            data: {"id": id,"adminName":loger},
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


function clickSend(id) {
    if (confirm("Are You Sure Send This Order?")) {
        $.ajax({
            type: "GET",
            async: true,
            url: "orderPage/sendOrder",
            data: {"id": id,"userName":loger},
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
    setTimeout(function(){  $('#alertBox').addClass("d-none"); }, 5000);
}
