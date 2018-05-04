<%--
  Created by IntelliJ IDEA.
  User: youwenqian
  Date: 18-4-4
  Time: 上午1:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <title>商品管理</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/login/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/login/css/default.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/login/css/styles.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/common.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/bootstrap/css/bootstrap.css">
    <!--[if IE]>
    <script src="<%=basePath%>js/html5shiv/html5shiv.min.js"></script>
    <![endif]-->
    <!-- layui -->
    <link rel="stylesheet" href="<%=basePath%>js/layui/css/layui.css"  media="all">
    <link rel="stylesheet" href="<%=basePath%>css/common.css"  media="all">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script src="<%=basePath%>js/layui/layui.js" charset="utf-8"></script>
    <script src="<%=basePath%>js/echarts.min.js"  charset="utf-8"></script>
</head>
<body class="layui-layout-body">
    <div class="layui-body" style="left:0px;background-color: #f1f1f1">
        <!-- 内容主体区域 -->
        <div class="row" style="height: 50%;padding-left:25px;padding-top:10px;">
            <div id="outStore" class="col-lg-8" style="height:100%;border:1px solid; border-color: #cccccc;border-radius:10px; background-color: #ffffff;"></div>
            <div class="col-lg-4 flex-container"  style="height: 100%">
                <div id="inStoreNum" style="margin-right: 10px;padding:10px; height:100%; background-color: #ffffff; border:1px solid; border-color: #cccccc;border-radius:10px;">
                   <div class="col-sm-6">
                       <span style="font-family: initial;font-size:40px;">入库金额</span><br>
                       <span id="intoStorePrice" style="font-family: serif;font-size:  40px;font-style: italic;">￥1200</span>
                   </div>
                    <div class="col-sm-6">
                        <img src="<%=basePath%>image/instore1.jpg" style="width:auto;height: auto;">
                    </div>
                </div>
                <div id="outStoreNUm" style="margin-top: 10px;margin-right: 10px;padding: 10px;height:100%; background-color: #ffffff; border:1px solid; border-color: #cccccc;border-radius:10px; ">
                    <div class="col-sm-6">
                        <span style="font-family: initial;font-size:40px;">出库金额</span><br>
                        <span id="outStorePrice" style="font-family: serif;font-size:  40px;font-style: italic;">￥1200</span>
                    </div>
                    <div class="col-sm-6">
                        <img src="<%=basePath%>image/outstore1.jpg" style="width:auto;height: auto;">
                    </div>
                </div>
            </div>
        </div>
        <div class="row" style="height: 50%; background-color: #f1f1f1;padding-left:25px;padding-top:10px;">
            <div id="openMenuNum"class="col-lg-8"  style="padding: 10px;height:100%;background-color: #ffffff;border: 1px solid;border-color: #cccccc;border-radius:  10px; "></div>
            <div class="col-lg-4">
                <div id="inStore" style="margin-right: 10px;padding:10px;height:100%;background-color: #ffffff;border: 1px solid;border-color: #cccccc;border-radius:  10px; "></div>
            </div>
        </div>
    </div>
    <script  type="text/javascript">
        //JavaScript代码区域
        layui.use('element', function(){
            var element = layui.element;

        });
        $.ajax({
            type: "POST",
            url: "/shoes/stock/getStockIn",
            dataType:"json",
            async:false,
            contentType: "application/json",
            success: function (data) {
                $("#intoStorePrice").html("￥ "+data.value);
            }
        });

        $.ajax({
            type: "POST",
            url: "/shoes/stock/getStockOut",
            dataType:"json",
            async:false,
            contentType: "application/json",
            success: function (data) {
                $("#outStorePrice").html("￥ "+data.value);
            }
        });

        var dataAxis = [];
        var dataShadow = [];

        $.ajax({
            type: "POST",
            url: "/shoes/stock/getStockFlowSevenOut",
            dataType:"json",
            async:false,
            contentType: "application/json",
            success: function (data) {
                dataShadow = data.data;
                dataAxis = data.dataAxis;
            }
        });
        var myChart = echarts.init(document.getElementById("outStore"));
        option = {
            title: {
                text: '最近7天的出库金额',
                subtext: '不包括当天的出库金额'
            },
            xAxis: {
                data: dataAxis,
                axisLabel: {
                    inside: true,
                    textStyle: {
                        color: '#fff'
                    }
                },
                axisTick: {
                    show: false
                },
                axisLine: {
                    show: false
                },
                z: 10
            },
            yAxis: {
                axisLine: {
                    show: false
                },
                axisTick: {
                    show: false
                },
                axisLabel: {
                    textStyle: {
                        color: '#999'
                    }
                }
            },
            dataZoom: [
                {
                    type: 'inside'
                }
            ],
            series: [
                { // For shadow
                    type: 'bar',
                    itemStyle: {
                        normal: {
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1,
                                [
                                    {offset: 0, color: '#83bff6'},
                                    {offset: 0.5, color: '#188df0'},
                                    {offset: 1, color: '#188df0'}
                                ]
                            )
                        },
                        emphasis: {
                            color: new echarts.graphic.LinearGradient(
                                0, 0, 0, 1,
                                [
                                    {offset: 0, color: '#2378f7'},
                                    {offset: 0.7, color: '#2378f7'},
                                    {offset: 1, color: '#83bff6'}
                                ]
                            )
                        }
                    },
                    barGap:'-100%',
                    barCategoryGap:'40%',
                    data: dataShadow,
                    animation: false
                }
            ]
        };
        myChart.setOption(option);

        var inStoreAxis = [];
        var inStoreData = [];
        $.ajax({
            type: "POST",
            url: "/shoes/stock/getStockFlowSevenIn",
            dataType:"json",
            async:false,
            contentType: "application/json",
            success: function (data) {
                inStoreData = data.data;
                inStoreAxis = data.dataAxis;
            }
        });
        option2 = {
            title: {
                text: '最近7天的入库金额',
                subtext: '不包括当天的入库金额'
            },
            xAxis: {
                type: 'category',
                boundaryGap: false,
                data: inStoreAxis
            },
            yAxis: {
                type: 'value'
            },
            series: [{
                data: inStoreData,
                type: 'line'
            }]
        };
        var myChart2 = echarts.init(document.getElementById("inStore"));
        myChart2.setOption(option2);

        var kaidanAxis =[];
        var kaidanData = [];
        $.ajax({
            type: "POST",
            url: "/shoes/stock/getSevenOutBills",
            dataType:"json",
            async:false,
            contentType: "application/json",
            success: function (data) {
                kaidanData = data.data;
                kaidanAxis = data.dataAxis;
            }
        });
        option1 = {
            title : {
                text: '最近7天开单数',
                subtext: '不包括当天的开单数',
                x:'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data: kaidanAxis
            },
            series : [
                {
                    name: '访问来源',
                    type: 'pie',
                    radius : '80%',
                    center: ['50%', '50%'],
                    data:kaidanData,
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        var myChart1 = echarts.init(document.getElementById("openMenuNum"));
        myChart1.setOption(option1);
    </script>
</body>
</html>
