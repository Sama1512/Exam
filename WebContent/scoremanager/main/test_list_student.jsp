<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
	<c:param name="title">学生管理システム</c:param>
	<c:param name="scripts">
		<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
		<script src="https://cdn.jsdelivr.net/npm/chartjs-plugin-datalabels@2"></script>
	</c:param>
	<c:param name="content">
		<section class="me-4">
			<h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧(学生)</h2>

			<div class="border mx-3 mb-3 p-3 rounded d-flex flex-column gap-4">
				<!-- 科目情報 -->
				<div class="d-flex align-items-center gap-3">
					<div class="fw-bold" style="min-width: 90px;">科目情報</div>
					<form method="get" action="TestListSubjectExecute.action" class="flex-grow-1">
						<div class="d-flex flex-wrap gap-3 align-items-end">
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
								<select class="form-select" name="f3" style="width: 328px;">
									<option value="0">--------</option>
									<c:forEach var="subject" items="${subject_list}">
										<option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
									</c:forEach>
								</select>
							</div>
							<div>
								<button type="submit" class="btn btn-secondary mt-4">検索</button>
							</div>
						</div>
					</form>
				</div>

				<hr>

				<!-- 学生情報 -->
				<div class="d-flex align-items-center gap-3">
					<div class="fw-bold" style="min-width: 90px;">学生情報</div>
					<form method="get" action="TestListStudentExecute.action" class="flex-grow-1">
						<div class="d-flex gap-3 align-items-end">
							<div class="flex-grow-1">
								<label class="form-label">学生番号</label>
								<input type="text" name="student_no" class="form-control" placeholder="学生番号を入力してください" value="${student_no}" required/>
							</div>
							<div>
								<button type="submit" class="btn btn-secondary">検索</button>
							</div>
						</div>
					</form>
				</div>
			</div>

			<c:choose>
				<c:when test="${not empty subjectMap}">
					<c:if test="${not empty studentName}">
						氏名：${studentName}(${studentNo})
					</c:if>
					<table class="table table-striped">
						<thead>
							<tr>
								<th>科目名</th>
								<th>科目コード</th>
								<th>回数</th>
								<th>点数</th>
							</tr>
						</thead>
						<tbody>
							<c:forEach var="row" items="${subjectMap}">
								<tr>
									<td>${row.subjectName}</td>
									<td>${row.subjectCd}</td>
									<td>${row.num}</td>
									<td>${row.point}</td>
								</tr>
							</c:forEach>
						</tbody>
					</table>

					<c:if test="${not empty graphData}">
						<div class="d-flex gap-4 mt-4">
							<div class="flex-fill text-center">
								<h5 class="mb-2">点数分布（科目数）</h5>
								<canvas id="barChart" style="max-width: 400px; height: 300px;"></canvas>
							</div>
							<div class="flex-fill text-center">
								<h5 class="mb-2">点数分布（割合）</h5>
								<canvas id="pieChart" style="max-width: 400px; height: 300px;"></canvas>
							</div>
						</div>

						<script>
							const labels = [<c:forEach var="entry" items="${graphData}" varStatus="status">'${entry.key}'<c:if test="${!status.last}">,</c:if></c:forEach>];
							const data = [<c:forEach var="entry" items="${graphData}" varStatus="status">${entry.value}<c:if test="${!status.last}">,</c:if></c:forEach>];

							new Chart(document.getElementById('barChart'), {
								type: 'bar',
								data: {
									labels: labels,
									datasets: [{ label: '科目数', data: data }]
								},
								options: {
									plugins: {
										datalabels: {
											formatter: function(value) { return value + ' 件'; },
											font: { weight: 'bold' }
										},
										tooltip: {
											callbacks: {
												label: function(ctx) {
													return ctx.dataset.label + ': ' + Math.floor(ctx.parsed.y) + ' 件';
												}
											}
										}
									},
									scales: {
										y: {
											beginAtZero: true,
											ticks: {
												precision: 0
											}
										}
									}
								},
								plugins: [ChartDataLabels]
							});

							new Chart(document.getElementById('pieChart'), {
								type: 'pie',
								data: {
									labels: labels,
									datasets: [{ label: '割合', data: data }]
								},
								options: {
									plugins: {
										datalabels: {
											display: function(ctx) { return ctx.dataset.data[ctx.dataIndex] > 0; },
											formatter: function(value, ctx) {
												const total = ctx.chart.data.datasets[0].data.reduce(function(a, b) { return a + b; }, 0);
												const percent = total === 0 ? 0 : ((value / total) * 100).toFixed(1);
												return value + ' 件 (' + percent + '%)';
											},
											color: '#000',
											font: { weight: 'bold', size: 12 }
										},
										tooltip: {
											callbacks: {
												label: function(ctx) {
													return ctx.label + ': ' + Math.floor(ctx.parsed) + ' 件';
												}
											}
										}
									}
								},
								plugins: [ChartDataLabels]
							});
						</script>
					</c:if>
				</c:when>
				<c:when test="${not empty error}">
					<c:if test="${not empty studentName}">
						氏名：${studentName}(${studentNo})
					</c:if>
					<div class="mt-4 text-muted">${error}</div>
				</c:when>
				<c:otherwise>
					<div class="mt-4" style="color: #17a2b8;">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください。</div>
				</c:otherwise>
			</c:choose>
		</section>
	</c:param>
</c:import>