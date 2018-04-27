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
    $("#dlg").dialog("open").dialog("setTitle","添加营销机会");
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
    $("#dlg").dialog("close");
}

function saveOrUpdateSaleChance() {
    $("#fm").form("submit",{
        url:ctx+"/saleChance/saveOrUpdateSaleChance",
        onSubmit:function(param){
            param.createMan=$.cookie("trueName");
            return $("#fm").form("validate");
        },
        success:function (data) {
            /**
             * data 为原始的json 字符串
             *   需要转换为js 对象
             */
            data=JSON.parse(data);
            if(data.code==200){
                closeDlg();
                querySaleChancesByParams();
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}

/**
 * 打开修改对话框
 */
function openModifySaleChanceDialog() {
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
    console.log(rows[0]);
    $("#fm").form("load",rows[0]);//填充表单数据  数据回显
    $("#assignMan").combobox("setValue",rows[0].trueName);
    $("#dlg").dialog("open").dialog("setTitle","更新营销机会");
}

function deleteSaleChance() {
    var rows=$("#dg").datagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请至少选中一条记录进行删除!");
        return;
    }

    // ids=1&ids=2&ids=3


    $.messager.confirm("来自crm","确定删除选中的"+rows.length+"条记录?",function (r) {
        if(r){
            var ids="ids=";
            for(var i=0;i<rows.length;i++){
                if(i<=rows.length-2){
                    ids=ids+rows[i].id+"&ids=";
                }else{
                    ids=ids+rows[i].id;
                }
            }
            console.log(ids);
            $.ajax({
                type:"post",
                url:ctx+"/saleChance/deleteSaleChanceBatch",
                data:ids,
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        // 刷新datagrid
                        querySaleChancesByParams();
                    }else{
                        $.messager.alert("来自crm",data.msg,"error");
                    }
                }
            })
        }
    })
}
