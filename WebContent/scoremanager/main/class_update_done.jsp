<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
	<c:param name="title">学生管理システム</c:param>
	<c:param name="scripts"/>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス情報変更</h2>
			<p class="mx-3 px-3 py-1 mb-3 text-center" style="background-color: #a6d8a8; border-radius: 3px;">変更が完了しました</p>
			<div class="mt-4 mx-3 d-flex gap-3">
				<a href="../scoremanager/ClassList.action" class="btn btn-link">クラス一覧</a>
			</div>
		</section>
	</c:param>
</c:import>