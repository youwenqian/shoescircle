var table,laypage,form,layer;
var editGoodsLayer,pageSize;
var brandArray,typeArray,sizeArray;
$(document).ready(function () {
    layui.use('table', function(){
        table = layui.table;
        laypage = layui.laypage;
        form = layui.form;
        layer = layui.layer;
        var bodyHeight = $(document).height();
        var formHeight = $("#search-goods-form").height();
        var layuiTableHBHeight = 80;
        pageSize = Math.floor((bodyHeight-formHeight-layuiTableHBHeight-25)/39);
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
        table.render({
            elem: '#goodsManageTable'
            ,url:'/shoes/goods/allDetail'
            ,cols: [[
                {field:'id', minWidth:'0%',width:'0%',type:'space',style:'display:none;'}
                ,{field:'userId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
                ,{field:'goodsName',  title: '名称',align:'center'}
                ,{field:'brandId',  title: '品牌',align:'center'}
                ,{field:'goodsClass',  title: '分类',align:'center'}
                ,{field:'sex',  title: '性别',align:'center'}
                ,{field:'goodsDesc',  title: '描述',align:'center'}
                ,{field:'keyword',  title: '关键字',align:'center'}
                ,{field:'price',  title: '共享价格',align:'center'}
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
                $("[data-field='brandId']").children().each(function(){
                    var id = $(this);
                    $.each(brandArray,function (i,content1) {
                        if(content1.id==$(id).text()){
                            $(id).text(content1.name);
                        }
                    });
                });
                $("[data-field='goodsClass']").children().each(function(){
                    var id = $(this);
                    $.each(typeArray,function (i,content1) {
                        if(content1.id==$(id).text()){
                            $(id).text(content1.typeName);
                        }
                    });
                });
                $("[data-field='status']").children().each(function(){
                    if($(this).text() == 1){
                        $(this).text("上架");
                    }
                    if($(this).text() == 0){
                        $(this).text("下架");
                    }
                });
                $("[data-field='sex']").children().each(function(){
                    if($(this).text() == 0){
                        $(this).text("男");
                    }
                    if($(this).text() == 1){
                        $(this).text("女");
                    }
                    if($(this).text() == 2){
                        $(this).text("通用");
                    }
                });
            }
        });
         //监听工具条
         table.on('tool(goodTable)', function(obj){
             var data = obj.data;
             if(obj.event === 'del'){
                 layer.confirm('确定删除'+data.goodsName+'吗？', function(index){
                     $.ajax({
                         type: "POST",
                         url: "/shoes/goods/deleteType/"+data.id,
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
                 editGoodsLayer = layer.open({
                     type: 1,
                     area:'600px',
                     title: '编辑商品',
                     content: $('#editGoodsForm'), //这里content是一个DOM，注意：最好该元素要存放在body最外层，否则可能被其它的相对元素所影响
                     success: function(layero){
                         layer.setTop(layero); //重点2
                         $("#idEdit").val(data.id);
                         $("#goodsNameEdit").val(data.goodsName);
                         if($("#goodsBrandEdit>option").length<=1){
                             $("#goodsBrandSearch>option:first ~ option").each(function(){
                                 $("#goodsBrandEdit").append(this);
                                 console.log($(this));
                                 if(data.brandId==$(this).val()){
                                     $("#goodsBrandEdit>option:last").attr("selected","selected");
                                     form.render('select');
                                 }
                             });
                         }else{
                             $("#goodsBrandEdit>option").each(function () {
                                 if(data.brandId==$(this).val()){
                                     $(this).attr("selected","selected");
                                 }
                             });
                         }
                         if($("#goodsTypeEdit>option").length<=1){
                             $("#goodsTypeSearch>option:first ~ option").each(function(){
                                 $("#goodsTypeEdit").append(this);
                                 if(data.goodsClass==$(this).val()){
                                     $("#goodsTypeEdit>option:last").attr("selected","selected");
                                     form.render('select');
                                 }
                             });
                         }else{
                             $("#goodsTypeEdit>option").each(function () {
                                 if(data.goodsClass==$(this).val()){
                                     $(this).attr("selected","selected");
                                 }
                             });
                         }
                         $("#sexEdit>option:first ~ option").each(function(){
                             if(data.sex==$(this).val()){
                                 $(this).attr("selected","selected");
                                 form.render('select');
                             }
                         });
                         $("#statusEdit>option:first ~ option").each(function(){
                             if(data.sex==$(this).val()){
                                 $(this).attr("selected","selected");
                                 form.render('select');
                             }
                         });
                         $("#descEdit").val(data.goodsDesc);
                         $("#keywordEdit").val(data.keyword);
                         $("#priceEdit").val(data.price);
                         $("#remarkEdit").val(data.remark);
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
    });
});

function addGoods() {
    editGoodsLayer = layer.open({
        type: 1,
        area:'600px',
        title: '添加商品',
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
        ,url:'/shoes/goods/getGoods?'+$("#search-goods-form").serialize()
        ,cols: [[
            {field:'id', minWidth:'0%',width:'0%',type:'space',style:'display:none;'}
            ,{field:'userId', type:'space',style:'display:none;',minWidth:'0%',width:'0%'}
            ,{field:'goodsName',  title: '名称',align:'center'}
            ,{field:'brandId',  title: '品牌',align:'center'}
            ,{field:'goodsClass',  title: '分类',align:'center'}
            ,{field:'sex',  title: '性别',align:'center'}
            ,{field:'goodsDesc',  title: '描述',align:'center'}
            ,{field:'keyword',  title: '关键字',align:'center'}
            ,{field:'price',  title: '共享价格',align:'center'}
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
            $("[data-field='brandId']").children().each(function(){
                var id = $(this);
                $.each(brandArray,function (i,content1) {
                    if(content1.id==$(id).text()){
                        $(id).text(content1.name);
                    }
                });
            });
            $("[data-field='goodsClass']").children().each(function(){
                var id = $(this);
                $.each(typeArray,function (i,content1) {
                    if(content1.id==$(id).text()){
                        $(id).text(content1.typeName);
                    }
                });
            });
            $("[data-field='status']").children().each(function(){
                if($(this).text() == 1){
                    $(this).text("上架");
                }
                if($(this).text() == 0){
                    $(this).text("下架");
                }
            });
            $("[data-field='sex']").children().each(function(){
                if($(this).text() == 0){
                    $(this).text("男");
                }
                if($(this).text() == 1){
                    $(this).text("女");
                }
                if($(this).text() == 2){
                    $(this).text("通用");
                }
            });
        }
    });
}

function addGoodsBtn() {
    $.post("/shoes/goods/addGoods", $("#addGoodsForm").serialize(),function (data) {
        if(data.flag){
            searchGoods();
            layer.msg("添加成功！", {icon: 1,time:2000});
        }else{
            layer.msg(data.message, {icon: 5,time:2000});
        }
        layer.close(editGoodsLayer);
    });
}

function editGoodsBtn() {
    $.post("/shoes/goods/updateGoods", $("#editGoodsForm").serialize(),function (data) {
        if(data.success){
            searchGoods();
            layer.msg("修改成功！", {icon: 1,time:2000});
        }else{
            layer.msg("修改失败，请联系管理员", {icon: 5,time:2000});
        }
        layer.close(editGoodsLayer);
    });
}