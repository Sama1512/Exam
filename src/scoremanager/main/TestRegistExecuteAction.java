package scoremanager.main;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
import dao.StudentDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import tool.Action;

public class TestRegistExecuteAction extends Action {
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			int count = Integer.parseInt(req.getParameter("count"));
			TestDAO dao = new TestDAO();
			StudentDAO studentDAO = new StudentDAO();
			SubjectDAO subjectDAO = new SubjectDAO();

			Map<Integer, String> errors = new HashMap<>();
			List<Test> testList = new ArrayList<>();

			Connection con = null;

			try {
				con = dao.getConnection();
				con.setAutoCommit(false);

				for (int i = 0; i < count; i++) {
					String studentNo = req.getParameter("student_no_" + i);
					String subjectCd = req.getParameter("subject_cd_" + i);
					int no = Integer.parseInt(req.getParameter("no_" + i));
					String pointStr = req.getParameter("point_" + i);
					Integer point = null;

					if (pointStr != null && !pointStr.trim().isEmpty()) {
						try {
							point = Integer.parseInt(pointStr.trim());
							if (point < 0 || point > 100) {
								errors.put(i, "0～100の範囲で入力してください。");
							}
						} catch (NumberFormatException e) {
							errors.put(i, "点数は数値で入力してください。");
						}
					}

					Student student = studentDAO.get(studentNo);
					if (student == null) {
						errors.put(i, "学生情報が見つかりません。");
						continue;
					}

					Subject subject = subjectDAO.get(subjectCd, student.getSchool());
					School school = student.getSchool();
					Test test = dao.get(student, subject, school, no);

					if (test == null) {
						errors.put(i, "この成績情報は存在しません。新規登録は「新規登録」画面から行ってください。");
						continue;
					}

					test.setPoint(point);
					testList.add(test);

					if (!errors.containsKey(i)) {
						dao.save(test, con);
					}
				}

				if (!errors.isEmpty()) {
					req.setAttribute("test_list", testList);
					req.setAttribute("errors", errors);
					return "/scoremanager/main/test_regist.jsp";
				}

				con.commit();
			} catch (Exception e) {
				if (con != null) con.rollback();
				throw e;
			} finally {
				if (con != null) con.close();
			}

			return "/scoremanager/main/test_regist_done.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error.jsp";
		}
	}
}