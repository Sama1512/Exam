package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectDeleteExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 入力値取得
		String cd = req.getParameter("cd");

		SubjectDAO dao = new SubjectDAO();

		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setSchool(teacher.getSchool());
		dao.delete(subject);

		return "/scoremanager/main/subject_delete_done.jsp";
	}
}