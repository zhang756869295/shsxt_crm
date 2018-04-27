<html>
<head>
<#include "common.ftl" >
    <script type="text/javascript" src="${ctx}/js/test.js"></script>
</head>
<body style="margin: 1px">
<table id="tt"
       singleSelect="true" toolbar="#tb" fit="true">
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


<div id="tb">
    <a href="javascript:addCusDevPlan()" class="easyui-linkbutton" iconCls="icon-save" plain="true">添加计划</a>

    <a href="javascript:saveCusDevPlan()" class="easyui-linkbutton" iconCls="icon-save" plain="true">保存计划</a>

    <a href="javascript:delCusDevPlan()" class="easyui-linkbutton" iconCls="icon-save" plain="true">删除计划</a>

</div>

</body>
</html>
