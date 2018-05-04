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
    <title>库存管理</title>
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
        <div class="col-sm-4">
            <label class="layui-form-label">名称</label>
            <div class="layui-input-block">
                <input type="text" name="goodsName" autocomplete="off" class="layui-input">
            </div>
        </div>
        <div class="col-sm-4">
            <button type="button" id="search-button" onclick="searchGoods()" class="btn btn-default">查找</button>
        </div>
        <div class="col-sm-4">
            <button type="button" id="add-button" onclick="addGoods()" class="btn btn-default">添加</button>
        </div>
    </div>
</form>
<table class="layui-table" id="goodsManageTable" lay-filter="stockTable"></table>
<form id="editGoodsForm" role="form" style="display:none">
    <div class="form-group">
        <input type="text" name="id" id="idEdit" hidden>
    </div>
    <div class="form-group" style="margin-left:0px;margin-right:0px;margin-bottom:0px">
        <label for="goodsNameEdit" class="col-sm-2 control-label">名称</label>
        <div class="col-sm-4" style="margin-bottom: 10px;">
            <select name="goodsId" id="goodsNameEdit" required class="form-control" lay-search="">
                <option value="">请选择</option>
            </select>
        </div>
        <label for="goodsSizeEdit" class="col-sm-2 control-label">尺码</label>
        <div class="col-sm-4" style="margin: 0px 0px 10px 0px;">
            <select name="sizeId" id="goodsSizeEdit" required class="form-control" lay-search="">
                <option value="">请选择</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="colorIdEdit" class="col-sm-2 control-label">颜色</label>
        <div class="col-sm-4">
            <select name="colorId" id="colorIdEdit" required class="form-control" lay-search="">
                <option value="">请选择</option>
            </select>
        </div>
        <label for="qtyEdit" class="col-sm-2 control-label">数量</label>
        <div class="col-sm-4">
            <input type="number" name="qty" id="qtyEdit" required class="form-control" lay-search="">
            </input>
        </div>
    </div>
    <div class="form-group">
        <label for="intoPriceEdit" class="col-sm-2 control-label">入库价格</label>
        <div class="col-sm-4">
            <input type="number" name="intoPrice" id="intoPriceEdit" required class="form-control" lay-search="">
            </input>
        </div>
        <label for="shareFlagEdit" class="col-sm-2 control-label">共享</label>
        <div class="col-sm-4" style="margin: 0px 0px 10px 0px;">
            <select name="shareFlag" id="shareFlagEdit" required class="form-control" lay-search="">
                <option value="">请选择</option>
                <option value="1" selected>共享</option>
                <option value="0">不共享</option>
            </select>
        </div>
    </div>
    <div class="form-group" style="float:left">
        <label for="storageTimeEdit" class="col-sm-2 control-label">入库时间</label>
        <div class="col-sm-4">
            <input name="storageTime" id="storageTimeEdit" autocomplete="off" class="layui-input" type="text">
        </div>
        <label for="storeAddressEdit" class="col-sm-2 control-label">库存地址</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="storeAddressEdit" required name="storeAddress" placeholder="">
        </div>
    </div>
    <div class="form-group">
        <label for="statusEdit" class="col-sm-2 control-label">状态</label>
        <div class="col-sm-4" style="margin: 0px 0px 10px 0px;">
            <select name="status" id="statusEdit" required class="form-control" lay-search="">
                <option value="">请选择</option>
                <option value="1" selected>在售</option>
                <option value="0">售完</option>
            </select>
        </div>
        <label for="remarkEdit" class="col-sm-2 control-label">备注</label>
        <div class="col-sm-4" style="margin: 0px 0px 10px 0px;">
            <input type="text" class="form-control" id="remarkEdit" name="remark" placeholder="备注信息">
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
    <div class="form-group" style="margin-left:0px;margin-right:0px;margin-bottom:0px">
        <label for="goodsNameAdd" class="col-sm-2 control-label">名称</label>
        <div class="col-sm-4" style="margin-bottom: 10px;">
            <select name="goodsId" id="goodsNameAdd" required class="form-control" lay-search="">
                <option value="">请选择</option>
            </select>
        </div>
        <label for="goodsSizeAdd" class="col-sm-2 control-label">尺码</label>
        <div class="col-sm-4" style="margin: 0px 0px 10px 0px;">
            <select name="sizeId" id="goodsSizeAdd" required class="form-control" lay-search="">
                <option value="">请选择</option>
            </select>
        </div>
    </div>
    <div class="form-group">
        <label for="colorIdAdd" class="col-sm-2 control-label">颜色</label>
        <div class="col-sm-4">
            <select name="colorId" id="colorIdAdd" required class="form-control" lay-search="">
                <option value="">请选择</option>
            </select>
        </div>
        <label for="qtyAdd" class="col-sm-2 control-label">数量</label>
        <div class="col-sm-4">
            <input type="number" name="qty" id="qtyAdd" required class="form-control" lay-search="">
            </input>
        </div>
    </div>
    <div class="form-group">
        <label for="intoPriceAdd" class="col-sm-2 control-label">入库价格</label>
        <div class="col-sm-4">
            <input type="number" name="intoPrice" id="intoPriceAdd" required class="form-control" lay-search="">
            </input>
        </div>
        <label for="shareFlagAdd" class="col-sm-2 control-label">共享</label>
        <div class="col-sm-4" style="margin: 0px 0px 10px 0px;">
            <select name="shareFlag" id="shareFlagAdd" required class="form-control" lay-search="">
                <option value="">请选择</option>
                <option value="1" selected>共享</option>
                <option value="0">不共享</option>
            </select>
        </div>
    </div>
    <div class="form-group" style="float:left;">
        <label for="storageTimeAdd" class="col-sm-2 control-label">入库时间</label>
        <div class="col-sm-4">
            <input name="storageTime" id="storageTimeAdd" autocomplete="off" class="layui-input" type="text">
        </div>
        <label for="storeAddressAdd" class="col-sm-2 control-label">库存地址</label>
        <div class="col-sm-4">
            <input type="text" class="form-control" id="storeAddressAdd" required name="storeAddress" placeholder="">
        </div>
    </div>
    <div class="form-group">
        <label for="statusAdd" class="col-sm-2 control-label">状态</label>
        <div class="col-sm-4" style="margin: 0px 0px 10px 0px;">
            <select name="status" id="statusAdd" required class="form-control" lay-search="">
                <option value="">请选择</option>
                <option value="1" selected>在售</option>
                <option value="0">售完</option>
            </select>
        </div>
        <label for="remarkAdd" class="col-sm-2 control-label">备注</label>
        <div class="col-sm-4" style="margin: 0px 0px 10px 0px;">
            <input type="text" class="form-control" id="remarkAdd" name="remark" placeholder="备注信息">
        </div>
    </div>
    <div class="form-group">
        <button type="button" onclick="addGoodsBtn()" id="addGoodsButton" class="btn btn-default"
                style="float: right; margin-bottom:  15px; margin-right: 15px;">确定
        </button>
    </div>
</form>
<form role="form" id="confirmForm"  style="display:none">
    <div class="form-group">
            <div class="col-sm-12">
                <input type="password" class="form-control" id="confirmPassword" name="confirmPassword" placeholder="确认密码">
            </div>
    </div>
    <div class="form-group">
        <button type="button" onclick="confirmBtn()" id="confirmButton" class="btn btn-default"
                style="float: right; margin-bottom:  15px; margin-right: 15px;">确定
        </button>
    </div>
</form>
<form role="form" id="outForm" style="display: none">
    <div class="form-group">
        <div class="form-group" style="display: none;">
            <input type="text" class="form-control" id="stockIdOut" hidden name="stockId" placeholder="入库流水id">
            <input type="text" class="form-control" id="goodsIdOut" hidden name="goodsId" placeholder="商品id">
            <input type="text" class="form-control" id="goodsExtendIdOut" hidden name="goodsExtendId" placeholder="商品扩展id">
            <input type="text" class="form-control" id="goodsQtyOut" hidden name="goodsQty" placeholder="商品库存数量">
        </div>
        <div class="form-group">
            <div class="col-sm-3">
                <label class="control-label">信息</label>
            </div>
            <div class="col-sm-9">
                <input type="text" class="form-control" id="infoOut" readonly name="info" placeholder="出库信息">
            </div>
        </div>
        <div class="form-group">
            <div class="col-sm-3">
                <label for ="numOut" class="control-label">数量</label>
            </div>
            <div class="col-sm-3">
                <input type="number" class="form-control" id="numOut" required min="1" name="num" placeholder="出库数量">
            </div>
            <div class="col-sm-3">
                <label for="priceOut" class="control-label">价格</label>
            </div>
            <div class="col-sm-3">
                <input type="number" class="form-control" id="priceOut" required name="price" placeholder="单价">
            </div>
        </div>
        <div class="form-group">
            <button type="button" onclick="outBtn()" id="outButton" class="btn btn-default"
                    style="float: right; margin-bottom:  15px; margin-right: 15px;">确定
            </button>
        </div>
    </div>
</form>
<script>
    //JavaScript代码区域
    layui.use('element', function () {
        var element = layui.element;

    });
</script>
<script src="<%=basePath%>js/custom/dateFormat.js"></script>
<script id="timeTpl_intoTime" type="text/html">
    {{#
    var date = new Date();
    date.setTime(d.storageTime);
    return date.Format("yyyy-MM-dd hh:mm:ss");
    }}
</script>
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
    <a class="layui-btn layui-btn-xs" lay-event="out">出库</a>
    <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>

    <!-- 这里同样支持 laytpl 语法，如： -->
    {{#  if(d.auth > 2){ }}
    <a class="layui-btn layui-btn-xs" lay-event="check">审核</a>
    {{#  } }}
</script>
<script src="<%=basePath%>js/custom/stock.js"></script>
</body>
</html>
