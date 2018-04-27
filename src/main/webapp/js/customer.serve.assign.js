/**
 * Created by lp on 2018/1/16.
 */


function openCustomerServeAssignDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择一条记录进行分配!");
        return ;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","只能选择一条记录进行分配!");
        return ;
    }
    $("#fm").form("load",rows[0]);
    openAddOrUpdateDlg("dlg","服务分配");
}


function addCustomerServeAssign() {
    $("#fm").form("submit",{
        url:ctx+"/customerServe/saveOrUpdateCustomerServe",
        onSubmit:function (param) {
            param.state=2;
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            if(data.code==200){
                $("#dg").datagrid("load");
                closeDlgData("dlg");
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}
