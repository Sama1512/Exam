package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import bean.TestListStudent;
import dao.ClassNumDAO;
import dao.StudentDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import tool.Action;

public class TestListStudentExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String studentNo = req.getParameter("student_no");

        Teacher teacher = (Teacher) req.getSession().getAttribute("user");
        School school = teacher.getSchool();

        // 年度、クラス、科目リストをいつでも検索できるように入れる
        List<Integer> entYearSet = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) entYearSet.add(i);

        SubjectDAO subjectDAO = new SubjectDAO();
        List<Subject> subjectList = subjectDAO.filter(school);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", new ClassNumDAO().filter(school));
        req.setAttribute("subject_list", subjectList);

        req.setAttribute("f1", null);
        req.setAttribute("f2", null);
        req.setAttribute("f3", null);

        studentNo = studentNo.trim();
        req.setAttribute("student_no", studentNo);

        StudentDAO studentDAO = new StudentDAO();
        Student student = studentDAO.get(studentNo);

        if (student == null) {
            req.setAttribute("error", "学生が見つかりません。");
            return "/scoremanager/main/test_list_student.jsp";
        }

        TestDAO testDAO = new TestDAO();
        List<TestListStudent> list = new ArrayList<>();

        for (Subject subject : subjectList) {
            for (int i = 1; i <= 2; i++) {
                Test test = testDAO.get(student, subject, school, i);
                if (test != null) {
                    TestListStudent row = new TestListStudent();
                    row.setSubjectCd(subject.getCd());
                    row.setSubjectName(subject.getName());
                    row.setNum(i);
                    row.setPoint(test.getPoint());
                    list.add(row);
                }
            }
        }

        req.setAttribute("subjectMap", list);
        req.setAttribute("studentName", student.getName());
        req.setAttribute("studentNo", student.getNo());
        req.setAttribute("searched", true);

        return "/scoremanager/main/test_list_student.jsp";
    }
}