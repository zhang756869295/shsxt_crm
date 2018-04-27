$(function () {
   $("#dg").edatagrid({
           url: ctx+"/cusDevPlan/queryCusDevPlansBySid?sid="+$("#saleChanceId").val(),
           saveUrl: ctx+"/cusDevPlan/saveOrUpdateCusDevPlan?sid="+$("#saleChanceId").val(),
           updateUrl:ctx+"/cusDevPlan/saveOrUpdateCusDevPlan?sid="+$("#saleChanceId").val()
    });

   // devResult 开发状态
    var devResult=$("#devResult").val();
    if(devResult==2||devResult==3){
        $("#dg").edatagrid("disableEditing");//禁用表格编辑
        $("#toolbar").hide();
    }

});


function addRow() {
    $("#dg").edatagrid("addRow");//创建一个新行
}


function saveOrUpdateCusDevPlan() {
    $("#dg").edatagrid("saveRow");//发送当前行数据到后台执行更新操作
    // 刷新可编辑表格
    $("#dg").edatagrid("load");
}


function delCusDevPlan() {
    var rows=$("#dg").edatagrid("getSelections");
    if(rows.length==0){
        $.messager.alert("来自crm","请选择待删除记录!","error");
        return;
    }

    $.messager.confirm("来自crm","确定执行删除操作?",function (r) {
        if(r){
            var ids="ids=";
            for(var i=0;i<rows.length;i++){
                if(i<=rows.length-2){
                    ids=ids+rows[i].id+"&ids=";
                }else{
                    ids=ids+rows[i].id;
                }
            }
            $.ajax({
                type:"post",
                url:ctx+"/cusDevPlan/delCusDevPlan",
                data:ids,
                dataType:"json",
                success:function (data) {
                    if(data.code==200){
                        $("#dg").edatagrid("load");
                    }else{
                        $.messager.alert("来自crm",data.msg,"error");
                    }
                }
            })
        }
    })
}


function updateSaleChanceDevResult(devResult) {
    $.ajax({
        type:"post",
        url:ctx+"/saleChance/updateSaleChanceDevResultBySid",
        data:{
            sid:$("#saleChanceId").val(),
            devResult:devResult
        },
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                $("#toolbar").hide();
                $("#dg").edatagrid("disableEditing");//禁用表格编辑
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }

    })
}