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
        <div class="layui-row bodyContent" style="padding-top: 10px;">
            <ul class="layui-nav layui-bg-green">
                <li class="layui-nav-item"><a href="#" onclick="loginLogup('goods')">商品管理</a></li>
                <li class="layui-nav-item"><a href="#"  onclick="loginLogup('buyGoods')">调货查找</a></li>
                <li class="layui-nav-item"><a href="#" onclick="loginLogup('sellGoods')">出库管理</a></li>
                <!--<li class="layui-nav-item"><a href="#" onclick="loginLogup('score')">商家打分</a></li>-->
                <li class="layui-nav-item"><a href="#" onclick="loginLogup('analaysisMain')">智能分析</a></li>
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
<div class="_container_"  style="">
    <form class="form" autocomplete="off"  id="loginLogup" style="display:none">
        <div style="padding-top: 20px;padding-right: 50px;padding-bottom:30px;padding-left: 50px;">
            <div class="form-group">
                <input type="text" name="userName" id="loginUserNmae" class="form-control" placeholder="登陆用户名">
            </div>
            <div class="form-group">
                <input type="password" name="passWord" id="loginPassword" class="form-control" placeholder="登陆密码">
            </div>
            <div class="form-group">
                <button type="submit" class="layui-btn-sm btn btn-default" style="float:left;width:100%;" id="login-button">登陆</button>
                <div style="width: 80px; margin-top: 20px;margin-right: 10px;"><a href="<%=basePath%>index/register" target="_self">注册</a></div>
            </div>
        </div>
    </form>

</div>
<script>
    var changeLayer,layer,path;
    var flag = false;
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
    function loginLogup(path1) {
        path = path1;
        layui.use(["layer"],function () {
            layer = layui.layer;
            changeLayer = layer.open({
                type: 1,
                area:'600px',
                title: '登录/注册',
                content: $('#loginLogup') //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
            });
        });
    }
    $('#login-button').click(function (event) {
        // 取消冒泡
        event.preventDefault();
        // 设置登陆按钮单机状态
        flag = true;
        // from validate
        var userName = $("#loginUserNmae").val();
        var password = $("#loginPassword").val();
        if(userName == "" || userName == null ) {
            $("#loginUserNmae").focus();
            var that = $("#loginUserNmae");
            layer.tips("请输入用户名",that);
            return false;
        } else if(password == "" || password == null) {
            $("#loginPassword").focus();
            var that = $("#loginPassword");
            layer.tips("请输入密码",that);
            return false;
        } else {
            $("#loginUserNmae").blur();
            $("#loginPassword").blur();
        }
        // form btn disabled
        $("#login-button").attr({"disabled":"disabled"});
        // remove class shake_effect
        $("#loginLogup").removeClass('shake_effect');
        // form data serialize
        var formData = $("#loginLogup").serialize();
        // form ajax
        $.ajax({
            type:'POST',
            url:"index/login",
            data: formData,
            dataType:'json',
            success: function(data){
                if(data.flag == true){
                    $('form').fadeOut(500);
                    $('.wrapper').addClass('form-success');
                    // success
                    $('.container h1 > span').css('opacity','1');
                    $('.container h1 > span i').css('animation-play-state','running');
                    window.location.href = "merchant/mechantMain?path="+path;
                }else{
                    layer.msg(data.message,{icon:5,time:2000});
                }
            },
            error: function(data){
                // 401：服务端认证失败
                if(401 == data.status){
                    // add class shake_effect
                    $("#loginLogup").addClass('shake_effect');
                    // layer msg
                    layer.msg('用户名或密码错误，请重试。', {
                        offset: ['58%'],
                        icon: 5,
                        time: 2000 //2秒关闭（如果不配置，默认是3秒）
                    }, function(){
                        //do something
                        $("#login-button").removeAttr("disabled");
                    });
                }
            }
        });
        // 还原登陆按钮状态
        flag = false;
    });
</script>
</body>
</html>
