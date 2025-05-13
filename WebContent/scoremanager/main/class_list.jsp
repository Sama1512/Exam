<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
	<c:param name="title">学生管理システム</c:param>
	<c:param name="scripts"/>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス管理</h2>
			<div class="mb-2 text-end px-4">
				<a href="ClassCreate.action">新規登録</a>
			</div>
			<table class="table table-hover">
				<thead>
					<tr>
						<th>クラス番号</th>
						<th></th>
					</tr>
				</thead>
				<tbody>
					<c:forEach var="classNum" items="${classNums}">
						<tr>
							<td style="width: 200px;">${classNum}</td>
							<td style="text-align: right;">
								<a href="ClassUpdate.action?class_num=${classNum}">変更</a>
							</td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</section>
	</c:param>
</c:import>