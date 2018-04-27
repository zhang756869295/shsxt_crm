/**
 * Created by lp on 2018/1/9.
 */
$(function () {
   $("#dg").datagrid({
       rowStyler:function (index,row) {
           var devResult=row.devResult;
           if(devResult==0){
               return "background-color:yellow";
           }
           if(devResult==1){
               return "background-color:orange";
           }
           if(devResult==2){
               return "background-color:green";
           }
           if(devResult==3){
               return "background-color:red";
           }
       }
   })


    $("#dlg").dialog({
        // 监听事件  监听对话框关闭
        onClose:function () {
            initFormData();
        }
    })
});


/**
 * 执行多条件查询
 */
function querySaleChancesByParams() {
   var customerName=$("#customerName").val();
   var state=$("#state").combobox("getValue");
   var devResult=$("#devResult").combobox("getValue");
   var time=$("#time").datebox("getValue");

   $("#dg").datagrid("load",{
       customerName:customerName,
       state:state,
       devResult:devResult,
       time:time
   })
}


/**
 *
 * @param val  单元格value
 * @param row  单元格所在行 行记录
 * @param index  单元格所在行 索引
 */
function formateState(val,row,index) {
    if(val==0){
        return "未分配";
    }
    if(val==1){
        return "已分配";
    }
}

function formateDevResult(val) {
    if(val==0||val==""){
        return "未开发";
    }
    if(val==1){
        return "开发中";
    }
    if(val==2){
        return "开发成功"
    }
    if(val==3){
        return "开发失败";
    }

}


function openAddSaleChacneDialog() {
    //$("#dlg").dialog("open").dialog("setTitle","添加营销机会");
    openAddOrUpdateDlg("dlg","添加营销机会");
}


function  initFormData() {
   /* $("#fm").form("clear");*/
   $("#linkMan").val("");
   $("#linkPhone").val("");
   $("#customerName02").val("");
    $("#chanceSource").val("");
    $("#cgjl").val("");
    $("#description").val("");
    $("#assignMan").combobox("setValue","");
    $("#id").val("");
}

function closeDlg() {
    //$("#dlg").dialog("close");
    closeDlgData("dlg");
}

function saveOrUpdateSaleChance() {
    saveOrUpdateData("fm",ctx+"/saleChance/saveOrUpdateSaleChance","dlg",querySaleChancesByParams);
}

/**
 * 打开修改对话框
 */
function openModifySaleChanceDialog() {
   openModifyDialog("dg","fm","dlg","更新营销机会");
    var rows=$("#dg").datagrid("getSelections");
    $("#assignMan").combobox("setValue",rows[0].trueName);
}


function deleteSaleChance() {
  deleteData("dg",ctx+"/saleChance/deleteSaleChanceBatch",querySaleChancesByParams);
}
