/**
 * Created by lp on 2018/1/16.
 */


function saveCustomerServe(){
    $("#fm").form("submit",{
        url:ctx+"/customerServe/saveOrUpdateCustomerServe",
        onSubmit:function (param) {
            param.createPeople=$.cookie("trueName");// 设置创建人
            return $("#fm").form("validate");
        },
        success:function (data) {
            data=JSON.parse(data);
            if(data.code==200){
                resetValue();
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })
}


function resetValue() {
    //$("#fm").reset();
    $("#fm")[0].reset();
}