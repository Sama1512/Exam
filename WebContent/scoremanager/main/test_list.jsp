<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">テスト一覧</c:param>
  <c:param name="scripts"/>
  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">テスト一覧</h2>

      <!-- フィルター -->
      <form method="get" action="TestList.action">
        <div class="row border mx-3 mb-3 py-2 align-items-center rounded">
          <div class="col-3">
            <label class="form-label">入学年度</label>
            <select class="form-select" name="f1">
              <option value="0">--------</option>
              <c:forEach var="year" items="${ent_year_set}">
                <option value="${year}" <c:if test="${year==f1}">selected</c:if>>${year}</option>
              </c:forEach>
            </select>
          </div>
          <div class="col-3">
            <label class="form-label">クラス</label>
            <select class="form-select" name="f2">
              <option value="0">--------</option>
              <c:forEach var="num" items="${class_num_set}">
                <option value="${num}" <c:if test="${num==f2}">selected</c:if>>${num}</option>
              </c:forEach>
            </select>
          </div>
          <div class="col-3">
            <label class="form-label">科目</label>
            <select class="form-select" name="f3">
            <option value="0">--------</option>
              <c:forEach var="subject" items="${subject_list}">
                <option value="${subject.cd}" <c:if test="${subject.cd==f3}">selected</c:if>>${subject.name}</option>
              </c:forEach>
            </select>
          </div>
          <div class="col-2">
            <label class="form-label">テスト番号</label>
            <select class="form-select" name="f4">
            <option value="0" selected>--------</option>
              <c:forEach var="no" items="${no_list}">
                <option value="${no}" <c:if test="${no==f4}">selected</c:if>>${no}</option>
              </c:forEach>
            </select>
          </div>
          <div class="col-1 d-flex align-items-end">
            <button type="submit" class="btn btn-secondary w-100">検索</button>
          </div>
        </div>
      </form>

      <!-- 結果表示 -->
      <c:choose>
        <c:when test="${not empty test_list}">
          <div>検索結果：${test_list.size()} 件</div>
          <table class="table table-striped">
            <thead>
              <tr>
                <th>学生番号</th>
                <th>氏名</th>
                <th>クラス</th>
                <th>科目</th>
                <th>テスト番号</th>
                <th>点数</th>
              </tr>
            </thead>
            <tbody>
              <c:forEach var="test" items="${test_list}">
                <tr>
                  <td>${test.student.no}</td>
                  <td>${test.student.name}</td>
                  <td>${test.classNum}</td>
                  <td>${test.subject.name}</td>
                  <td>${test.no}</td>
                  <td>${test.point}</td>
                </tr>
              </c:forEach>
            </tbody>
          </table>
        </c:when>
        <c:otherwise>
          <div class="mt-4 text-muted">該当するテスト情報がありませんでした。</div>
        </c:otherwise>
      </c:choose>
    </section>
  </c:param>
</c:import>