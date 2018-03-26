<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://shiro.apache.org/tags" prefix="shiro"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<!--  vue.js -->
<link rel="stylesheet" type="text/css"
	href="../js/easyui/themes/default/easyui.css">

<link rel="stylesheet" type="text/css"
	href="../js/easyui/themes/icon.css">
<script type="text/javascript" src="../js/jquery-1.8.3.js"></script>
<script type="text/javascript" src="../js/easyui/jquery.easyui.min.js"></script>

<script type="text/javascript">
	$(function() {

		var index;

		$('#dg').datagrid({
			url : 'dg.json',//请求路径
			striped : true,// 是否显示斑马线
			rownumbers : true,//是否显示行号
			singleSelect : true,// 是否单选
			pagination : true,// 是否使用分页工具条
			onAfterEdit: function(index, row, changes) {
				//编辑完成后触发的函数
				console.log(index);// 行号
				console.log(row);// 修改之后,所有的列组成的json字符串
				console.log(changes);//修改之后,发生改变的列组成的json字符串
				
			},
			columns : [ [ {
				field : 'cb',
				title : 'cb',
				width : 100,
				checkbox : true
			// 是否显示复选框
			}, {//定义表头
				field : 'id',
				title : '编号',
				width : 100,
				editor : {
					type : "numberbox"
				}
			}, {
				field : 'name',
				title : '名称',
				width : 100,
				editor : {
					type : "text"
				}
			}, {
				field : 'age',
				title : '年龄',
				width : 100,
				align : 'right',
				editor : {
					type : "numberbox"
				}
			} ] ],
			toolbar : [ {
				iconCls : 'icon-add',
				text : '添加',
				handler : function() {
					$('#dg').datagrid('insertRow', {
						index : 0, //向哪一行插入数据. 索引从0开始
						row : {}
					//插入的行的数据,什么都不写就代表是空行
					});
					//开始编辑行
					$('#dg').datagrid("beginEdit", 0)
				}
			}, '-', 
			
			<shiro:hasPermission name="courierActsssion_pageQuery">
			{
				iconCls : 'icon-remove',
				text : '删除',
				handler : function() {

					var rows = $('#dg').datagrid("getSelections");
					if (rows.length == 1) {

						index = $('#dg').datagrid("getRowIndex", rows[0]);

						$('#dg').datagrid("deleteRow", index)
					} else {
						$.messager.alert("提示", "您只能选择一条数据进行操作", "warning")
					}

				}
			}, '-', 

			</shiro:hasPermission>
			
			
			{
				iconCls : 'icon-edit',
				text : '修改',
				handler : function() {

					var rows = $('#dg').datagrid("getSelections");
					if (rows.length == 1) {

						index = $('#dg').datagrid("getRowIndex", rows[0]);

						$('#dg').datagrid("beginEdit", index)
					} else {
						$.messager.alert("提示", "您只能选择一条数据进行操作", "warning")
					}

				}
			}, '-', {
				iconCls : 'icon-save',
				text : '保存',
				handler : function() {
					$('#dg').datagrid("endEdit", index)
				}
			}, '-', {
				iconCls : 'icon-help',
				text : '帮助',
				handler : function() {
					alert('帮助按钮')
				}
			} ]

		});

	})
</script>
</head>
<body>
	<!-- EasyUI控件很多都可以发起网络请求,请求方式是AJAX -->
	<table id="dg">

	</table>
</body>
</html>