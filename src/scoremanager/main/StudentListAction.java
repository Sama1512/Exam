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

public class StudentListAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse response) throws Exception {
		HttpSession session = req.getSession(); // セッション
		Teacher teacher = (Teacher) session.getAttribute("user");
		req.setAttribute("user", teacher);

		String entYearStr = "";     // 入力された入学年度
		String classNum = "";       // 入力されたクラス番号
		String isAttendStr = "";    // 入力された在学フラグ
		int entYear = 0;            // 入学年度
		boolean isAttend = false;   // 在学フラグ
		List<Student> students = null; // 学生リスト
		LocalDate todaysDate = LocalDate.now();       // LocalDateインスタンスを取得
		int year = todaysDate.getYear();              // 現在の年を取得
		StudentDAO sDAO = new StudentDAO();           // 学生DAO
		ClassNumDAO cNumDAO = new ClassNumDAO();      // クラス番号
		Map<String,String> errors = new HashMap<>();      //エラーメッセージ

		// リクエストパラメーターの取得 2
		entYearStr = req.getParameter("f1");
		classNum = req.getParameter("f2");
		isAttendStr = req.getParameter("f3");

		if (entYearStr != null && !entYearStr.equals("0")) {
			entYear = Integer.parseInt(entYearStr);
		}

		if (classNum == null || classNum.equals("0")) {
			classNum = null;
		}

		// リストを初期化
		List<Integer> entYearSet = new ArrayList<>();
		// 10年前から1年後まで年をリストに追加
		for (int i = year - 10; i < year + 1; i++) {
			entYearSet.add(i);
		}

		 // DBからデータ取得 3
		 // ログインユーザーの学校コードをもとにクラス番号の一覧を取得
		 List<String> list = cNumDAO.filter(teacher.getSchool());

		// 在学フラグが送信されていた場合
		 if (isAttendStr != null) {
			 isAttend = true;
			 req.setAttribute("f3", isAttendStr);
		 }

		 if (entYear != 0 && classNum != null) {
			 // 入学年度とクラス番号を指定
			 students = sDAO.filter(teacher.getSchool(), entYear, classNum, isAttend);
		} else if (entYear != 0) {
			// 入学年度のみ指定
			students = sDAO.filter(teacher.getSchool(), entYear, isAttend);
		} else if (entYear == 0 && classNum == null) {
			// 全学生取得
			students = sDAO.filter(teacher.getSchool(), isAttend);
		} else {
			errors.put("f1", "クラスを指定する場合は入学年度も指定してください");
			req.setAttribute("errors", errors);
			    students = sDAO.filter(teacher.getSchool(), isAttend);
		}


		// レスポンス値をセット 6
		// リクエストに入学年度をセット
		req.setAttribute("f1", entYear);
		// リクエストにクラス番号をセット
		req.setAttribute("f2", classNum);

		// 在学フラグが送信されていた場合
		if (isAttendStr != null) {
			// 在学フラグを立てる
			isAttend = true;
			// リクエストに在学フラグをセット
			req.setAttribute("f3", isAttendStr);
		}

		// リクエストに学生リストをセット
		req.setAttribute("students", students);
		// リクエストにデータをセット
		req.setAttribute("class_num_set", list);
		req.setAttribute("ent_year_set", entYearSet);


		return "/scoremanager/main/student_list.jsp";
	}
}