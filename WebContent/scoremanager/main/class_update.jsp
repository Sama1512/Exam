<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/common/base.jsp">
	<c:param name="title">学生管理システム</c:param>
	<c:param name="scripts"/>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">クラス情報変更</h2>

			<form method="post" action="ClassUpdateExecute.action">
				<div class="row border mx-3 mb-3 py-3 px-2 rounded">

					<!-- 新クラス番号 -->
					<div class="col-12 mb-3">
						<label class="form-label" for="class-num-input">クラス番号</label>
						<input type="text" class="form-control form-control-lg" id="class-num-input"
						       name="class_num" value="${classNum.classNum}" required>

						<!-- エラーメッセージ表示 -->
						<c:if test="${errors.class_num != null}">
							<div class="text-warning small mt-1">${errors.class_num}</div>
						</c:if>
					</div>
				</div>

				<!-- 前のクラス番号を送信する -->
				<input type="hidden" name="old_class_num" value="${classNum.classNum}">

				<input type="hidden" name="school_cd" value="${school_cd}">

				<div class="mx-3">
					<button type="submit" class="btn btn-primary">変更</button>
				</div>
			</form>

			<div class="mt-4 mx-3">
				<a href="ClassList.action" class="btn btn-secondary">戻る</a>
			</div>
		</section>
	</c:param>
</c:import>
