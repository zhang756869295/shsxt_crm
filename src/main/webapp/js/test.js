$(function () {
    $('#tt').edatagrid({
            url: ctx+'/data.json',
            saveUrl:"",
            updateUrl: "",
            destroyUrl: ""
});
});


function addCusDevPlan() {
    $("#tt").edatagrid("addRow");
}

function delCusDevPlan() {
    $("#tt").edatagrid("destroyRow");
}


function saveCusDevPlan() {
    alert("保存计划数据");
}