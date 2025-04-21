package scoremanager.main;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDAO;
import tool.Action;

public class StudentUpdateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        @SuppressWarnings("unused")
		Teacher teacher = (Teacher) session.getAttribute("user");

        // 入力値取得
        String studentNo = req.getParameter("student_no");
        String entYearStr = req.getParameter("ent_year");
        String studentName = req.getParameter("student_name");
        String classNum = req.getParameter("class_num");
        String isAttendStr = req.getParameter("is_attend");

        StudentDAO dao = new StudentDAO();

        // 変更処理
        int entYear = Integer.parseInt(entYearStr);
        boolean isAttend = isAttendStr != null;

        Student student = new Student();
        student.setNo(studentNo);
        student.setEntYear(entYear);
        student.setName(studentName);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        dao.save(student);

        return "/scoremanager/main/student_update_done.jsp";
    }
}