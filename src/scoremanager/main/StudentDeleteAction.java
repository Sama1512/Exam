package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDAO;
import tool.Action;

public class StudentDeleteAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        @SuppressWarnings("unused")
		Teacher teacher = (Teacher) session.getAttribute("user");

        // 削除対象の学生番号を取得
        String no = req.getParameter("no");

        // 学生情報を取得
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.get(no);

        // 取得したデータをセット
        req.setAttribute("student", student);

        return "main/student_delete.jsp";
    }
}
