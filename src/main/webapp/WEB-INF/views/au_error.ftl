<#include "common.ftl" />
<script type="text/javascript">

    $(function () {
        alert("${errorMsg}");
        if("${uri}" =="/main"){
            window.location.href=ctx+"/index";//跳转至登录页面
        }else{
            window.parent.location.href=ctx+"/index";// 从子容器跳出 然后从父容器执行跳转
        }
    })
</script>