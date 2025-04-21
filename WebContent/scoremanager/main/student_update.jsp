<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">学生情報変更</c:param>
  <c:param name="scripts"/>
  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">学生情報変更</h2>
      <form method="post" action="StudentUpdateExecute.action">
        <div class="row border mx-3 mb-3 py-3 px-2 rounded">

          <div class="col-12 mb-3">
            <label class="form-label" for="student-f1-select">入学年度</label><br>
            <input type="text" class="form-control form-control-lg" id="ent-year-input"
                   name="ent_year" value="${student.entYear}" hidden="hidden">
            ${student.entYear}
          </div>

          <div class="col-12 mb-3">
            <label class="form-label" for="student-no-input">学生番号</label><br>
            <input type="text" class="form-control form-control-lg" id="student-no-input"
                   name="student_no" value="${student.no}" hidden="hidden">
            ${student.no}
          </div>

          <div class="col-12 mb-3">
            <label class="form-label" for="student-name-input">氏名</label>
            <input type="text" class="form-control form-control-lg" id="student-name-input"
                   name="student_name" value="${student.name}" required>
          </div>

          <div class="col-12 mb-3">
            <label class="form-label" for="student-class-select">クラス</label>
            <select class="form-select form-select-lg" id="student-class-select" name="class_num">
              <c:forEach var="cnum" items="${class_num_set}">
                <option value="${cnum}" <c:if test="${cnum == student.classNum}">selected</c:if>>${cnum}</option>
              </c:forEach>
            </select>
          </div>

          <div class="col-12 mb-3 d-inline-flex align-items-center">
            <span class="me-2">在学中</span>
            <input class="form-check-input" type="checkbox" name="is_attend" id="attend-check" value="1"
                   <c:if test="${student.attend}">checked</c:if>>
          </div>

        </div>
        <div class="mx-3">
          <button type="submit" class="btn btn-primary">変更</button>
        </div>
      </form>

      <div class="mt-4 mx-3">
        <a href="StudentList.action" class="btn btn-secondary">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>