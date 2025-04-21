<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">学生情報変更</c:param>
  <c:param name="scripts"/>
  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報変更</h2>
      <form method="post" action="SubjectUpdateExecute.action">
        <div class="row border mx-3 mb-3 py-3 px-2 rounded">

          <div class="col-12 mb-3">
            <label class="form-label" for="subject-f1-select">科目コード</label><br>
            <input type="text" class="form-control form-control-lg" id="subject-cd-input"
                   name="cd" value="${subject.cd}" hidden="hidden">
            ${subject.cd}
            <!-- 別画面で科目が削除されてしまったときのエラー -->
            <c:if test="${errors.cd != null}">
              <div class="text-warning small mt-1">${errors.cd}</div>
            </c:if>
          </div>

          <div class="col-12 mb-3">
            <label class="form-label" for="subject-name-input">科目名</label>
            <input type="text" class="form-control form-control-lg" id="subject-name-input"
                   name="name" value="${subject.name}" required>
          </div>

        </div>
        <div class="mx-3">
          <button type="submit" class="btn btn-primary">変更</button>
        </div>
      </form>

      <div class="mt-4 mx-3">
        <a href="SubjectList.action" class="btn btn-secondary">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>