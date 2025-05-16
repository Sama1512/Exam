package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDAO;
import tool.Action;

public class StudentDeleteExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			HttpSession session = req.getSession();
			@SuppressWarnings("unused")
			Teacher teacher = (Teacher) session.getAttribute("user");

			// 入力値取得
			String no = req.getParameter("no");

			StudentDAO dao = new StudentDAO();

			Student student= new Student();
			student.setNo(no);
			dao.delete(student);

			return "/scoremanager/main/student_delete_done.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error.jsp";
		}
	}
}