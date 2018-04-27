/**
 * Created by lp on 2018/1/12.
 */
var zTreeObj;
$(function () {
    $("#dlg").dialog({
        onClose:function () {
            /**
             * 清空表单数据
             */
            initFormData();
        }
    });



});
function queryRolesByParams() {
    $("#dg").datagrid("load",{
        roleName:$("#roleName").val(),
        time:$("#time").datebox("getValue")
    })
}

function  initFormData() {
    $("#roleName02").val("");
    $("#roleRemark").val("");
    $("#id").val("");
}


function  openAddRoleDailog() {
    openAddOrUpdateDlg("dlg","添加角色");
}

function  saveOrUpdateRole() {
    saveOrUpdateData("fm",ctx+"/role/saveOrUpdateRole","dlg",queryRolesByParams);
}

function openModifyRoleDialog() {
    openModifyDialog("dg","fm","dlg","修改角色信息");
}

function deleteRole() {
    deleteData("dg",ctx+"/role/deleteRolesBatch",queryRolesByParams);
}

function openRelationPermissionDialog() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择一条角色记录进行授权!");
        return ;
    }

    if(rows.length>1){
        $.messager.alert("来自crm","只能选择一条角色记录进行授权!");
        return ;
    }
    loadModuleInfo(rows[0].id);
    $("#roleId").val(rows[0].id);
    openAddOrUpdateDlg("permissionDlg","权限分配");

}




function loadModuleInfo(rid) {
    $.ajax({
        type:"post",
        url:ctx+"/module/queryAllModules",
        data:{
            rid:rid
        },
        dataType:"json",
        success:function (data) {

            // zTree 的参数配置，深入使用请参考 API 文档（setting 配置详解）
            var setting = {
                data: {
                    simpleData: {
                        enable: true
                    }
                },
                view:{
                    showLine: false
                    // showIcon: false
                },
                check: {
                    enable: true,
                    chkboxType: { "Y": "ps", "N": "ps" }
                },
                callback: {
                    onCheck: zTreeOnCheck
                }
            };
            var zNodes =data;
            zTreeObj=$.fn.zTree.init($("#treeDemo"), setting, zNodes);
        }
    })

}



function zTreeOnCheck(event, treeId, treeNode) {
    var nodes= zTreeObj.getCheckedNodes(true);
    $("#moduleIds").val("");
    if(nodes.length>0){
        var ids="moduleIds=";
        for(var i=0;i<nodes.length;i++){
           var node= nodes[i];
           if(i<=nodes.length-2){
                ids=ids+node.id+"&moduleIds=";
           }else{
               ids=ids+node.id;
            }
        }
        $("#moduleIds").val(ids);
    }
}


/**
 * 执行授权
 */
function doGrant() {
    $.ajax({
        type:"post",
        url:ctx+"/role/addGrant",
        data:"rid="+$("#roleId").val()+"&"+$("#moduleIds").val(),
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                /**
                 * 清空隐藏域值
                 */
                $("#roleId").val("");
                $("#moduleIds").val("");
                //关闭对话框
                closePermissionDlg();
                $.messager.alert("来自crm","角色授权成功!");

            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })

}

function closePermissionDlg() {
    closeDlgData("permissionDlg");
}
