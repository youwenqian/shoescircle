var laydate,table,laypage,form,layer;
var editGoodsLayer,comfirmLayer,outLayer,pageSize;
var brandArray,typeArray,sizeArray;
var selectedData;
$(document).ready(function () {
    layui.use(['laydate','table','laypage','form','layer'], function(){
        laydate = layui.laydate;
        table = layui.table;
        laypage = layui.laypage;
        form = layui.form;
        layer = layui.layer;
        var bodyHeight = $(document).height();
        var formHeight = $("#search-goods-form").height();
        var layuiTableHBHeight = 80;
        pageSize = Math.floor((bodyHeight-formHeight-layuiTableHBHeight-25)/39);
        table.render({
            elem: '#goodsManageTable'
            ,url:'/shoes/stock/getStockDetail'
            ,cols: [[
                {field:'id', minWidth:'0%',width:'0%',type:'space',style:'display:none;'}
                ,{field:'userId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
                ,{field:'goodsId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
                ,{field:'goodsName',  title: '名称',align:'center'}
                ,{field:'goodsExtendId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
                ,{field:'sizeId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
                ,{field:'sizeName', title:'尺码',align:'center'}
                ,{field:'colorId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
                ,{field:'colorName', title:'颜色',align:'center'}
                ,{field:'batchNo', title:'批号',align:'center'}
                ,{field:'intoPrice',  title: '入库价格',align:'center'}
                ,{field:'qty',  title: '数量',align:'center'}
                ,{field:'shareFlag',  title: '共享',align:'center'}
                ,{field:'storageTime',  title: '入库时间',templet: '#timeTpl_intoTime',align:'center'}
                ,{field:'storeAddress',  title: '地址',align:'center'}
                ,{field:'status',  title: '状态',align:'center'}
                ,{field:'createUser',  title: '创建人', align:'center'}
                ,{field:'createTime',  title: '创建时间',templet: '#timeTpl_createTime',align:'center'}
                ,{field:'updateUser', title: '更新人', minWidth: 100,align:'center'}
                ,{field:'updateTime',  title: '更新时间', templet: '#timeTpl_updateTime',align:'center'}
                ,{field:'remark',  title: '备注', sort: true,align:'center'}
                ,{fixed: 'right',title:'操作', width: 165, align:'center', toolbar: '#operation'}
            ]]
            ,page: {
                limit:pageSize,
                limits:[pageSize]
            }
            ,skin:'line'
            ,even:true
            ,done: function(res, curr, count){
                $("[data-field='status']").children().each(function(){
                    if($(this).text() == 1){
                        $(this).text("在售");
                    }
                    if($(this).text() == 0){
                        $(this).text("售完");
                    }
                });
                $("[data-field='shareFlag']").children().each(function(){
                    if($(this).text() == 1){
                        $(this).text("共享");
                    }
                    if($(this).text() == 0){
                        $(this).text("不共享");
                    }
                });
            }
        });
        //监听工具条
        table.on('tool(stockTable)', function(obj){
            var data = obj.data;
            if(obj.event === 'del'){
                layer.confirm('确定删除'+data.goodsName+" "+data.sizeName+" "+data.colorName+'吗？', function(index){
                    $.ajax({
                        type: "POST",
                        url: "/shoes/stock/deleteStock/"+data.id,
                        dataType:"json",
                        contentType: "application/json",
                        success: function (data) {
                            obj.del();
                            form.render('select');
                        }
                    });
                    layer.close(index);
                });
            } else if(obj.event === 'edit'){
                comfirmLayer = layer.open({
                    type: 1,
                    area:'300px',
                    title: '确认密码',
                    content: $('#confirmForm'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                    success: function(layero){
                        layer.setTop(layero); //重点2
                        $("#confirmPassword").val("");
                        selectedData = data;
                    }
                });
            } else if(obj.event === 'out'){
                if(data.status==0){
                    layer.msg("商品售完,不能再出库！", {icon: 5,time:2000});
                }else{
                    outLayer = layer.open({
                        type: 1,
                        area:'600px',
                        title: '确认密码',
                        content: $('#outForm'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                        success: function(layero){
                            layer.setTop(layero); //重点2
                            $("#stockIdOut").val(data.id);
                            var info = data.goodsName+" "+data.sizeName+" "+ data.colorName;
                            $("#infoOut").val(info);
                            $("#goodsIdOut").val(data.goodsId);
                            $("#goodsExtendIdOut").val(data.goodsExtendId);
                            $("#numOut").attr("max",data.qty);
                            $("#numOut").val(1);
                            $("#goodsQtyOut").val(data.qty);
                        }
                    });
                }
            }
        });
        $.ajax({
            type: "POST",
            url: "/shoes/size/allDetail",
            dataType:"json",
            contentType: "application/json",
            success: function (data) {
                brandArray = data.data.slice(0);
                for (var i = 0; i < data.data.length; i++) {
                    $("#goodsSizeAdd").append('<option value="' + data.data[i].id + '">' + data.data[i].sizeName + '</option>');
                    $("#goodsSizeEdit").append('<option value="' + data.data[i].id + '">' + data.data[i].sizeName + '</option>');
                }
                form.render('select');
            }
        });
        $.ajax({
            type: "POST",
            url: "/shoes/color/getColor",
            dataType:"json",
            contentType: "application/json",
            success: function (data) {
                typeArray = data.data.slice(0);
                for (var i = 0; i < data.data.length; i++) {
                    $("#colorIdAdd").append('<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>');
                    $("#colorIdEdit").append('<option value="' + data.data[i].id + '">' + data.data[i].name + '</option>');
                }
                form.render('select');
            }
        });
        $.ajax({
            type: "POST",
            url: "/shoes/goods/getGoodsIdAndName",
            dataType:"json",
            contentType: "application/json",
            success: function (data) {
                typeArray = data.data.slice(0);
                for (var i = 0; i < data.data.length; i++) {
                    $("#goodsNameEdit").append('<option value="' + data.data[i].id + '">' + data.data[i].goodsName + '</option>');
                    $("#goodsNameAdd").append('<option value="' + data.data[i].id + '">' + data.data[i].goodsName + '</option>');
                }
                form.render('select');
            }
        });
        laydate.render({
            elem: '#storageTimeAdd'
        });
        laydate.render({
            elem: '#storageTimeEdit'
        });
    });
});

function addGoods() {
    editGoodsLayer = layer.open({
        type: 1,
        area:'600px',
        title: '添加',
        content: $('#addGoodsForm'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
        success: function(layero){
            layer.setTop(layero); //重点2
            $("#idAdd").val("");
            $("#goodsNameAdd").val("");
            if($("#goodsBrandAdd>option").length<=1){
                $("#goodsBrandSearch>option:first ~ option").each(function(){
                    $("#goodsBrandAdd").append(this);
                });
            }else{
                $("#goodsBrandSearch>option:first").attr("selected","selected");
            }
            if($("#goodsClassAdd>option").length<=1){
                $("#goodsTypeSearch>option:first ~ option").each(function(){
                    $("#goodsClassAdd").append(this);
                });
            }else{
                $("#goodsClassSearch>option:first").attr("selected","selected");
            }
            $("#price").val(0.0);
            $("#keyword").val("");
            $("#remark").val("");
        }
    });
}

function searchGoods() {
    table.render({
        elem: '#goodsManageTable'
        ,url:'/shoes/stock/getStockDetail?'+$("#search-goods-form").serialize()
        ,cols: [[
            {field:'id', minWidth:'0%',width:'0%',type:'space',style:'display:none;'}
            ,{field:'userId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
            ,{field:'goodsId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
            ,{field:'goodsName',  title: '名称',align:'center'}
            ,{field:'goodsExtendId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
            ,{field:'sizeId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
            ,{field:'sizeName', title:'尺码',align:'center'}
            ,{field:'colorId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
            ,{field:'colorName', title:'颜色',align:'center'}
            ,{field:'batchNo', title:'批号',align:'center'}
            ,{field:'intoPrice',  title: '入库价格',align:'center'}
            ,{field:'qty',  title: '数量',align:'center'}
            ,{field:'shareFlag',  title: '共享',align:'center'}
            ,{field:'storageTime',  title: '入库时间',templet: '#timeTpl_intoTime',align:'center'}
            ,{field:'storeAddress',  title: '地址',align:'center'}
            ,{field:'status',  title: '状态',align:'center'}
            ,{field:'createUser',  title: '创建人', align:'center'}
            ,{field:'createTime',  title: '创建时间',templet: '#timeTpl_createTime',align:'center'}
            ,{field:'updateUser', title: '更新人', minWidth: 100,align:'center'}
            ,{field:'updateTime',  title: '更新时间', templet: '#timeTpl_updateTime',align:'center'}
            ,{field:'remark',  title: '备注', sort: true,align:'center'}
            ,{fixed: 'right',title:'操作', width: 165, align:'center', toolbar: '#operation'}
        ]]
        ,page: {
            limit:pageSize,
            limits:[pageSize]
        }
        ,skin:'line'
        ,even:true
        ,done: function(res, curr, count){
            $("[data-field='status']").children().each(function(){
                if($(this).text() == 1){
                    $(this).text("在售");
                }
                if($(this).text() == 0){
                    $(this).text("售完");
                }
            });
            $("[data-field='shareFlag']").children().each(function(){
                if($(this).text() == 1){
                    $(this).text("共享");
                }
                if($(this).text() == 0){
                    $(this).text("不共享");
                }
            });
        }
    });
}

function addGoodsBtn() {
    $.post("/shoes/stock/addStock", $("#addGoodsForm").serialize(),function (data) {
        if(data.flag){
            searchGoods();
            layer.msg("添加成功！", {icon: 1,time:2000});
        }else{
            layer.msg("添加失败，请联系管理员", {icon: 5,time:2000});
        }
        layer.close(editGoodsLayer);
    });
}

function editGoodsBtn() {
    $.post("/shoes/stock/updateStock", $("#editGoodsForm").serialize(),function (data) {
        if(data.flag){
            searchGoods();
            layer.msg("修改成功！", {icon: 1,time:2000});
        }else{
            layer.msg("修改失败，请联系管理员", {icon: 5,time:2000});
        }
        layer.close(editGoodsLayer);
    });
}

function outBtn() {
    var qty = parseInt($("#goodsQtyOut").val());
    var num = parseInt($("#numOut").val());
    if(qty<num){
        alert("出库数量不能大于库存数量！");
    }else{
        $.post("/shoes/stock/outStock", $("#outForm").serialize(),function (data) {
            if(data.flag){
                searchGoods();
                layer.msg("出库成功！", {icon: 1,time:2000});
            }else{
                layer.msg("出库失败，请联系管理员", {icon: 5,time:2000});
            }
            layer.close(outLayer);
        });
    }
}

function confirmBtn() {
    $.post("/shoes/index/confirmPassword", $("#confirmForm").serialize(),function (data) {
        if(data.success){
            editGoodsLayer = layer.open({
                type: 1,
                area:'600px',
                title: '编辑',
                content: $('#editGoodsForm'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                success: function(layero){
                    layer.setTop(layero); //重点2
                    $("#idEdit").val(selectedData.id);
                    $("#goodsNameEdit>option").each(function () {
                        if(selectedData.goodsId==$(this).val()){
                            $(this).attr("selected","selected");
                        }
                    });
                    $("#colorIdEdit>option").each(function () {
                        if(selectedData.colorId==$(this).val()){
                            $(this).attr("selected","selected");
                        }
                    });
                    $("#goodsSizeEdit>option").each(function () {
                        if(selectedData.sizeId==$(this).val()){
                            $(this).attr("selected","selected");
                        }
                    });
                    $("#qtyEdit").val(selectedData.qty);
                    $("#intoPriceEdit").val(selectedData.intoPrice);
                    var date = new Date();
                    date.setTime(selectedData.storageTime);
                    $("#storageTimeEdit").val(date.Format("yyyy-MM-dd hh:mm:ss"));
                    $("#storeAddressEdit").val(selectedData.storeAddress);
                    $("#remarkEdit").val(selectedData.remark);
                }
            });
            layer.msg("修改成功！", {icon: 1,time:2000});
            layer.close(comfirmLayer);
        }else{
            layer.msg("修改失败，请联系管理员", {icon: 5,time:2000});
        }
    });
}