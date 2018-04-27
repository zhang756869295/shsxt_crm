<html>
<head>
<#include "common.ftl" >
<script type="text/javascript" src="${ctx}/js/base.js"></script>
<script type="text/javascript" src="${ctx}/js/sale.chance02.js"></script>
</head>
<body style="margin: 1px">
<table id="dg"  class="easyui-datagrid"
        pagination="true" rownumbers="true"
       url="${ctx}/saleChance/querySaleChancesByParams" fit="true" toolbar="#tb">
    <thead>
    <tr>
        <th field="cb" checkbox="true" align="center"></th>
        <th field="id" width="50" align="center">编号</th>
        <th field="chanceSource" width="200" align="center" >机会来源</th>
        <th field="customerName" width="50" align="center">客户名称</th>
        <th field="cgjl" width="50" align="center" >成功几率</th>
        <th field="overview" width="200" align="center">概要</th>
        <th field="linkMan" width="100" align="center">联系人</th>
        <th field="linkPhone" width="100" align="center">联系电话</th>
        <th field="description" width="200" align="center" >机会描述</th>
        <th field="createMan" width="100" align="center">创建人</th>
        <th field="createDate" width="100" align="center">创建时间</th>
        <th field="trueName" width="200" align="center" >指派人</th>
        <th field="assignTime" width="200" align="center" >指派时间</th>
        <th field="state" width="100" align="center" formatter="formateState">分配状态</th>
        <th field="devResult" width="200" align="center" formatter="formateDevResult">客户开发状态</th>
    </tr>
    </thead>
</table>
<div id="tb">
    <#if permissions?seq_contains("101002")>
        <a href="javascript:openAddSaleChacneDialog()" class="easyui-linkbutton" iconCls="icon-save" plain="true">添加</a>
        <a href="javascript:openModifySaleChanceDialog()" class="easyui-linkbutton" iconCls="icon-edit" plain="true">更新</a>
    </#if>
     <#if permissions?seq_contains("101003")>
         <a href="javascript:deleteSaleChance()" class="easyui-linkbutton" iconCls="icon-remove" plain="true">删除</a>
     </#if>
    <br/>
    客户名称:<input type="text" id="customerName"/>
    状态:
    <select  class="easyui-combobox" name="state" id="state"  panelHeight="auto">
        <option value="">全部</option>
        <option value="0">未分配</option>
        <option value="1">已分配</option>
    </select>

    开发结果:
    <select  class="easyui-combobox"  id="devResult"  panelHeight="auto">
        <option value="">全部</option>
        <option value="0">未开发</option>
        <option value="1">开发中</option>
        <option value="2">开发成功</option>
        <option value="3">开发失败</option>
    </select>

    创建时间:<input id="time" type="text" class="easyui-datebox" ></input>
    <a href="javascript:querySaleChancesByParams()" class="easyui-linkbutton" iconCls="icon-search" plain="true">搜索</a>
</div>


<div id="dlg" class="easyui-dialog" title="添加营销记录" closed="true"
      style="width: 500px;height:300px" buttons="#bt">
    <form  id="fm" method="post">
        机会来源:<input type="text" id="chanceSource" name="chanceSource"/><br/><br/>
        客户名称:<input type="text" id="customerName02" class="easyui-validatebox" name="customerName"  required="required"/><br/><br/>
        成功几率:<input type="text" id="cgjl" name="cgjl"/><br/><br/>
        联系人:<input type="text" name="linkMan"  id="linkMan" class="easyui-validatebox" required="required"/><br/><br/>
        联系电话:<input type="text" name="linkPhone" id="linkPhone"  class="easyui-validatebox" required="required"/><br/><br/>
        描述信息:<input type="text" id="description" name="description"/><br/><br/>
        分配人:<input class="easyui-combobox" id="assignMan" name="assignMan"
                   valueField="id" textField="trueName"
                   url="${ctx}/user/queryCustomerManagers" panelHeight="auto"/><br/><br/>
        <input name="id" id="id" type="hidden"/>
    </form>
</div>

<div id="bt">
     <a href="javascript:saveOrUpdateSaleChance()" class="easyui-linkbutton" plain="true" iconCls="icon-save">保存</a>
    <a href="javascript:closeDlg()" class="easyui-linkbutton" plain="true" iconCls="icon-cancel">取消</a>
</div>



</body>
</html>
