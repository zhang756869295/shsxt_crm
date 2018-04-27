function openTab(text, url, iconCls){
    if($("#tabs").tabs("exists",text)){
        $("#tabs").tabs("select",text);
    }else{
        var content="<iframe frameborder=0 scrolling='auto' style='width:100%;height:100%' src='" + url + "'></iframe>";
        $("#tabs").tabs("add",{
            title:text,
            iconCls:iconCls,
            closable:true,
            content:content
        });
    }
}

/**
 * 安全退出
 *   清除cookie
 *   跳转到登录页
 */
function  logout() {
    $.messager.confirm("来自crm","确认退出系统?",function (r) {
        if(r){
            $.removeCookie("userName");
            $.removeCookie("trueName");
            $.removeCookie("userIdStr");
            $.messager.alert("来自crm","系统将在3秒后自动退出...");
            setTimeout(function () {
                window.location.href=ctx+"/index";
            },3000);

        }
    })
}

function openPasswordModifyDialog() {
    $("#dlg").dialog("open").dialog("setTitle","修改密码");
}


/**
 * 修改密码
 */
function  modifyPassword() {
    var oldPassword=$("#oldPassword").val();
    var newPassword=$("#newPassword").val();
    var confirmPassword=$("#newPassword2").val();
    if(isEmpty(oldPassword)){
        $.messager.alert("来自crm","原始密码非空!","error");
        return;
    }
    if(isEmpty(newPassword)){
        $.messager.alert("来自crm","新密码非空!","error");
        return;
    }
    if(isEmpty(confirmPassword)){
        $.messager.alert("来自crm","确认密码非空!","error");
        return;
    }
    if(newPassword != confirmPassword){
        $.messager.alert("来自crm","两次密码输入不一致!","error");
        return;
    }

    $.ajax({
        type:"post",
        url:ctx+"/user/updateUserPassword",
        data:{
            oldPassword:oldPassword,
            newPassword:newPassword,
            confirmPassword:confirmPassword
        },
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                $.messager.alert("来自crm","密码更新成功,系统将在2秒后自动退出!","info");
                setTimeout(function () {
                    $.removeCookie("userName");
                    $.removeCookie("trueName");
                    $.removeCookie("userIdStr");
                    window.location.href=ctx+"/index";
                },2000);
            }else{
                $.messager.alert("来自crm",data.msg,"error");
            }
        }
    })

}







