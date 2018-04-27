$(function () {
    $("#submit").click(function () {
        var userName=$("#userName").val();
        var userPwd=$("#userPwd").val();
        if(isEmpty(userName)){
            alert("用户名不能为空!");
            return;
        }
        if(isEmpty(userPwd)){
            alert("用户密码不能为空!");
            return;
        }
        $.ajax({
            type:"post",
            url:ctx+"/user/userLogin",
            data:{
                userName:userName,
                userPwd:userPwd
            },
            dataType:"json",
            success:function (data) {
                console.log(data);
                if(data.code==200){
                    var result=data.result;
                    $.cookie("userName",result.userName);
                    $.cookie("trueName",result.trueName);
                    $.cookie("userIdStr",result.userIdStr);
                    window.location.href=ctx+"/main";
                }else{
                    alert(data.msg);
                }
            }
        })
    })
});