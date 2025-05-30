package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectUpdateAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher) session.getAttribute("user");

			// 変更対象の科目コードを取得
			String cd = req.getParameter("cd");

			// 科目情報を取得
			SubjectDAO subjectDAO = new SubjectDAO();
			Subject subject = subjectDAO.get(cd, teacher.getSchool());

			// 取得したデータをセット
			req.setAttribute("subject", subject);

			return "/scoremanager/main/subject_update.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error.jsp";
		}
	}
}