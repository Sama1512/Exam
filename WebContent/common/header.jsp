<%-- ヘッダー --%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<div class="d-flex align-items-center mb-3 mb-md-0 me-md-auto text-dark text-decoration-none">
	<h1 class="fs-1">学生管理システム</h1>
</div>
<c:if test="${user != null && user.authenticated}">
	<!-- ログイン中のユーザー向け表示 -->
	<div class="nav align-self-end">
		<span class="nav-item px-2">${user.name}様</span>
		<a class="nav-item px-2" href="Logout.action">ログアウト</a>
	</div>
</c:if>