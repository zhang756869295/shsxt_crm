/**
 * Created by lp on 2018/1/16.
 */
$(function () {
   loadCustomerGcData();
});



function loadCustomerGcData() {

    $.ajax({
        type:"post",
        url:ctx+"/customer/countCustomersByLevel",
        dataType:"json",
        success:function (data) {
            if(data.code==200){
                // 基于准备好的dom，初始化echarts实例
                var myChart = echarts.init(document.getElementById('main'));
                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: 'Crm客户构成分析'
                    },
                    tooltip: {},
                    legend: {
                        data:['客户数量']
                    },
                    xAxis: {
                        data: data.data2
                    },
                    yAxis: {},
                    series: [{
                        name: '客户数量',
                        type: 'bar',
                        data: data.data1
                    }]
                };
            // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            }else{
                alert("暂无数据!");
            }
        }
    })


}






