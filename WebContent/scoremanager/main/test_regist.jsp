<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
	<c:param name="title">学生管理システム</c:param>
	<c:param name="scripts"/>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績管理</h2>

			<!-- 検索 -->
			<form method="get" action="TestRegist.action">
				<div class="d-flex flex-wrap gap-3 align-items-end border mx-3 mb-3 p-3 rounded">
					<div>
						<label class="form-label">入学年度</label>
						<select class="form-select" name="f1">
							<option value="0">--------</option>
							<c:forEach var="year" items="${ent_year_set}">
								<option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
							</c:forEach>
						</select>
					</div>

					<div>
						<label class="form-label">クラス</label>
						<select class="form-select" name="f2">
							<option value="0">--------</option>
							<c:forEach var="num" items="${class_num_set}">
								<option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-4">
						<label class="form-label">科目</label>
						<select class="form-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subject_list}">
								<option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-2">
						<label class="form-label">回数</label>
						<select class="form-select" name="f4">
							<option value="0" selected>--------</option>
							<c:forEach var="no" items="${no_list}">
								<option value="${no}" <c:if test="${no==f4}">selected</c:if>>${no}</option>
							</c:forEach>
						</select>
					</div >

					<div class="col-1 d-flex align-items-end">
					<button type="submit" class="btn btn-secondary mt-4">検索</button>
					</div>
				</div>
			</form>
			<c:if test="${searched and not empty errors['filter']}">
				<div class="alert alert-warning mx-3">
					${errors['filter']}
				</div>
			</c:if>


			<!-- 結果表示 -->
			<c:choose>
				<c:when test="${not empty test_list}">
					<c:forEach var="test" items="${test_list}" varStatus="status">
						<c:if test="${status.first}">
							<div class="mb-3">科目：${test.subject.name}（${test.no}回）</div>
						</c:if>
					</c:forEach>
					<form method="post" action="TestRegistExecute.action">
						<table class="table table-striped">
							<thead>
								<tr>
									<th>入学年度</th>
									<th>クラス</th>
									<th>学生番号</th>
									<th>氏名</th>
									<th>点数</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="test" items="${test_list}" varStatus="status">
									<tr>
										<td>${test.student.entYear}</td>
										<td>${test.classNum}</td>
										<td>${test.student.no}</td>
										<td>${test.student.name}</td>
										<td>
											<input type="number" name="point_${status.index}" class="form-control" value="${test.point}" required />
											<!-- 入力された得点が０未満または100を超えているときのエラー -->
											<c:if test="${not empty errors[status.index]}">
												<div class="text-warning small mt-1">${errors[status.index]}</div>
											</c:if>
											<input type="hidden" name="student_no_${status.index}" value="${test.student.no}" />
											<input type="hidden" name="subject_cd_${status.index}" value="${test.subject.cd}" />
											<input type="hidden" name="no_${status.index}" value="${test.no}" />
										</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
						<input type="hidden" name="count" value="${fn:length(test_list)}" />
						<div class="mt-3">
							<button type="submit" class="btn btn-secondary">登録して終了</button>
						</div>
					</form>
				</c:when>
				<c:otherwise>
					<c:if test="${empty errors['filter']}">
						<c:if test="${searched}">
							<div class="mt-4 text-muted">該当するテスト情報が存在しませんでした。</div>
						</c:if>
					</c:if>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>