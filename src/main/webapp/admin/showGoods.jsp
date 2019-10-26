<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css" />
<script src="${pageContext.request.contextPath}/js/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
<script src="${pageContext.request.contextPath}/js/DatePicker.js"></script>
<title>商品列表</title>

</head>
<body>
<div class="row" style="width:98%;margin-left: 1%;">
	<div class="col-xs-12 col-sm-12 col-md-12 col-lg-12">
		<div class="panel panel-default" style="height: 802px">
			<div class="panel-heading">
				商品列表
			</div>
			<div class="panel-body" style="height: 802px">
				<div class="row">
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="form-group form-inline">
							<span>商品名称</span>
							<input type="text" name="name" class="form-control">
						</div>
					</div>
					<div class="col-xs-5 col-sm-5 col-md-5 col-lg-5">
						<div class="form-group form-inline">
							<span>上架时间</span>
							<input type="text" readonly="readonly"  name="pubdate" class="form-control" onclick="setday(this)">
						</div>
					</div>
					<div>
						<button type="button" value="查询"  id="search">查询</button>
					</div>
				</div>
				<div style="height: 802px;overflow: scroll;">
					<table id="tb_list" class="table table-striped table-hover table-bordered">
						<tr>
							<td>序号</td><td>商品名称</td><td>价格</td><td>上架时间</td><td>图片</td><td>操作</td>
						</tr>
						<c:forEach items="${goods}" var="goods" varStatus="i">
							<tr>
								<td>${i.count}</td>
								<td>${goods.name}</td>
								<td>${goods.price}</td>
								<td><fmt:formatDate value="${goods.times}" pattern="yyyy-MM-dd"></fmt:formatDate></td>

								<td><img src="${pageContext.request.contextPath}/image/${goods.picture}" /></td>

								<td><a href="#" onclick="delgoods(${goods.id})">删除</a> &nbsp;<a href="#" onclick="upgoods(${goods.id})" >修改</a> &nbsp;
									<a tabindex="0" id="example${goods.id}" class="btn btn-primary btn-xs"
									role="button" data-toggle="popover"
									data-trigger="focus"
									data-placement="left"
									data-content="${goods.intro}">描述</a>
									<script type="text/javascript">
										$(function(){
											$("#example${goods.id}").popover();
										})
									</script>
								</td>
							</tr>
						</c:forEach>
					</table>
				</div>
			</div>
			
		</div>
	</div>

	<script type="text/javascript">
		function delgoods(gid) {
			if (confirm("确定要删除这个商品吗?")){
			    $.post("${pageContext.request.contextPath}/admincontroller/delGoods","gid="+gid,function (data) {
					if (data!=null){
					    alert("删除成功!");
                        location.href="${pageContext.request.contextPath}/admincontroller/goods";
					}
                })
			}
        }

        function upgoods(gid) {
			location.href="${pageContext.request.contextPath}/admincontroller/loadGoods?gid="+gid;
        }
	</script>

</div>
</body>
</html>