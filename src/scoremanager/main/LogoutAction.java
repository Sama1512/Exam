package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {
	public String execute(
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			HttpSession session=request.getSession();

			session.removeAttribute("user");
			return "/scoremanager/main/logout.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error.jsp";
		}
	}
}