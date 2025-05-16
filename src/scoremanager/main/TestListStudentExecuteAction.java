package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListStudent;
import dao.ClassNumDAO;
import dao.StudentDAO;
import dao.SubjectDAO;
import dao.TestListStudentDAO;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			String studentNo = req.getParameter("student_no");

			Teacher teacher = (Teacher) req.getSession().getAttribute("user");
			School school = teacher.getSchool();

			// 年度、クラス、科目リストをいつでも検索できるように入れる
			List<Integer> entYearSet = new ArrayList<>();
			int currentYear = LocalDate.now().getYear();
			for (int i = currentYear - 10; i <= currentYear; i++) entYearSet.add(i);

			SubjectDAO subjectDAO = new SubjectDAO();
			List<Subject> subjectList = subjectDAO.filter(school);
			req.setAttribute("ent_year_set", entYearSet);
			req.setAttribute("class_num_set", new ClassNumDAO().filter(school));
			req.setAttribute("subject_list", subjectList);

			req.setAttribute("f1", null);
			req.setAttribute("f2", null);
			req.setAttribute("f3", null);

			studentNo = studentNo.trim();
			req.setAttribute("student_no", studentNo);

			StudentDAO studentDAO = new StudentDAO();
			Student student = studentDAO.get(studentNo);

			//入力された学生番号に該当する学生がいないor入力された学生番号の学生が削除されている(delete_flag = trueの)場合
			if (student == null) {
				req.setAttribute("error", "学生が見つかりません。");
				return "/scoremanager/main/test_list_student.jsp";
			}

			TestListStudentDAO testListStudentDAO = new TestListStudentDAO();
			List<TestListStudent> list = testListStudentDAO.filter(student);

			//入力された学生番号の学生に成績情報が一つも登録されていない場合
			if (list == null || list.isEmpty()) {
				//名前と番号はあるので入れる
				req.setAttribute("studentName", student.getName());
				req.setAttribute("studentNo", student.getNo());
				req.setAttribute("error", "この学生の成績情報は登録されていません。");
				return "/scoremanager/main/test_list_student.jsp";
			}

			req.setAttribute("subjectMap", list);
			req.setAttribute("studentName", student.getName());
			req.setAttribute("studentNo", student.getNo());
			req.setAttribute("searched", true);

			return "/scoremanager/main/test_list_student.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error.jsp";
		}
	}
}