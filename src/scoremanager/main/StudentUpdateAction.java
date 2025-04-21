package scoremanager.main;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.StudentDAO;
import tool.Action;

public class StudentUpdateAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 変更対象の学生番号を取得
        String studentNo = req.getParameter("no");

        // 学生情報を取得
        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.get(studentNo);

        // クラスリストを取得
        ClassNumDAO cNumDAO = new ClassNumDAO();
        List<String> classNumSet = cNumDAO.filter(teacher.getSchool());

        // 取得したデータをセット
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("student", student);

        return "main/student_update.jsp";
    }
}
