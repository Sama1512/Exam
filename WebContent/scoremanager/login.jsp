<%-- ログインJSP --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/common/base.jsp">
	<c:param name="title">得点管理システム</c:param>

	<c:param name="scripts">
		<script>
			function togglePassword() {
				const pw = document.getElementById("password-input");
				pw.type = pw.type === "password" ? "text" : "password";
			}
		</script>
	</c:param>

	<c:param name="content">
		<section class="w-75 text-center m-auto border pb-3">
			<form action = "LoginExecute.action" method="post">
				<div id="wrap_box">
					<h2 class="h3 mb-3 fw-norma bg-secondary bg-opacity-10 py-2">ログイン</h2>
					<c:if test="${errors.login_error != null}">
						<div class="small mt-1">
							<ul>
								<li>
									${errors.login_error}
								</li>
							</ul>
						</div>
					</c:if>
					<div>
						<!-- ＩＤ -->
						<div class="form-floating mx-5">
							<input class="form-control px-5 fs-5" autocomplete="off"
								id="id-input" maxlength="20" name="id" placeholder="半角でご入力下さい"
								style="ime-mode: disabled" type="text" value="${id}" required />
							<label>ＩＤ</label>
						</div>
						<!-- パスワード -->
						<div class="form-floating mx-5 mt-3">
							<input class="form-control px-5 fs-5" autocomplete="off"
								id="password-input" maxlength="20" name="password"
								placeholder="20文字以内の半角英数字でご入力下さい" style="ime-mode: disabled"
								type="password" value="${password}" required />
							<label>パスワード</label>
						</div>
						<div class="form-check mt-3">
							<label class="form-check-label" for="password-display">
								<input class="form-check-input" id="password-display" name="chk_d_ps" type="checkbox" onclick="togglePassword()" />
								パスワードを表示
							</label>
						</div>
					</div>

					<div class="mt-4">
						<input class="w-25 btn btn-lg btn-primary" type="submit" name="login" value="ログイン"/>
					</div>
				</div>
			</form>
		</section>
	</c:param>
</c:import>