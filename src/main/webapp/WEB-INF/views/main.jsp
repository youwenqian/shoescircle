<%--
  Created by IntelliJ IDEA.
  User: youwenqian
  Date: 18-3-6
  Time: 下午11:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<!doctype html>
<html>
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>shoes circle</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/login/css/normalize.css"/>
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/login/css/default.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/login/css/styles.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>js/bootstrap/css/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="<%=basePath%>css/common.css">
    <!--[if IE]>
    <script src="<%=basePath%>js/html5shiv/html5shiv.min.js"></script>
    <![endif]-->
    <!-- layui -->
    <link rel="stylesheet" href="<%=basePath%>js/layui/css/layui.css" media="all">
    <script src="<%=basePath%>js/jquery-1.9.1.min.js"></script>
    <script src="<%=basePath%>js/layui/layui.js" charset="utf-8"></script>
</head>
<body>
<div class="htmleaf-container">
    <div class="wrapper">
        <div class="layui-row bodyContent">
            <div id="userName" style="width: 50%; height: 40px; text-align: left; float: left">
                <span>欢迎：${userName}</span></div>
            <div style="margin-right: 10px; height: 40px; text-align: right;"><a href="#" onclick="changePassword()">修改密码</a> <a
                    href="<%=basePath%>index/logout">退出登录</a></div>
        </div>
        <div class="layui-row bodyContent" style="padding-top: 10px;">
            <ul class="layui-nav layui-bg-green">
                <li class="layui-nav-item"><a href="<%=basePath%>merchant/mechantMain?path=goods"
                                              target="_blank">商品管理</a></li>
                <li class="layui-nav-item layui-this"><a href="<%=basePath%>merchant/mechantMain?path=buyGoods"
                                                         target="_blank">调货查找</a></li>
                <li class="layui-nav-item"><a href="<%=basePath%>merchant/mechantMain?path=sellGoods" target="_blank">出库管理</a>
                </li>
                <!--<li class="layui-nav-item"><a href="<%=basePath%>merchant/mechantMain?path=score" target="_blank">商家打分</a></li>-->
                <li class="layui-nav-item"><a href="<%=basePath%>merchant/mechantMain?path=analaysisMain"
                                              target="_blank">智能分析</a></li>
            </ul>
        </div>
        <div class="layui-row bodyContent" style="margin-top: 10px;">
            <div class="layui-carousel" id="test10">
                <div carousel-item="">
                    <div><img src="<%=basePath%>image/111.jpg" style="width: 100%; height: 100%;"></div>
                    <div><img src="<%=basePath%>image/222.jpg" style="width: 100%; height: 100%;"></div>
                    <div><img src="<%=basePath%>image/333.jpg" style="width: 100%; height: 100%;"></div>
                </div>
            </div>
        </div>
    </div>
</div>
</div>
<form id="changeForm" style="display:none;">
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
<script>
    var changeLayer,layer;
    layui.use(['carousel', 'form' ], function () {
        var carousel = layui.carousel
            , form = layui.form;

        //图片轮播
        carousel.render({
            elem: '#test10'
            , width: '100%'
            , height: '500px'
            , interval: 5000
        });

    });
    function changePassword() {
        layui.use(["layer"],function () {
            layer = layui.layer;
            changeLayer = layer.open({
                type: 1,
                area:'600px',
                title: '修改密码',
                content: $('#changeForm') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
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
        $.post("<%=basePath%>index/changePassword",$("#changeForm").serialize(),function (data) {
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
