<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/common/base.jsp">
  <c:param name="title">テスト一覧</c:param>
  <c:param name="scripts"/>
  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">テスト一覧</h2>

<h5 class="ms-3 mt-3">科目：${subject.name}（${test_no}回）</h5>

<form method="post" action="TestRegistExecute.action">
  <table class="table table-bordered text-center align-middle">
    <thead class="table-light">
      <tr>
        <th>入学年度</th>
        <th>クラス</th>
        <th>学生番号</th>
        <th>氏名</th>
        <th>点数</th>
      </tr>
    </thead>
    <tbody>
      <c:forEach var="test" items="${test_list}" varStatus="i">
        <tr>
          <td>${test.student.entYear}</td>
          <td>${test.classNum}</td>
          <td>
            ${test.student.no}
            <input type="hidden" name="tests[${i.index}].student.no" value="${test.student.no}" />
          </td>
          <td>${test.student.name}</td>
          <td>
            <input type="number" name="tests[${i.index}].point"
                   class="form-control" min="0" max="100"
                   value="${test.point}" />
            <input type="hidden" name="tests[${i.index}].classNum" value="${test.classNum}" />
            <input type="hidden" name="tests[${i.index}].subject.cd" value="${test.subject.cd}" />
            <input type="hidden" name="tests[${i.index}].school.cd" value="${test.school.cd}" />
            <input type="hidden" name="tests[${i.index}].no" value="${test.no}" />
          </td>
        </tr>
      </c:forEach>
    </tbody>
  </table>

  <div class="text-center mb-3">
    <button type="submit" class="btn btn-primary">登録</button>
  </div>
</form>

</section>
</c:param>
</c:import>