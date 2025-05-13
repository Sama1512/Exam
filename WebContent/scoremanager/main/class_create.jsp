<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
	<c:param name="title">学生管理システム</c:param>
	<c:param name="scripts"/>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス情報登録</h2>

			<form method="post" action="ClassCreateExecute.action">
				<div class="col-12 mb-3">
					<label class="form-label" for="class-num-input">クラス番号</label>
					<input type="text" class="form-control form-control-lg" id="class-num-input" name="class_num" value="${param.class_num}" placeholder="クラス番号を入力してください" required>
					<!-- 入力されたクラス番号が既に使われているときのエラー -->
					<c:if test="${errors.class_num != null}">
						<div class="text-warning small mt-1">${errors.class_num}</div>
					</c:if>
				</div>

				<input type="hidden" name="school_cd" value="${school_cd}">

				<div class="mx-3">
					<button type="submit" class="btn btn-primary">登録</button>
				</div>
			</form>

			<div class="mt-4 mx-3">
				<a href="ClassList.action" class="btn btn-secondary">戻る</a>
			</div>
		</section>
	</c:param>
</c:import>