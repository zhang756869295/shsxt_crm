/**
 * Created by lp on 2018/1/16.
 */


function queryCustomerServesByParams() {
    $("#dg").datagrid("load",{
        cusName:$("#cusName").val(),
        myd:$("#myd").combobox("getValue"),
        time:$("#time").datebox("getValue")
    })
}
