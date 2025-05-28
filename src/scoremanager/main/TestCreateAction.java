package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDAO;
import dao.StudentDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import tool.Action;

public class TestCreateAction extends Action {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		if (teacher == null) {
			req.setAttribute("error", "ログイン情報が見つかりませんでした。ログインし直してください。");
			return "/error.jsp";
		}

		School school = teacher.getSchool();
		if (school == null) {
			req.setAttribute("error", "教師に学校情報が紐づいていません。管理者にお問い合わせください。");
			return "/error.jsp";
		}

		ClassNumDAO classNumDAO = new ClassNumDAO();
		SubjectDAO subjectDAO = new SubjectDAO();
		StudentDAO studentDAO = new StudentDAO();
		TestDAO testDAO = new TestDAO();

		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subjectCd = req.getParameter("f3");

		Map<String, String> errors = new HashMap<>();
		List<Test> testList = new ArrayList<>();

		boolean isValid = entYearStr != null && !entYearStr.equals("0") &&
				classNum != null && !classNum.equals("0") &&
				subjectCd != null && !subjectCd.equals("0");

		if (!isValid) {
			errors.put("filter", "すべての検索条件を選択してください。");
		} else {
			try {
				int entYear = Integer.parseInt(entYearStr.trim());
				Subject subject = subjectDAO.get(subjectCd, school);

				if (subject == null) {
					errors.put("filter", "指定された科目が見つかりません");
				} else {
					List<Student> students = studentDAO.filter(school, entYear, classNum, true);

					if (students == null || students.isEmpty()) {
						errors.put("filter", "該当する学生が存在しませんでした");
					} else {
						for (Student student : students) {
							for (int testNo = 1; testNo <= 2; testNo++) {
								Test test = testDAO.get(student, subject, school, testNo);
								if (test == null) {
									test = new Test();
									test.setStudent(student);
									test.setSubject(subject);
									test.setSchool(school);
									test.setClassNum(classNum);
									test.setNo(testNo);
									test.setPoint(null);
								}
								testList.add(test);
							}
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				errors.put("filter", "検索中に例外が発生しました");
			}
		}

		List<Integer> entYearSet = new ArrayList<>();
		int currentYear = LocalDate.now().getYear();
		for (int i = currentYear - 10; i <= currentYear; i++) {
			entYearSet.add(i);
		}

		req.setAttribute("f1", entYearStr != null ? Integer.parseInt(entYearStr) : 0);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);
		req.setAttribute("test_list", testList);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumDAO.filter(school));
		req.setAttribute("subject_list", subjectDAO.filter(school));
		req.setAttribute("errors", errors);
		req.setAttribute("searched", true);

		return "/scoremanager/main/test_create.jsp";
	}
}