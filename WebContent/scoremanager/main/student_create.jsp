<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
	<c:param name="title">学生管理システム</c:param>
	<c:param name="scripts"/>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報登録</h2>
			<form method="post" action="StudentCreateExecute.action">
				<div class="row border mx-3 mb-3 py-3 px-2 rounded">
					<div class="col-12 mb-3">
						<label class="form-label" for="student-f1-select">入学年度</label>
						<select class="form-select form-select-lg" id="student-f1-select" name="ent_year">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}" <c:if test="${year == param.ent_year}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
						<!-- 入学年度に0が選択されている(「--------」が選択されたまま)のときのエラー -->
						<c:if test="${errors.ent_year_zero != null}">
							<div class="text-warning small mt-1">${errors.ent_year_zero}</div>
						</c:if>
					</div>
					<div class="col-12 mb-3">
						<label class="form-label" for="student-no-input">学生番号</label>
						<input type="text" class="form-control form-control-lg" id="student-no-input" name="student_no" value="${param.student_no}" placeholder="学生番号を入力してください" required>
						<!-- 入力された学生番号が既に使われているときのエラー -->
						<c:if test="${errors.student_no != null}">
							<div class="text-warning small mt-1">${errors.student_no}</div>
						</c:if>
					</div>
					<div class="col-12 mb-3">
						<label class="form-label" for="student-name-input">氏名</label>
						<input type="text" class="form-control form-control-lg" id="student-name-input" name="student_name" value="${param.student_name}" placeholder="氏名を入力してください" required>
					</div>
					<div class="col-12 mb-3">
						<label class="form-label" for="student-class-select">クラス</label>
						<select class="form-select form-select-lg" id="student-class-select" name="class_num">
							<c:forEach var="cnum" items="${class_num_set}" varStatus="status">
								<option value="${cnum}" <c:if test="${status.index == 0}">selected</c:if>>${cnum}</option>
							</c:forEach>
						</select>
					</div>
				</div>
				<div class="mx-3">
					<button type="submit" class="btn btn-primary">登録して終了</button>
				</div>
			</form>
			<div class="mt-4 mx-3">
				<a href="StudentList.action" class="btn btn-secondary">戻る</a>
			</div>
		</section>
	</c:param>
</c:import>