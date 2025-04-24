package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import bean.TestListSubject;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import tool.Action;

public class TestListSubjectExecuteAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");

        Teacher teacher = (Teacher) req.getSession().getAttribute("user");
        School school = teacher.getSchool();

        List<Integer> entYearSet = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) entYearSet.add(i);
        List<String> classNumSet = new ClassNumDAO().filter(school);
        List<Subject> subjectList = new SubjectDAO().filter(school);

        req.setAttribute("f1", entYearStr);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumSet);
        req.setAttribute("subject_list", subjectList);

        //未入力があればエラーを返す
        if (entYearStr == null || entYearStr.equals("0") ||
            classNum == null || classNum.equals("0") ||
            subjectCd == null || subjectCd.equals("0")) {

            req.setAttribute("subjectError", "すべての項目を選択してください。");
            return "/scoremanager/main/test_list.jsp";
        }

        //入力された値が全て正常な時の処理
        int entYear = Integer.parseInt(entYearStr);
        Subject subject = new SubjectDAO().get(subjectCd, school);
        TestDAO testDAO = new TestDAO();

        List<Test> allTests = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            List<Test> filtered = testDAO.filter(entYear, classNum, subject, i, school);
            allTests.addAll(filtered);
        }

        Map<String, TestListSubject> map = new LinkedHashMap<>();
        for (Test test : allTests) {
            String key = test.getStudent().getNo();
            TestListSubject row = map.getOrDefault(key, new TestListSubject());

            row.setEntYear(test.getStudent().getEntYear());
            row.setClassNum(test.getClassNum());
            row.setStudentNo(test.getStudent().getNo());
            row.setStudentName(test.getStudent().getName());

            if (row.getPoints() == null) {
                row.setPoints(new HashMap<>());
            }
            row.getPoints().put(String.valueOf(test.getNo()), test.getPoint());

            map.put(key, row);
        }

        req.setAttribute("subjectName", subject.getName()); //選択された科目名
        req.setAttribute("studentMap", new ArrayList<>(map.values())); //学生情報
        req.setAttribute("searched", true); //検索が実行されたのでtrueを返す

        return "/scoremanager/main/test_list_subject.jsp";
    }
}