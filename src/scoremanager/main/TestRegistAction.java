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
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import tool.Action;

public class TestRegistAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher) session.getAttribute("user");
			School school = teacher.getSchool();

			ClassNumDAO classNumDAO = new ClassNumDAO();
			SubjectDAO subjectDAO = new SubjectDAO();
			TestDAO testDAO = new TestDAO();

			//パラメータ取得
			String entYearStr = req.getParameter("f1");
			String classNum = req.getParameter("f2");
			String subjectCd = req.getParameter("f3");
			String testNumStr = req.getParameter("f4");

			int entYear = 0;
			int testNum = 0;
			Map<String, String> errors = new HashMap<>();
			List<Test> testList = new ArrayList<>();

			//年度・クラス・科目・回数のいずれかが未選択の場合はエラー
			boolean isInputValid = true;
			if (entYearStr == null || entYearStr.equals("0") ||
				classNum == null || classNum.equals("0") ||
				subjectCd == null || subjectCd.equals("0") ||
				testNumStr == null || testNumStr.equals("0")) {
				errors.put("filter", "すべての検索条件を選択してください。");
				isInputValid = false;
			}

			//入力がすべて揃っていれば取得・検索
			if (isInputValid) {
				try {
					entYear = Integer.parseInt(entYearStr.trim());
					testNum = Integer.parseInt(testNumStr.trim());
				} catch (NumberFormatException e) {
					errors.put("filter", "検索条件に不正な値があります。");
					isInputValid = false;
				}

				Subject subject = subjectDAO.get(subjectCd, school);

				if (isInputValid) {
					testList = testDAO.filter(entYear, classNum, subject, testNum, school);
				}
			}

			//年度リスト(10年前～現在)
			List<Integer> entYearSet = new ArrayList<>();
			int currentYear = LocalDate.now().getYear();
			for (int i = currentYear - 10; i <= currentYear; i++) {
				entYearSet.add(i);
			}

			//テスト番号リスト(1,2)
			List<Integer> noList = new ArrayList<>();
			for (int i = 1; i <= 2; i++) {
				noList.add(i);
			}

			//リクエストにセット
			req.setAttribute("f1", entYearStr != null ? Integer.parseInt(entYearStr) : 0);
			req.setAttribute("f2", classNum);
			req.setAttribute("f3", subjectCd);
			req.setAttribute("f4", testNumStr != null ? Integer.parseInt(testNumStr) : 0);
			req.setAttribute("test_list", testList);
			req.setAttribute("ent_year_set", entYearSet);
			req.setAttribute("class_num_set", classNumDAO.filter(school));
			req.setAttribute("subject_list", subjectDAO.filter(school));
			req.setAttribute("no_list", noList);
			req.setAttribute("errors", errors);

			//検索が実行されたことのフラグ
			if (entYearStr != null || classNum != null || subjectCd != null || testNumStr != null) {
				req.setAttribute("searched", true);
			}

			return "/scoremanager/main/test_regist.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error.jsp";
		}
	}
}
