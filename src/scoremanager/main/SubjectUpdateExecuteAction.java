package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectUpdateExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("user");

		// 入力値取得
		String cd = req.getParameter("cd");
		String name = req.getParameter("name");

		Map<String, String> errors = new HashMap<>();

		SubjectDAO dao = new SubjectDAO();

		//対象の科目が存在するかどうかチェックする(別画面から削除されてしまった場合)
		Subject isExist = dao.get(cd, teacher.getSchool());
		if (isExist== null) {
			errors.put("cd", "科目が存在していません");
			return "/scoremanager/main/subject_update.jsp";
		}

		Subject subject = new Subject();
		subject.setCd(cd);
		subject.setName(name);
		subject.setSchool(teacher.getSchool());
		dao.save(subject);

		return "/scoremanager/main/subject_update_done.jsp";
	}
}