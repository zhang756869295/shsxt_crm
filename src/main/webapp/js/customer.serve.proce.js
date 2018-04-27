/**
 * Created by lp on 2018/1/16.
 */


function openCustomerServeProceDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择一条记录进行处理!");
        return ;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","只能选择一条记录进行处理!");
        return ;
    }
    $("#fm").form("load",rows[0]);
    openAddOrUpdateDlg("dlg","服务处理");
}


function addCustomerServeProce() {
    $("#fm").form("submit",{
        url:ctx+"/customerServe/saveOrUpdateCustomerServe",
        onSubmit:function (param) {
            param.state=3;
            param.serviceProcePeople=$.cookie("trueName");
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            if(data.code==200){
                $("#dg").datagrid("load");
                closeCustomerServeProceDialog();
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}


function closeCustomerServeProceDialog() {
    closeDlgData("dlg");
}

