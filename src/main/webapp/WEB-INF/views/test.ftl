<html>
<head>
<#include "common.ftl" >
    <script type="text/javascript" src="${ctx}/js/test.js"></script>
</head>
<body style="margin: 1px">
<table id="tt" style="width:600px;height:200px"
       title="Editable DataGrid"
       singleSelect="true">
    <thead>
    <tr>
        <th field="itemid" width="100" editor="{type:'validatebox',options:{required:true}}">Item ID</th>
        <th field="productid" width="100" editor="text">Product ID</th>
        <th field="listprice" width="100" align="right" editor="{type:'numberbox',options:{precision:1}}">List Price</th>
        <th field="unitcost" width="100" align="right" editor="numberbox">Unit Cost</th>
        <th field="attr1" width="150" editor="text">Attribute</th>
    </tr>
    </thead>
</table>

</body>
</html>
