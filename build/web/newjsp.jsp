<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s" %> 
<%@ taglib prefix="sj" uri="/struts-jquery-tags" %>
<%@taglib prefix="sjg" uri="/struts-jquery-grid-tags"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3" crossorigin="anonymous">
        <script src="https://kit.fontawesome.com/7eefacbee9.js" crossorigin="anonymous"></script>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-ka7Sk0Gln4gmtz2MlQnikT1wXgYsOg+OMhuP+IlRH9sENBO0LRn5q+8nbTov4+1p" crossorigin="anonymous"></script>
        <sj:head jqueryui="true" jquerytheme="excite-bike"/>
        <title>User Page</title>
    </head>
    <body>
        <h1>Food</h1>
        <sj:tabbedpanel id="tab">
            <sj:tab id="tab1" target="one" label="ListaFood"/>
            <sj:tab/>
            <div id="one" >
                <s:url id="hello" action="user/getNew"/>
                <s:url id="edit" action="user/getedit"/>
                <sjg:grid id="gridtable"
                          caption="Product Master"
                          dataType="json"
                          href="%{hello}"
                          editurl="%{edit}"
                          pager="true"
                          gridModel="orderGridModel"
                          rowList="3,5,10"
                          rowNum="3"
                          navigator="true" 
                          navigatorSearch="false"
                          navigatorAddOptions="{height:280, reloadAfterSubmit:true}"
                          navigatorEditOptions="{height:200, width:450, reloadAfterSubmit:true, afterComplete:afCom }"
                          rownumbers="true"
                          autowidth="true">
                    <sjg:gridColumn name="order.id" title="id" hidden="true" editable="true"/>
                    <sjg:gridColumn name="foodName" index="foodName" title="Food Name" sortable="false" sortable="true"/>
                    <sjg:gridColumn name="order.upDateTime" index="order.upDateTime" title="Update Time" sortable="true"/>
                    <sjg:gridColumn name="order.quantity" index="order.quantity" width="30" align="center"  title="Quantity" sortable="true" editable="true" />
                    <sjg:gridColumn name="order.delivery" width="60" index="order.upDateTime" align="center" title="Order Send/Wait" sortable="true"  formatter="sendO"/>
                </sjg:grid>
            </div>
        </sj:tabbedpanel>


        <script>
            function sendO(cellValue, options, rowObject) {
                if (!cellValue) {
                    return 'Wait';
                }
                return 'Send';
            }

            function afCom(response, postdata, formid) {
                var res = $.parseJSON(response.responseText);
                console.log(res.message);
            }
        </script>



    </body>
</html>
