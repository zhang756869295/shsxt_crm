/**
 * Created by lp on 2018/1/15.
 */
$(function () {
    $("#dlg").dialog({
        onClose:function () {
            initFormData();
        }
    })
});


function initFormData() {
    $("#fm")[0].reset();
}
function queryCustomersByParams() {
    $("#dg").datagrid("load",{
        cusName:$("#cusName").val(),
        khno:$("#khno").val(),
        fr:$("#fr").val()
    })
}

function openAddCustomerDialog() {
    openAddOrUpdateDlg("dlg","添加客户信息");
}


function  closeCustomerDialog() {
    closeDlgData("dlg");
}


function saveOrUpdateCustomer() {
    saveOrUpdateData("fm",ctx+"/customer/saveOrUpdateCustomer","dlg",queryCustomersByParams);
}

function openModifyCustomerDialog() {
    openModifyDialog("dg","fm","dlg","更新客户信息");
}

function deleteCustomer() {
    deleteData("dg",ctx+"/customer/deleteCustomersByIds",queryCustomersByParams);
}


function openCustomerOtherInfo(title,type) {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择一条记录进行更新!");
        return ;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","只能选择一条记录进行更新!");
        return ;
    }
    var cusId=rows[0].id;
    if(type==3){
        window.parent.openTab(title,ctx+"/customerOrder/index?cid="+cusId);
    }
}
