package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import tool.Action;

public class ClassCreateAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher) session.getAttribute("user");

			req.setAttribute("school_cd", teacher.getSchool().getCd());

			return "/scoremanager/main/class_create.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error.jsp";
		}
	}
}