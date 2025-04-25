package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDAO;
import tool.Action;

public class StudentCreateAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		ClassNumDAO cNumDAO = new ClassNumDAO();
		List<String> classNumSet = cNumDAO.filter(teacher.getSchool());

		List<Integer> entYearSet = new ArrayList<>();
		int currentYear = LocalDate.now().getYear();
		for (int i = currentYear - 10; i <= currentYear; i++) {
			entYearSet.add(i);
		}

		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);

		return "/scoremanager/main/student_create.jsp";
	}
}