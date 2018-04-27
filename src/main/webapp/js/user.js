$(function () {
    $("#dlg").dialog({
        onClose:function () {
            /**
             * 清空表单数据
             */
            initFormData();
        }
    })
});


function  initFormData() {
    $("#userName02").val("");
    $("#trueName").val("");
    $("#email02").val("");
    $("#phone02").val("");
    $("#roleIds").combobox("setValue","");
    $("#id").val("");
}

function queryUsersByParams() {
    $("#dg").datagrid("load",{
        userName:$("#userName").val(),
        email:$("#email").val(),
        phone:$("#phone").val()
    })
}

function  openAddUserDailog() {
    openAddOrUpdateDlg("dlg","添加用户");
}

function  saveOrUpdateUser() {
    saveOrUpdateData("fm",ctx+"/user/saveOrUpdateUser","dlg",queryUsersByParams);
}

function openModifyUserDialog() {
    openModifyDialog("dg","fm","dlg","修改用户信息");
}

function deleteUser() {
    deleteData("dg",ctx+"/user/deleteUserBatch",queryUsersByParams);
}


