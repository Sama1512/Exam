<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
	<c:param name="title">学生情報変更</c:param>
	<c:param name="scripts"/>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報削除</h2>

			<form method="post" action="SubjectDeleteExecute.action">
				<div class="row border mx-3 mb-3 py-3 px-2 rounded">
					<div class="col-12 mb-3">
						<input type="text" class="form-control form-control-lg" id="subject-cd-input" name="cd" value="${subject.cd}" hidden="hidden">
						「${subject.name}(${subject.cd})」を削除してもよろしいですか？
						<!-- 別画面で科目が削除されてしまったときのエラー -->
						<c:if test="${errors.cd != null}">
							<div class="text-warning small mt-1">${errors.cd}</div>
						</c:if>
					</div>
				</div>

				<div class="mx-3">
					<button type="submit" class="btn btn-danger">削除</button>
				</div>
			</form>

			<div class="mt-4 mx-3">
				<a href="SubjectList.action" class="btn btn-secondary">戻る</a>
			</div>
		</section>
	</c:param>
</c:import>