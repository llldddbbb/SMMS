var vm = new Vue({
    el: '#app',
    data: {
        showList: true,
        title: '',
        q: {
            item: null
        },
        material:{},
        file:{},
        projectId: getUrlKey('projectId')
    },
    methods: {
        query: function () {
            vm.reload();
        },
        reload: function (event) {
            vm.showList = true;
            var page = $("#jqGrid").jqGrid('getGridParam', 'page');
            $("#jqGrid").jqGrid('setGridParam', {
                postData: {'item': vm.q.item},
                page: page
            }).trigger("reloadGrid");
        },
        downloadFile: function(type){
            location.href=baseURL+"/mat/material/download/"+vm.material.matId+"?type="+type+"&token="+token
        },
        del: function () {
            var matIds = getSelectedRows();
            if (matIds == null) {
                return;
            }
            confirm('确定要移除选中的记录？', function () {
                $.ajax({
                    type: "POST",
                    url: baseURL + "mat/project/material/remove",
                    contentType: "application/json",
                    data: JSON.stringify(matIds),
                    success: function (r) {
                        if (r.code == 0) {
                            alert('操作成功', function () {
                                vm.reload();
                            });
                        } else {
                            alert(r.msg);
                        }
                    }
                });
            });
        }
    }

});

$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mat/project/material/list/' + vm.projectId,
        datatype: "json",
        colModel: [
            {label: '编号', name: 'matId', index: "mat_id", width: 25, key: true},
            {
                label: '产品图像',
                name: 'productPicture',
                index: "product_picture",
                width: 90,
                formatter: function (value, options, row) {
                    return '<a href="'+baseURL+'/mat/material/productPicture?pictureName=' + value + '" target="_blank" ><img style="width: 100%;height: 120px" src="'+baseURL+'/mat/material/productPicture?pictureName=' + value + '" /></a>';
                }
            },
            {label: '部件名称', name: 'item', index: 'item', width: 90},
            {label: '部件型号', name: 'model', index: 'model', width: 70},
            {label: '应用产品', name: 'applications', index: 'applications', width: 70},
            {label: '价格', name: 'price', index: 'price', width: 35},
            {label: '生产厂家', name: 'manufacturer', index: 'manufacturer', width: 80},
            {label: '联系方式', name: 'contact', index: 'contact', width: 40},
            {label: '备注', name: 'remarks', index: 'remarks', width: 50},
            {
                label: '网址', name: 'website', index: 'website', width: 25, formatter: function (value) {
                return '<a href="' + value + '" target="_blank" title="' + value + '">查看</a>'
            }
            },
            {
                label: '资料', width: 25, formatter: function (cellvalue, options, rowObject) {
                return '<input type="button" onclick="showFile(' + rowObject.matId + ')" class="btn btn-warning  btn-xs" value="查看" />';
            }
            }
        ],
        viewrecords: true,
        multiselect: true,
        height: 485,
        rowNum: 10,
        rowList: [10, 30, 50],
        autowidth: true,
        pager: "#jqGridPager",
        jsonReader: {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames: {
            page: "page",
            rows: "limit",
            order: "order"
        },
        gridComplete: function () {
            // //隐藏grid底部滚动条
            // $("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" });
        }
    });
});

function showFile(matId) {
    if(!hasPermission('mat:material:download')){
        alert("未授权，请联系管理员");
        return ;
    }
    Vue.set(vm.material,"matId",matId);
    var url = "mat/material/info/" + matId;
    $.ajax({
        type: "GET",
        url: baseURL + url,
        contentType: "application/json",
        data: {},
        success: function (r) {
            if (r.code === 0) {
                vm.file = r.material;
                layer.open({
                    type: 1,
                    offset: '50px',
                    skin: 'layui-layer-molv',
                    title: "下载资料",
                    area: ['300px','280px'],
                    shade: 0,
                    shadeClose: false,
                    content: jQuery("#fileInfoLayer")
                });
            } else {
                alert(r.msg);
            }
        }
    });
}


