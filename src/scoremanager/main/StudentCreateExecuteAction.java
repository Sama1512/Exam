package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.StudentDAO;
import tool.Action;

public class StudentCreateExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 入力値取得
		String entYearStr = req.getParameter("ent_year");
		String studentNo = req.getParameter("student_no");
		String studentName = req.getParameter("student_name");
		String classNum = req.getParameter("class_num");

		Map<String, String> errors = new HashMap<>();

		//入学年度に0が選択されている(「--------」が選択されたまま)のときのエラーを入れる処理
		if (entYearStr.equals("0")) {
			errors.put("ent_year_zero", "入学年度を選択してください");
		}

		//入力された学生番号が既に使われているときのエラーを入れる処理
		StudentDAO dao = new StudentDAO();
		if (dao.get(studentNo) != null) {
			errors.put("student_no", "この学生番号は既に使われています");
		}

		// エラーがある場合は再表示
		if (!errors.isEmpty()) {
			// 年度リスト
			List<Integer> entYearSet = new ArrayList<>();
			int currentYear = LocalDate.now().getYear();
			for (int i = currentYear - 10; i <= currentYear; i++) {
				entYearSet.add(i);
			}

			// クラスリスト
			ClassNumDAO cNumDAO = new ClassNumDAO();
			List<String> classNumSet = cNumDAO.filter(teacher.getSchool());

			req.setAttribute("errors", errors);
			req.setAttribute("ent_year_set", entYearSet);
			req.setAttribute("class_num_set", classNumSet);

			return "/scoremanager/main/student_create.jsp";
		}

		// 正常に登録出来る時の処理
		int entYear = Integer.parseInt(entYearStr);

		Student student = new Student();
		student.setNo(studentNo);
		student.setName(studentName);
		student.setEntYear(entYear);
		student.setClassNum(classNum);
		student.setSchool(teacher.getSchool());
		student.setAttend(true);
		dao.save(student);

		return "/scoremanager/main/student_create_done.jsp";
	}
}