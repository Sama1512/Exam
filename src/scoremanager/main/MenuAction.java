package scoremanager.main;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.Action;

public class MenuAction extends Action {
	public String execute(
		HttpServletRequest request, HttpServletResponse response) throws Exception {
		try {
			return "/scoremanager/main/menu.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error.jsp";
		}
	}
}