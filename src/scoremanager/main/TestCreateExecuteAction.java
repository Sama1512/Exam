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

public class TestCreateExecuteAction extends Action {
	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		int count = Integer.parseInt(req.getParameter("count"));
		TestDAO testDAO = new TestDAO();
		StudentDAO studentDAO = new StudentDAO();
		SubjectDAO subjectDAO = new SubjectDAO();

		List<Test> testList = new ArrayList<>();
		Map<Integer, String> errors = new HashMap<>();

		Connection con = null;

		try {
			con = testDAO.getConnection();
			con.setAutoCommit(false);

			for (int i = 0; i < count; i++) {
				String studentNo = req.getParameter("student_no_" + i);
				String subjectCd = req.getParameter("subject_cd_" + i);
				int no = Integer.parseInt(req.getParameter("no_" + i));
				String pointStr = req.getParameter("point_" + i);

				//入力されていなければスキップ(登録しない)
				if (pointStr == null || pointStr.trim().isEmpty()) {
					continue;
				}

				Integer point = null;
				try {
					point = Integer.parseInt(pointStr.trim());
					if (point < 0 || point > 100) {
						errors.put(i, "0～100の範囲で入力してください。");
						continue;
					}
				} catch (NumberFormatException e) {
					errors.put(i, "点数は数値で入力してください。");
					continue;
				}

				Student student = studentDAO.get(studentNo);
				Subject subject = subjectDAO.get(subjectCd, student.getSchool());
				School school = student.getSchool();

				Test test = new Test();
				test.setStudent(student);
				test.setSubject(subject);
				test.setSchool(school);
				test.setClassNum(student.getClassNum());
				test.setNo(no);
				test.setPoint(point);

				testList.add(test);

				testDAO.save(test, con);
			}

			if (!errors.isEmpty()) {
				req.setAttribute("test_list", testList);
				req.setAttribute("errors", errors);
				return "/scoremanager/main/test_create.jsp";
			}

			con.commit();
			return "/scoremanager/main/test_create_done.jsp";
		} catch (Exception e) {
			if (con != null) con.rollback();
			e.printStackTrace();
			req.setAttribute("error", "成績の登録中にエラーが発生しました: " + e.getMessage());
			return "/error.jsp";
		} finally {
			if (con != null) con.close();
		}
	}
}