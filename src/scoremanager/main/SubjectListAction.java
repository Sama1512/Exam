package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectListAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			// セッションからログインユーザーを取得
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher) session.getAttribute("user");
			req.setAttribute("user", teacher);

			System.out.println("ログイン教師の学校コード: " + teacher.getSchool().getCd());
			// 教師の学校に所属する科目を取得
			SubjectDAO subjectDAO = new SubjectDAO();
			List<Subject> subjects = subjectDAO.filter(teacher.getSchool());

			req.setAttribute("subjects", subjects);

			return "/scoremanager/main/subject_list.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error.jsp";
		}
	}
}