/**
 * Created by lp on 2018/1/9.
 */


function querySaleChancesByParams() {
    var customerName=$("#customerName").val();
    var devResult=$("#devResult").combobox("getValue");
    var time=$("#time").datebox("getValue");
    $("#dg").datagrid("load",{
        customerName:customerName,
        devResult:devResult,
        time:time
    })
}

function  formateDevResult(val) {
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

/**
 *
 */
function formateOp(val,row) {
    var devResult=row.devResult;
    if(devResult==0||devResult==1){
        var href="javascript:openSaleChanceInfoDialog("+'"开发机会数据"'+","+row.id+")";
        return "<a href='"+href+"'>开发</a>";
    }
    if(devResult==2||devResult==3){
        var href="javascript:openSaleChanceInfoDialog("+'"详情机会数据"'+","+row.id+")";
        return "<a href='"+href+"'>查看详情</a>";
    }
}

function openSaleChanceInfoDialog(title,id) {
    /**
     * 打开新的选项卡
     */
    window.parent.openTab(title+"_"+id,ctx+"/cusDevPlan/index?sid="+id);
}