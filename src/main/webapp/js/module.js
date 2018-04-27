/**
 * Created by lp on 2018/1/13.
 */

$(function () {
    $("#parentMenu").hide();

    $("#grade02").combobox({
        onChange:function(newVal) {
            // 0-根级  1-一级  2-二级菜单
            if(newVal==1||newVal==2){
                $("#parentMenu").show();
                // 加载上级菜单
                loadParentMenu(newVal-1);
            }else{
                $("#parentId02").combobox("clear");
                $("#parentMenu").hide();
            }
        }
    })


    $("#dlg").dialog({
        onClose:function () {
            initFormData();
        }
    })

});


function initFormData() {
    $("#moduleName02").val("");
    $("#optValue02").val("");
    $("#grade02").combobox("setValue","");
    $("#parentId02").combobox("setValue","");
    $("#orders").val("");
    $("#id").val("");
}

function loadParentMenu(grade) {
 /*   $.ajax({
        type:"post",
        url:ctx+"/module/queryAllParentMenusByGrade",
        data:{
            grade:grade
        },
        dataType:"json",
        success:function (data) {
            console.log(data);
        }

    })*/
   $("#parentId02").combobox({
       valueField:"id",
       textField:"moduleName",
       url:ctx+"/module/queryAllParentMenusByGrade?grade="+grade
   })

}


function formateGrade(val) {
    if(val==0){
        return "根级菜单";
    }
    if(val==1){
        return "一级菜单";
    }
    if(val==2){
        return "二级菜单";
    }

}


function queryModulesByParams() {
    $("#dg").datagrid("load",{
        moduleName:$("#moduleName").val(),
        pid:$("#pid").val(),
        grade:$("#grade").combobox("getValue"),
        optValue:$("#optValue").val()
    })
}

function openAddModuleDailog() {
    openAddOrUpdateDlg("dlg","添加模块");
}

function closeDlg() {
    closeDlgData("dlg");
}

function saveOrUpdateModule() {
    saveOrUpdateData("fm",ctx+"/module/saveOrUpdateModule","dlg",queryModulesByParams);
}

function openModifyModuleDialog() {

    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择一条记录进行更新!");
        return ;
    }
    if(rows.length>1){
        $.messager.alert("来自crm","只能选择一条记录进行更新!");
        return ;
    }

    /**
     * 更新操作
     */
    $("#fm").form("load",rows[0]);//填充表单数据  数据回显
    var grade=rows[0].grade;

    if(grade!=0){
        loadParentMenu(grade-1);
        $("#parentMenu").show();
        $("#parentId02").combobox("setValue",rows[0].parentId);
    }else{
        $("#grade02").combobox("setValue",grade);
    }

    openAddOrUpdateDlg("dlg","更新资源");

}



function deleteModule() {
    deleteData("dg",ctx+"/module/deleteModuleByMid",queryModulesByParams);
}

