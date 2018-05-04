var table,laypage,form,layer;
var editGoodsLayer,pageSize;
var brandArray,typeArray,sizeArray,colorArray;
$(document).ready(function () {
    layui.use('table', function(){
        table = layui.table;
        laypage = layui.laypage;
        form = layui.form;
        layer = layui.layer;
        var bodyHeight = $("#goodsManageTable").closest("body").height();
        var formHeight = $("#search-goods-form").height();
        var layuiTableHBHeight = 80;
        pageSize = Math.floor((bodyHeight-formHeight-layuiTableHBHeight-5)/39);
        $.ajax({
            type: "POST",
            url: "/shoes/brand/shortBrand",
            dataType:"json",
            async:false,
            contentType: "application/json",
            success: function (data) {
                brandArray = data.data.slice(0);
                for (var i = 0; i < data.data.length; i++) {
                    $("#goodsBrandSearch").append('<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>');
                }
                form.render('select');
            }
        });
        $.ajax({
            type: "POST",
            url: "/shoes/size/allDetail",
            dataType:"json",
            async:false,
            contentType: "application/json",
            success: function (data) {
                sizeArray = data.data.slice(0);
                for (var i = 0; i < data.data.length; i++) {
                    $("#goodsSizeSearch").append('<option value="' + data.data[i].id + '">' + data.data[i].sizeName + '</option>');
                }
                form.render('select');
            }
        });
        $.ajax({
            type: "POST",
            url: "/shoes/type/shortType",
            dataType:"json",
            async:false,
            contentType: "application/json",
            success: function (data) {
                typeArray = data.data.slice(0);
                for (var i = 0; i < data.data.length; i++) {
                    $("#goodsTypeSearch").append('<option value="' + data.data[i].id + '">' + data.data[i].typeName + '</option>');
                }
                form.render('select');
            }
        });
        $.ajax({
            type: "POST",
            url: "/shoes/color/getColor",
            dataType:"json",
            async:false,
            contentType: "application/json",
            success: function (data) {
                colorArray = data.data.slice(0);
                for (var i = 0; i < data.data.length; i++) {
                    $("#goodsColorSearch").append('<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>');
                }
                form.render('select');
            }
        });
        //监听表格复选框选择
        /* table.on('checkbox(demo)', function(obj){
             console.log(obj)
         });*/
        //监听工具条
        table.on('tool(goodTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'buy'){
                editGoodsLayer = layer.open({
                    type: 1,
                    area:'700px',
                    title: '编辑订单',
                    content: $('#editGoodsForm'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    success: function(layero){
                        layer.setTop(layero); //重点2
                        var brandName,size,color,goodsClassName;
                        $.each(brandArray, function (i, content1) {
                            if (content1.id == data.brandId) {
                                brandName = content1.name;
                            }
                        });
                        $.each(sizeArray, function (i, content1) {
                            if (content1.id == data.sizeId) {
                                size = content1.sizeName;
                            }
                        });
                        $.each(typeArray, function (i, content1) {
                            if (content1.id == data.goodsClass) {
                                goodsClassName = content1.typeName;
                            }
                        });
                        $.each(colorArray, function (i, content1) {
                            if (content1.id == data.colorId) {
                                color = content1.name;
                            }
                        });
                        var info = data.goodsName+" "+brandName+" "+size+" "+ color +" "+goodsClassName+" "+data.price;
                        $("#idEdit").val(data.id);
                        $("#userIdEdit").val(data.userId);
                        $("#colorIdEdit").val(data.colorId);
                        $("#sizeIdEdit").val(data.sizeId);
                        $("#goodsExtendIdEdit").val(data.goodsExtendId);
                        $("#stockNumEdit").val(data.qty);
                        $("#info").html(info);
                        $("#qty").val(1);
                    }
                });
            }
        });
        form.on('select(brandIdSearchFilter)', function (data) {
            brandId = data.value;
            // categoryName = data.elem[data.elem.selectedIndex].text;
            form.render('select');
        });
        form.on('select(goodsClassSearchFilter)', function (data) {
            goodsClass = data.value;
            // categoryName = data.elem[data.elem.selectedIndex].text;
            form.render('select');
        });
        form.on('select(goodsSizeSearchFilter)', function (data) {
            sizeId = data.value;
            // categoryName = data.elem[data.elem.selectedIndex].text;
            form.render('select');
        });
        form.on('select(goodsColorSearchFilter)', function (data) {
            colorId = data.value;
            // categoryName = data.elem[data.elem.selectedIndex].text;
            form.render('select');
        });
    });
});
function searchGoods() {
    table.render({
        elem: '#goodsManageTable'
        ,url:'/shoes/goods/getOthersGoods?'+$("#search-goods-form").serialize()
        ,cols: [[
            {field:'id', minWidth:'0%',width:'0%',type:'space',style:'display:none;'}
            ,{field:'userId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
            ,{field:'goodsExtendId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
            ,{field:'goodsName',  title: '名称',align:'center'}
            ,{field:'brandId',  title: '品牌',align:'center'}
            ,{field:'sizeId',  title: '尺码',align:'center'}
            ,{field:'colorId',  title: '颜色',align:'center'}
            ,{field:'goodsClass',  title: '分类',align:'center'}
            ,{field:'keyword',  title: '关键字',align:'center'}
            ,{field:'qty',  title: '库存',align:'center'}
            ,{field:'price',  title: '价格',align:'center'}
            ,{field:'sex',  title: '性别',align:'center'}
            ,{field:'status',  title: '状态',align:'center'}
            ,{fixed: 'right',title:'操作', width: 165, align:'center', toolbar: '#operation'}
        ]]
        ,page: {
            limit:pageSize,
            limits:[pageSize]
        }
        ,skin:'line'
        ,even:true
        ,done: function(res, curr, count) {
            $("[data-field='brandId']").children().each(function () {
                var id = $(this);
                $.each(brandArray, function (i, content1) {
                    if (content1.id == $(id).text()) {
                        $(id).text(content1.name);
                    }
                });
            });
            $("[data-field='goodsClass']").children().each(function () {
                var id = $(this);
                $.each(typeArray, function (i, content1) {
                    if (content1.id == $(id).text()) {
                        $(id).text(content1.typeName);
                    }
                });
            });
            $("[data-field='sizeId']").children().each(function () {
                var id = $(this);
                $.each(sizeArray, function (i, content1) {
                    if (content1.id == $(id).text()) {
                        $(id).text(content1.sizeName);
                    }
                });
            });
            $("[data-field='colorId']").children().each(function () {
                var id = $(this);
                $.each(colorArray, function (i, content1) {
                    if (content1.id == $(id).text()) {
                        $(id).text(content1.name);
                    }
                });
            });
            $("[data-field='status']").children().each(function () {
                if ($(this).text() == 1) {
                    $(this).text("上架");
                }
                if ($(this).text() == 0) {
                    $(this).text("下架");
                }
            });
            $("[data-field='sex']").children().each(function () {
                if ($(this).text() == 0) {
                    $(this).text("男");
                }
                if ($(this).text() == 1) {
                    $(this).text("女");
                }
                if ($(this).text() == 2) {
                    $(this).text("通用");
                }
            });
        }
    });
}
function editGoodsBtn() {
    var sNum = parseInt($("#stockNumEdit").val());
    var qtyNum = parseInt($("#qty").val());
    if(qtyNum>sNum){
        alert("库存件数不够！");
    }else{
        $.post("/shoes/order/addOrder", $("#editGoodsForm").serialize(),function (data) {
            if(data.success){
                layer.msg("下单成功！", {icon: 1,time:2000});
            }else{
                layer.msg("下单失败，请联系管理员", {icon: 5,time:2000});
            }
            layer.close(editGoodsLayer);
        });
    }
}