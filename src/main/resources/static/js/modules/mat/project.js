$(function () {
    $("#jqGrid").jqGrid({
        url: baseURL + 'mat/project/list',
        datatype: "json",
        colModel: [			
			{ label: '项目ID', name: 'projectId', index: "project_id", width: 45, key: true },
			{ label: '项目名称', name: 'name', width: 75 },
			{ label: '排序号', name: 'orderNum', width: 90 },
			{ label: '创建时间', name: 'createTime', index: "create_time", width: 80}
        ],
		viewrecords: true,
        height: 385,
        rowNum: 10,
		rowList : [10,30,50],
        autowidth:true,
        multiselect: true,
        pager: "#jqGridPager",
        jsonReader : {
            root: "page.list",
            page: "page.currPage",
            total: "page.totalPage",
            records: "page.totalCount"
        },
        prmNames : {
            page:"page",
            rows:"limit",
            order: "order"
        },
        gridComplete:function(){
        	//隐藏grid底部滚动条
        	$("#jqGrid").closest(".ui-jqgrid-bdiv").css({ "overflow-x" : "hidden" }); 
        }
    });
});

var vm = new Vue({
	el:'#app',
	data:{
		showList: true,
		title:null,
		project:{
			name:'',
            orderNum:0
		}
	},
	methods: {
		query: function () {
			vm.reload();
		},
		add: function(){
			vm.showList = false;
			vm.title = "新增项目";
		},
		update: function () {
			var projectId = getSelectedRow();
			if(projectId == null){
				return ;
			}
			
			vm.showList = false;
            vm.title = "修改项目";
			
			vm.getProject(projectId);
			
		},
		del: function () {
			var projectId = getSelectedRow();
			if(projectId == null){
				return ;
			}
			
			confirm('确定要删除选中的记录？', function(){
				$.ajax({
					type: "POST",
				    url: baseURL + "mat/project/delete",
                    contentType: "application/json",
				    data: JSON.stringify(projectId),
				    success: function(r){
						if(r.code == 0){
							alert('操作成功', function(){
                                parent.location.reload();
							});
						}else{
							alert(r.msg);
						}
					}
				});
			});
		},
		saveOrUpdate: function () {
			var url = vm.project.projectId == null ? "mat/project/save" : "mat/project/update";
			$.ajax({
				type: "POST",
			    url: baseURL + url,
                contentType: "application/json",
			    data: JSON.stringify(vm.project),
			    success: function(r){
			    	if(r.code === 0){
						alert('操作成功', function(){
                            parent.location.reload();
						});
					}else{
						alert(r.msg);
					}
				}
			});
		},
		getProject: function(projectId){
			$.get(baseURL + "mat/project/info/"+projectId, function(r){
				vm.project = r.project;
			});
		},
		reload: function () {
                vm.showList = true;
                var page = $("#jqGrid").jqGrid('getGridParam','page');
                $("#jqGrid").jqGrid('setGridParam',{
                    page:page
                }).trigger("reloadGrid");
		}
	}
});