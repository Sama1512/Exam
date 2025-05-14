<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">
		学生管理システム
	</c:param>
	<c:param name="scripts"/>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">メニュー</h2>
			<div class="row text-center px-4 fs-3 my-5">
				<div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow" style="height: 10rem; background-color: #f8d7da;">
					<a href="StudentList.action">学生管理</a>
				</div>
				<div class="col d-flex flex-column align-items-center justify-content-center mx-2 rounded shadow" style="height: 10rem; background-color: #d4edda;">
					<div class="mb-0">成績管理</div>
					<div><a href="TestRegist.action">成績登録</a></div>
					<div><a href="TestList.action">成績参照</a></div>
				</div>
				<div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow" style="height: 10rem; background-color: #d1ecf1;">
					<a href="SubjectList.action">科目管理</a>
				</div>
				<div class="col d-flex align-items-center justify-content-center mx-2 rounded shadow" style="height: 10rem; background-color: #fff3cd;">
					<a href="ClassList.action">クラス管理</a>
				</div>
			</div>
		</section>
	</c:param>
</c:import>