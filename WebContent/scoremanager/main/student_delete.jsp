<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
	<c:param name="title">学生情報変更</c:param>
	<c:param name="scripts"/>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報削除</h2>
			<form method="post" action="StudentDeleteExecute.action">
				<div class="row border mx-3 mb-3 py-3 px-2 rounded">

					<div class="col-12 mb-3">
						<input type="text" class="form-control form-control-lg" id="student-no-input" name="no" value="${student.no}" hidden="hidden">
						学生番号：${student.no}<br>
						学生名：${student.name}<br>
						削除してもよろしいですか？

						<!-- 別画面で学生が削除されてしまったときのエラー -->
						<c:if test="${errors.no != null}">
							<div class="text-warning small mt-1">${errors.no}</div>
						</c:if>
					</div>
				</div>
				<div class="mx-3">
					<button type="submit" class="btn btn-danger">削除</button>
				</div>
			</form>

			<div class="mt-4 mx-3">
				<a href="StudentList.action" class="btn btn-secondary">戻る</a>
			</div>
		</section>
	</c:param>
</c:import>