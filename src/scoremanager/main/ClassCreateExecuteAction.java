package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDAO;
import tool.Action;

public class ClassCreateExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		String classNumInput = req.getParameter("class_num");

		Map<String, String> errors = new HashMap<>();

		//入力されたクラス番号が5文字より長かった場合
		if (classNumInput == null || classNumInput.length() > 5) {
			errors.put("class_num", "クラス番号は5文字以内で入力してください");
		}

		//入力されたクラス番号が重複していた場合
		ClassNumDAO dao = new ClassNumDAO();
		if (dao.get(classNumInput, teacher.getSchool()) != null) {
			errors.put("class_num", "クラス番号が重複しています");
		}

		// エラーがある場合は再表示
		if (!errors.isEmpty()) {
			req.setAttribute("errors", errors);
			req.setAttribute("class_num", classNumInput);
			req.setAttribute("school_cd", teacher.getSchool().getCd());
			return "/scoremanager/main/class_create.jsp";
		}

		ClassNum newClassNum = new ClassNum();
		newClassNum.setClassNum(classNumInput);
		newClassNum.setSchool(teacher.getSchool());
		dao.save(newClassNum);

		return "/scoremanager/main/class_create_done.jsp";
	}
}
