var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "categoryId",
            pIdKey: "parentId",
            rootPId: -1
        },
        key: {
            url: "nourl"
        }
    }
};
var ztree;

var vm = new Vue({
    el: '#app',
    data: {
        showList: true,
        title: null,
        category: {
            parentName: null,
            parentId: 33,
            type: 1,
            orderNum: 0
        }
    },
    methods: {
        getcategory: function (categoryId) {
            //加载菜单树
            $.get(baseURL + "mat/category/select", function (r) {
                ztree = $.fn.zTree.init($("#categoryTree"), setting, r.categoryList);
                var node = ztree.getNodeByParam("categoryId", vm.category.parentId);
                ztree.selectNode(node);
                vm.category.parentName = node.name;
            })
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增类目";
            vm.category = {parentName: null, parentId: 33, type: 1, orderNum: 0};
            vm.getcategory();
        },
        update: function () {
            var categoryId = getcategoryId();
            if (categoryId == null) {
                return;
            }

            $.get(baseURL + "mat/category/info/" + categoryId, function (r) {
                vm.showList = false;
                vm.title = "修改类目";
                vm.category = r.category;
                vm.getcategory();
            });
        },
        del: function () {
            var categoryId = getcategoryId();
            if (categoryId == null) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "mat/category/delete",
                    data: "categoryId=" + categoryId,
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.category.categoryId == null ? "mat/category/save" : "mat/category/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.category),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            vm.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        categoryTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择类目",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#categoryLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级类目
                    vm.category.parentId = node[0].categoryId;
                    vm.category.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            category.table.refresh();
        }
    }
});


var category = {
    id: "categoryTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
category.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '类别ID', field: 'categoryId', visible: false, align: 'center', valign: 'middle', width: '30px'},
        {title: '类别名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '40px'},
        {title: '上级类目', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '80px'}
    ];
    return columns;
};


function getcategoryId() {
    var selected = $('#categoryTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}

//获取类目列表
$(function () {
    var colunms = category.initColumn();
    var table = new TreeTable(category.id, baseURL + "mat/category/list", colunms);
    table.setExpandColumn(2);
    table.setRootCodeValue(33);
    table.setIdField("categoryId");
    table.setCodeField("categoryId");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    category.table = table;
});
