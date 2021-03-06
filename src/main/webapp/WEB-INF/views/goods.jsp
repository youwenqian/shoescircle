<%--
  Created by IntelliJ IDEA.
  User: youwenqian
  Date: 18-4-4
  Time: 上午1:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
%>
<html>
<head>
    <title>商品管理</title>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
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
    <script src="<%=basePath%>js/bootstrap/js/bootstrap.js"></script>
</head>
<body class="layui-layout-body" style="background-color: #f1f1f1;">
<form class="layui-form layui-form-pane" id="search-goods-form" style="padding: 5px;margin-top:5px;">
    <div class="layui-form-item">
        <div class="col-lg-4">
            <label class="layui-form-label">名称</label>
            <div class="layui-input-block">
                <input type="text" name="goodsName" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="col-lg-4">
            <label class="layui-form-label">品牌</label>
            <div class="layui-input-block">
                <select name="brandId" id="goodsBrandSearch" lay-filter="brandIdSearchFilter" lay-search=""
                        style="width:200px;">
                    <option value="">请选择</option>
                </select>
            </div>
        </div>
        <div class="col-lg-4">
            <button type="button" id="search-button" onclick="searchGoods()" class="btn btn-default">查找</button>
        </div>
    </div>
    <div class="layui-form-item">
        <div class="col-lg-4">
            <label class="layui-form-label">关键字</label>
            <div class="layui-input-block">
                <input type="text" name="keyword" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="col-lg-4">
            <label class="layui-form-label">分类</label>
            <div class="layui-input-block">
                <select name="goodsClass" id="goodsTypeSearch" lay-filter="goodsClassSearchFilter" lay-search=""
                        style="width:200px;">
                    <option value="">请选择</option>
                </select>
            </div>
        </div>
        <div class="col-lg-4">
            <button type="button" id="add-button" onclick="addGoods()" class="btn btn-default">添加</button>
        </div>
    </div>
</form>
<table class="layui-table" id="goodsManageTable" lay-filter="goodTable"></table>
<form id="editGoodsForm" role="form" style="display:none">
    <div class="form-group">
        <input type="text" name="id" id="idEdit" hidden>
    </div>
    <div class="form-group row" style="margin-left:0px;margin-right:0px;margin-bottom:0px"
    ">
    <label for="goodsName" class="col-sm-2 control-label">名称</label>
    <div class="col-sm-4">
        <input type="text" class="form-control" required id="goodsNameEdit" name="goodsName" placeholder=“请输入名称”>
    </div>
    <label for="goodsBrandEdit" class="col-sm-2 control-label">品牌</label>
    <div class="col-sm-4">
        <select name="brandId" id="goodsBrandEdit" required class="form-control" lay-search="">
            <option value="">请选择</option>
        </select>
    </div>
    </div>
    <div class="form-group">
        <label for="goodsClassAdd" class="col-sm-2 control-label">性别</label>
        <div class="col-sm-4" style="margin-bottom: 10px;">
            <select name="sex" id="sexEdit" required class="form-control" lay-search="">
                <option value="">请选择</option>
                <option value="0">男</option>
                <option value="1">女</option>
                <option value="2">通用</option>
            </select>
        </div>
        <label for="statusEdit" class="col-sm-2 control-label">状态</label>
        <div class="col-sm-4"  style="margin-bottom: 10px;">
            <select name="status" id="statusEdit" required class="form-control" lay-search="">
                <option value="">请选择</option>
                <option value="1">上架</option>
                <option value="0">下架</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="goodsTypeEdit" class="col-sm-2 control-label">分类</label>
        <div class="col-sm-4">
            <select name="goodsClass" id="goodsTypeEdit" required class="form-control" lay-search="">
                <option value="">请选择</option>
            </select>
        </div>
        <label for="keyword" class="col-sm-2 control-label">关键字</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="keywordEdit" required name="keyword" placeholder="商品关键字">
        </div>
    </div>
    <div class="form-group">
        <label for="price" class="col-sm-2 control-label">价格</label>
        <div class="col-sm-4">
            <input type="number" class="form-control" id="priceEdit" name="price" placeholder="商品的价格（元）">
        </div>
        <label for="remark" class="col-sm-2 control-label">备注</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="remarkEdit" name="remark" placeholder="备注信息">
        </div>
    </div>
    <div class="form-group">
        <label for="keyword" class="col-sm-2 control-label">描述</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="descEdit" name="goodsDesc" placeholder="">
        </div>
    </div>
    <div class="form-group">
        <button type="button" onclick="editGoodsBtn()" id="editGoodsButton" class="btn btn-default"
                style="float: right; margin-bottom:  15px; margin-right: 15px;">确定
        </button>
    </div>
</form>
<form id="addGoodsForm" role="form" style="display:none">
    <div class="form-group">
        <input type="text" name="id" id="idAdd" hidden>
    </div>
    <div class="form-group row" style="margin-left:0px;margin-right:0px;margin-bottom:0px">
        <label for="goodsName" class="col-sm-2 control-label">名称</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" required id="goodsNameAdd" name="goodsName" placeholder=“请输入名称”>
        </div>
        <label for="goodsBrandAdd" class="col-sm-2 control-label">品牌</label>
        <div class="col-sm-4">
            <select name="brandId" id="goodsBrandAdd" required class="form-control" lay-search="">
                <option value="">请选择</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="sexAdd" class="col-sm-2 control-label">性别</label>
        <div class="col-sm-4" style="margin-bottom: 10px;">
            <select name="sex" id="sexAdd" required class="form-control" lay-search="">
                <option value="">请选择</option>
                <option value="0">男</option>
                <option value="1">女</option>
                <option value="2">通用</option>
            </select>
        </div>
        <label for="statusAdd" class="col-sm-2 control-label">状态</label>
        <div class="col-sm-4" style="margin-bottom: 10px;">
            <select name="status" id="statusAdd" required class="form-control" lay-search="">
                <option value="">请选择</option>
                <option value="1" selected>上架</option>
                <option value="0">下架</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="goodsClassAdd" class="col-sm-2 control-label">分类</label>
        <div class="col-sm-4">
            <select name="goodsClass" id="goodsClassAdd" required class="form-control" lay-search="">
                <option value="">请选择</option>
            </select>
        </div>
        <label for="keyword" class="col-sm-2 control-label">关键字</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="keywordAdd" required name="keyword" placeholder="商品关键字">
        </div>
    </div>
    <div class="form-group">
        <label for="price" class="col-sm-2 control-label">价格</label>
        <div class="col-sm-4">
            <input type="number" class="form-control" id="priceAdd" name="price" placeholder="商品的价格（元）">
        </div>
        <label for="remark" class="col-sm-2 control-label">备注</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="remarkAdd" name="remark" placeholder="备注信息">
        </div>
    </div>
    <div class="form-group">
        <label for="keyword" class="col-sm-2 control-label">描述</label>
        <div class="col-sm-10">
            <input type="text" class="form-control" id="descAdd" name="goodsDesc" placeholder="">
        </div>
    </div>
    <div class="form-group">
        <button type="button" onclick="addGoodsBtn()" id="addGoodsButton" class="btn btn-default"
                style="float: right; margin-bottom:  15px; margin-right: 15px;">确定
        </button>
    </div>
</form>
<script>
    //JavaScript代码区域
    layui.use('element', function () {
        var element = layui.element;

    });
</script>
<script src="<%=basePath%>js/custom/dateFormat.js"></script>
<script id="timeTpl_createTime" type="text/html">
    {{#
    var date = new Date();
    date.setTime(d.createTime);
    return date.Format("yyyy-MM-dd hh:mm:ss");
    }}
</script>
<script id="timeTpl_updateTime" type="text/html">
    {{#
    var date = new Date();
    date.setTime(d.updateTime);
    return date.Format("yyyy-MM-dd hh:mm:ss");
    }}
</script>
<script type="text/html" id="operation">
    <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>

    <!-- 这里同样支持 laytpl 语法，如： -->
    {{#  if(d.auth > 2){ }}
    <a class="layui-btn layui-btn-xs" lay-event="check">审核</a>
    {{#  } }}
</script>
<script src="<%=basePath%>js/custom/mechantGoods.js"></script>
</body>
</html>
