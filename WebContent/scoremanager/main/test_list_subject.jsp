<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:import url="/common/base.jsp">
  <c:param name="title">学生管理システム</c:param>
  <c:param name="scripts"/>
  <c:param name="content">
    <section class="me-4">
      <h2 class="h3 mb-3 fw-normal bg-secondary bg-opacity-10 py-2 px-4">成績一覧(科目)</h2>

      <!-- 検索フォームの全体枠 -->
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
                    <input type="text" name="student_no" class="form-control" placeholder="学生番号を入力してください" value="${student.no}" required/>
                 </div>
                 <div>
                    <button type="submit" class="btn btn-secondary">検索</button>
                 </div>
              </div>
            </form>
         </div>
      </div>

      <!-- 検索結果表示 -->
      <c:choose>
         <c:when test="${not empty studentMap}">
            <c:if test="${not empty subjectName}">
            科目：${subjectName}
            </c:if>
            <table class="table table-striped">
               <thead>
                  <tr>
                     <th>入学年度</th>
                     <th>クラス</th>
                     <th>学生番号</th>
                     <th>氏名</th>
                     <th>1回</th>
                     <th>2回</th>
                  </tr>
               </thead>
               <tbody>
                  <c:forEach var="row" items="${studentMap}">
                     <tr>
                        <td>${row.entYear}</td>
                        <td>${row.classNum}</td>
                        <td>${row.studentNo}</td>
                        <td>${row.studentName}</td>
                        <td><c:out value="${row.points['1']}" default="―"/></td>
                        <td><c:out value="${row.points['2']}" default="―"/></td>
                     </tr>
                  </c:forEach>
               </tbody>
            </table>
         </c:when>

         <c:when test="${searched and empty studentMap}">
            <div class="mt-4 text-muted">学生情報が存在しませんでした。</div>
         </c:when>

        <c:otherwise>
           <div class="mt-4" style="color: #17a2b8;">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください。</div>
        </c:otherwise>
     </c:choose>
    </section>
  </c:param>
</c:import>