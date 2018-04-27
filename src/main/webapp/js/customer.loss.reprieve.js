/**
 * Created by lp on 2018/1/15.
 */
$(function () {
   $("#dg").edatagrid({
       url:ctx+"/customerReprieve/queryCustomerReprieveByLossId?lossId="+$("#lossId").val()
   })
});
