var setting = {
    data: {
        simpleData: {
            enable: true,
            idKey: "projectId",
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
        project: {
            parentName: null,
            parentId: 34,
            type: 1,
            orderNum: 0
        }
    },
    methods: {
        getproject: function (projectId) {
            //加载项目树
            $.get(baseURL + "mat/project/select", function (r) {
                ztree = $.fn.zTree.init($("#projectTree"), setting, r.projectList);
                var node = ztree.getNodeByParam("projectId", vm.project.parentId);
                ztree.selectNode(node);
                vm.project.parentName = node.name;
            })
        },
        add: function () {
            vm.showList = false;
            vm.title = "新增类目";
            vm.project = {parentName: null, parentId: 34, type: 1, orderNum: 0};
            vm.getproject();
        },
        update: function () {
            var projectId = getprojectId();
            if (projectId == null) {
                return;
            }

            $.get(baseURL + "mat/project/info/" + projectId, function (r) {
                vm.showList = false;
                vm.title = "修改类目";
                vm.project = r.project;
                vm.getproject();
            });
        },
        del: function () {
            var projectId = getprojectId();
            if (projectId == false) {
                return;
            }

            confirm('确定要删除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "mat/project/delete",
                    data: "projectId=" + projectId,
                    success: function (r) {
                        if (r.code === 0) {
                            alert('操作成功', function () {
                                parent.location.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.project.projectId == null ? "mat/project/save" : "mat/project/update";
            $.ajax({
                type: "POST",
                url: baseURL + url,
                contentType: "application/json",
                data: JSON.stringify(vm.project),
                success: function (r) {
                    if (r.code === 0) {
                        alert('操作成功', function () {
                            parent.location.reload();
                        });
                    } else {
                        alert(r.msg);
                    }
                }
            });
        },
        projectTree: function () {
            layer.open({
                type: 1,
                offset: '50px',
                skin: 'layui-layer-molv',
                title: "选择项目",
                area: ['300px', '450px'],
                shade: 0,
                shadeClose: false,
                content: jQuery("#projectLayer"),
                btn: ['确定', '取消'],
                btn1: function (index) {
                    var node = ztree.getSelectedNodes();
                    //选择上级类目
                    vm.project.parentId = node[0].projectId;
                    vm.project.parentName = node[0].name;
                    layer.close(index);
                }
            });
        },
        reload: function () {
            vm.showList = true;
            project.table.refresh();
        }
    }
});


var project = {
    id: "projectTable",
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
project.initColumn = function () {
    var columns = [
        {field: 'selectItem', radio: true},
        {title: '项目ID', field: 'projectId', visible: false, align: 'center', valign: 'middle', width: '30px'},
        {title: '项目名称', field: 'name', align: 'center', valign: 'middle', sortable: true, width: '180px'},
        {title: '排序号', field: 'orderNum', align: 'center', valign: 'middle', sortable: true, width: '40px'},
        {title: '上级项目', field: 'parentName', align: 'center', valign: 'middle', sortable: true, width: '80px'}
    ];
    return columns;
};


function getprojectId() {
    var selected = $('#projectTable').bootstrapTreeTable('getSelections');
    if (selected.length == 0) {
        alert("请选择一条记录");
        return false;
    } else {
        return selected[0].id;
    }
}

//获取类目列表
$(function () {
    var colunms = project.initColumn();
    var table = new TreeTable(project.id, baseURL + "mat/project/list", colunms);
    table.setExpandColumn(2);
    table.setRootCodeValue(34);
    table.setIdField("projectId");
    table.setCodeField("projectId");
    table.setParentCodeField("parentId");
    table.setExpandAll(false);
    table.init();
    project.table = table;
});
