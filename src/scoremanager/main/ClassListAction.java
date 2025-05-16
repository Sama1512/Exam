package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDAO;
import tool.Action;

public class ClassListAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			// セッションからログインユーザーを取得
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher) session.getAttribute("user");
			req.setAttribute("user", teacher);

			System.out.println("ログイン教師の学校コード: " + teacher.getSchool().getCd());
			// 教師の学校に存在するクラスを取得
			ClassNumDAO classNumDAO = new ClassNumDAO();
			List<String> classNums = classNumDAO.filter(teacher.getSchool());

			req.setAttribute("classNums", classNums);

			return "/scoremanager/main/class_list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error.jsp";
		}
	}
}