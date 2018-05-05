<%--
  Created by IntelliJ IDEA.
  User: youwenqian
  Date: 18-4-15
  Time: 下午2:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>shoes circle</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/login/css/normalize.css" />
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/login/css/default.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/login/css/styles.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/common.css">
    <!--[if IE]>
    <script src="<%=basePath%>js/html5shiv/html5shiv.min.js"></script>
    <![endif]-->
    <!-- layui -->
    <link rel="stylesheet" href="<%=basePath%>js/layui/css/layui.css"  media="all">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script src="<%=basePath%>js/layui/layui.js" charset="utf-8"></script>
</head>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo">shoes circle</div>
        <!-- 头部区域（可配合layui已有的水平导航） -->
        <div id = "userName" style="padding-left:20px;width: 10%; height: 40px; text-align: left; float: left; color:#999;"><span>欢迎：${userName}</span></div>
        <div style="height: 100%" id="notes" >

        </div>
        <div class="layui-nav layui-layout-right" ><a style="padding-right:30px; color:#999;" href="#" onclick="changePassword()">修改密码</a> <a style=" color:#999;" href="<%=basePath%>index/logout">退出登录</a></div>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree"  lay-filter="test">
                <li class="layui-nav-item"><a id="goods" href="<%=basePath%>merchant/goods" target="content">商品管理</a></li>
                <li class="layui-nav-item"><a id="buyGoods" href="<%=basePath%>merchant/buyGoods" target="content">求购商品</a></li>
                <li class="layui-nav-item"><a id="sellGoods" href="<%=basePath%>merchant/sellGoods" target="content">库存管理</a></li>
                <!--<li class="layui-nav-item"><a id="score" href="<%=basePath%>merchant/score" target="content">商家打分</a></li> -->
                <li class="layui-nav-item"><a id="analaysisMain" href="<%=basePath%>merchant/mechantAnalysis" target="content">智能分析</a></li>
            </ul>
        </div>
    </div>

    <div class="layui-body" style="background-color: #f1f1f1">
        <iframe name="content" style="width:100%;height:-webkit-fill-available;height:100%;" ></iframe>
    </div>
</div>
<form id="changePwdForm" style="display:none;">
    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <div class="layui-input-block">
                <input type="text" name="id" autocomplete="off" value="${userId}" hidden  >
            </div>
        </div>
        <div class="col-sm-2"></div>
    </div>
    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <label class="layui-form-label">原密码</label>
            <div class="layui-input-block">
                <input type="password" id="changePasswordId" name="password" autocomplete="off" class="layui-input" placeholder="原登录密码">
            </div>
        </div>
        <div class="col-sm-2"></div>
    </div>
    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <label class="layui-form-label">新密码</label>
            <div class="layui-input-block">
                <input type="password" name="newPassword" id="changeNewPassword" autocomplete="off" class="layui-input" placeholder="新登录密码">
            </div>
        </div>
        <div class="col-sm-2"></div>
    </div>
    <div class="row">
        <div class="col-sm-2"></div>
        <div class="col-sm-8">
            <div class="layui-input-block">
                <button type="button" onclick="changePwdBtn()" class="btn btn-default" style="float: right; margin-bottom:  15px; margin-right: 15px;">确定</button>
            </div>
        </div>
        <div class="col-sm-2"></div>
    </div>
</form>
<script type="text/javascript">
    var changeLayer,layer;
    var orders = [],orderDivs=[];
    var queryInterval,showInterval;
    $(document).ready(function(){
        var path = "<%=request.getParameter("path")%>";
        console.log(path);
        document.getElementById(path).click();
    });
    function queryOrders() {
        $.ajax({
            type: "POST",
            url: "/shoes/order/getOrders",
            dataType:"json",
            async:false,
            contentType: "application/json",
            success: function (data) {
                orders = data.data;
                console.log(1);
                for(var i=0;i<orders.length;i++){
                    var html1 = "<div orderId='"+orders[i].id+"' style='display:none;height: 100%;color:#999;text-align: center;cursor:pointer;' onclick='dealBill(this)'>"+orders[i].userName+" 用户求购 "+orders[i].goodsName+" 尺码为:"+orders[i].sizeName+ ",颜色为:"+orders[i].colorName+",数量为："+orders[i].qty+",备注:"+orders[i].remark+"</div>"
                    $("#notes").append(html1);
                }
                orderDivs = $("div[orderId]");
            }
        });
    }
    queryInterval = setInterval(queryOrders(), 1000*60*15);//15分钟查询一次，没处理的订单
    var showIndex = 0;
    showInterval = setInterval(function () {
        console.log(3);
        if(orderDivs.length>0){
            if(showIndex==0){
                $(orderDivs[showIndex]).fadeIn();
            }else if(showIndex==orderDivs.length-1){
                $(orderDivs[showIndex-1]).fadeOut();
                $(orderDivs[showIndex]).fadeIn();
                showIndex= 0;
            }else{
                $(orderDivs[showIndex-1]).fadeOut();
                $(orderDivs[showIndex]).fadeIn();
                showIndex= showIndex+1;
            }
            if(showIndex<orderDivs.length-1){
                showIndex= showIndex+1;
            }
            console.log(2);
        }
    },1000*60);//1分钟淡入淡出一次
    function dealBill(orderDiv){
        $.ajax({
            type: "POST",
            url: "/shoes/order/updateOrder?id="+$(orderDiv).attr("orderId"),
            dataType:"json",
            async:false,
            contentType: "application/json",
            success: function (data) {
                if(data.flag){
                    layer.msg("订单处理成功，请尽快出库！", {icon: 1,time:2000});
                    $(orderDiv).remove();
                    orderDivs = $("div[orderId]");
                }else{
                    layer.msg("订单处理失败，请联系管理员！",{icon:5,time:2000});
                }
            }
        });
    }
    function changePassword() {
        layui.use(["layer"],function () {
            layer = layui.layer;
            changeLayer = layer.open({
                type: 1,
                area:'600px',
                title: '修改密码',
                content: $('#changePwdForm'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                done:function(layero){
                    layer.setTop(layero); //重点2
                    $("#changePasswordId").val("");
                    $("#changeNewPassword").val("");
                }
            });
        });
    }
    function changePwdBtn() {
        var pwd = $("#changePasswordId").val();
        var newPwd = $("#changeNewPassword").val();
        if(pwd==null||pwd==undefined){
            return false;
        }
        if(newPwd==null||newPwd==undefined){
            return false;
        }
        if(newPwd==pwd){
            layer.msg("密码没有任何变化！",{icon:5,time:2000});
            return false;
        }
        $.post("<%=basePath%>index/changePassword",$("#changePwdForm").serialize(),function (data) {
            console.log(data);
            if(data.flag){
                layer.msg("修改成功！", {icon: 1,time:2000});
            }else{
                layer.msg(data.message, {icon: 5,time:2000});
            }
            layer.close(changeLayer);
        });
    }
</script>
</body>
</html>
