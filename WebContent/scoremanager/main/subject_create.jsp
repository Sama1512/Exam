<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">学生管理システム</c:param>
  <c:param name="scripts"/>
  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">科目情報登録</h2>
      <form method="post" action="SubjectCreateExecute.action">
          <div class="col-12 mb-3">
            <label class="form-label" for="subject-cd-input">科目コード</label>
            <input type="text" class="form-control form-control-lg" id="subject-cd-input" name="cd"
                   value="${param.cd}" placeholder="科目コードを入力してください" required>
            <!-- 入力された科目コードが既に使われているときのエラー -->
            <c:if test="${errors.cd != null}">
              <div class="text-warning small mt-1">${errors.cd}</div>
            </c:if>
          </div>
          <div class="col-12 mb-3">
            <label class="form-label" for="subject-name-input">科目名</label>
            <input type="text" class="form-control form-control-lg" id="subject-name-input" name="name"
                   value="${param.name}" placeholder="科目名を入力してください" required>
          </div>
          <input type="hidden" name="school_cd" value="${school_cd}">
        <div class="mx-3">
          <button type="submit" class="btn btn-primary">登録</button>
        </div>
      </form>
      <div class="mt-4 mx-3">
        <a href="SubjectList.action" class="btn btn-secondary">戻る</a>
      </div>
    </section>
  </c:param>
</c:import>