package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import dao.TestListSubjectDAO;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		String entYearStr = req.getParameter("f1");
		String classNum = req.getParameter("f2");
		String subjectCd = req.getParameter("f3");

		Teacher teacher = (Teacher) req.getSession().getAttribute("user");
		School school = teacher.getSchool();

		List<Integer> entYearSet = new ArrayList<>();
		int currentYear = LocalDate.now().getYear();
		for (int i = currentYear - 10; i <= currentYear; i++){
			entYearSet.add(i);
		}
		List<String> classNumSet = new ClassNumDAO().filter(school);
		List<Subject> subjectList = new SubjectDAO().filter(school);

		req.setAttribute("f1", entYearStr);
		req.setAttribute("f2", classNum);
		req.setAttribute("f3", subjectCd);
		req.setAttribute("ent_year_set", entYearSet);
		req.setAttribute("class_num_set", classNumSet);
		req.setAttribute("subject_list", subjectList);

		// 入力チェック
		if (entYearStr == null || entYearStr.equals("0") ||
			classNum == null || classNum.equals("0") ||
			subjectCd == null || subjectCd.equals("0")) {
			req.setAttribute("subjectError", "すべての項目を選択してください。");
			return "/scoremanager/main/test_list.jsp";
		}

		// データ取得処理
		int entYear = Integer.parseInt(entYearStr);
		Subject subject = new SubjectDAO().get(subjectCd, school);
		TestListSubjectDAO dao = new TestListSubjectDAO();
		List<TestListSubject> list = dao.filter(entYear, classNum, subject, school);

		req.setAttribute("subjectName", subject.getName());
		req.setAttribute("studentMap", list);
		req.setAttribute("searched", true);

		return "/scoremanager/main/test_list_subject.jsp";
	}
}