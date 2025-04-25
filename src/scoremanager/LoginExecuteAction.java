package scoremanager;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDAO;
import tool.Action;

public class LoginExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();

		String id = request.getParameter("id");
		String password = request.getParameter("password");

		// 入力した値が間違えていた時にフォームに戻す用
		request.setAttribute("id", id);
		request.setAttribute("password", password);

		Map<String, String> errors = new HashMap<>();

		TeacherDAO dao = new TeacherDAO();
		Teacher teacher = dao.login(id, password);

		if (teacher != null) {
			teacher.setAuthenticated(true);
			System.out.println("ログインユーザー名: " + teacher.getName());
			session.setAttribute("user", teacher);
			return "/scoremanager/main/menu.jsp";
		} else {
			errors.put("login_error", "ログインに失敗しました。IDまたはパスワードが正しくありません。");
		}

		request.setAttribute("errors", errors);
		return "/scoremanager/login.jsp";
	}
}