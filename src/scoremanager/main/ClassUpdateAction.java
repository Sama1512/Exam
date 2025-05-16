package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDAO;
import tool.Action;

public class ClassUpdateAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher) session.getAttribute("user");

			//変更対象のクラス番号を取得
			String class_num = req.getParameter("class_num");

			//クラス情報を取得
			ClassNumDAO classNumDAO = new ClassNumDAO();
			ClassNum classNum = classNumDAO.get(class_num, teacher.getSchool());

			//取得したデータをセット
			req.setAttribute("classNum", classNum);

			return "main/class_update.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error.jsp";
		}
	}
}