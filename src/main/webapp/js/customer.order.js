/**
 * Created by lp on 2018/1/15.
 */

function formateState(val) {
    if(val==1){
        return "已支付";
    }else{
        return "待支付";
    }
}

function formateOp(val,row) {
    var orderId=row.id;
    var href="javascript:openOrderDeatilDialog("+orderId+")";
    return "<a href="+href+">查看详情</a>";
}

function openOrderDeatilDialog(orderId) {
    $.ajax({
        type:"post",
        url:ctx+"/customerOrder/queryOrderDeatilsByOrderId",
        data:{
            orderId:orderId
        },
        dataType:"json",
        success:function(data){
            console.log(data);
            $("#fm").form("load",data);
            $("#dlg").dialog("open");
        }
    })

    $("#dg2").datagrid("load",{
        orderId:orderId
    })

}