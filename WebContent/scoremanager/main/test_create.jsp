<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
	<c:param name="title">学生管理システム</c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績新規作成</h2>

			<!-- 検索フォーム -->
			<form method="get" action="TestCreate.action">
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
					<div>
						<label class="form-label">科目</label>
						<select class="form-select" name="f3">
							<option value="0">--------</option>
							<c:forEach var="subject" items="${subject_list}">
								<option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
							</c:forEach>
						</select>
					</div>
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

			<!-- 検索結果表示 -->
			<c:if test="${searched and empty errors['filter']}">
				<form method="post" action="TestCreateExecute.action">
					<table class="table table-striped">
						<thead>
							<tr>
								<th>入学年度</th>
								<th>クラス</th>
								<th>学生番号</th>
								<th>氏名</th>
								<th>1回目</th>
								<th>2回目</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="i" begin="0" end="${fn:length(test_list) - 1}" step="2">
								<tr>
									<td>${test_list[i].student.entYear}</td>
									<td>${test_list[i].classNum}</td>
									<td>${test_list[i].student.no}</td>
									<td>${test_list[i].student.name}</td>

									<!-- 1回目 -->
									<td>
										<input type="number" name="point_${i}" class="form-control" min="0" max="100" value="${test_list[i].point != null ? test_list[i].point : ''}" <c:if test="${test_list[i].point != null}">readonly</c:if> />
										<input type="hidden" name="student_no_${i}" value="${test_list[i].student.no}" />
										<input type="hidden" name="subject_cd_${i}" value="${test_list[i].subject.cd}" />
										<input type="hidden" name="no_${i}" value="${test_list[i].no}" />
									</td>

									<!-- 2回目 -->
									<td>
										<c:if test="${i + 1 < fn:length(test_list)}">
											<input type="number" name="point_${i + 1}" class="form-control" min="0" max="100" value="${test_list[i + 1].point != null ? test_list[i + 1].point : ''}" <c:if test="${test_list[i + 1].point != null}">readonly</c:if> />
											<input type="hidden" name="student_no_${i + 1}" value="${test_list[i + 1].student.no}" />
											<input type="hidden" name="subject_cd_${i + 1}" value="${test_list[i + 1].subject.cd}" />
											<input type="hidden" name="no_${i + 1}" value="${test_list[i + 1].no}" />
										</c:if>
									</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>
					<input type="hidden" name="count" value="${fn:length(test_list)}" />
					<div class="mt-3">
						<button type="submit" class="btn btn-primary">登録して終了</button>
					</div>
				</form>
			</c:if>
		</section>
	</c:param>
</c:import>