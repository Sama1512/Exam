package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.ClassNumDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import tool.Action;

public class TestListAction extends Action {
    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");
        School school = teacher.getSchool();

        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String testNumStr = req.getParameter("f4");

        int entYear = 0;
        int testNum = 0;
        Map<String, String> errors = new HashMap<>();
        List<Test> testList = new ArrayList<>();

        if (entYearStr != null && !entYearStr.equals("0")) {
            try {
                entYear = Integer.parseInt(entYearStr.trim());
            } catch (NumberFormatException e) {
                errors.put("f1", "入学年度が不正です。");
            }
        }

        if (classNum != null && classNum.equals("0")) {
            classNum = null;
        }

        SubjectDAO subjectDAO = new SubjectDAO();
        Subject subject = null;
        if (subjectCd != null && !subjectCd.equals("0")) {
            subject = subjectDAO.get(subjectCd, school);
        }

        if (testNumStr != null && !testNumStr.equals("0")) {
            try {
                testNum = Integer.parseInt(testNumStr.trim());
            } catch (NumberFormatException e) {
                errors.put("f4", "テスト番号が不正です。");
            }
        }

        if (entYear != 0 && classNum != null && subject != null && testNum != 0) {
            testList = new TestDAO().filter(entYear, classNum, subject, testNum, school);
        }

        //直近10年の年度リスト
        List<Integer> entYearSet = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++){
        	entYearSet.add(i);
        }

        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", testNum);
        req.setAttribute("test_list", testList);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", new ClassNumDAO().filter(school));
        req.setAttribute("subject_list", subjectDAO.filter(school));
        req.setAttribute("no_list", Arrays.asList(1, 2));
        req.setAttribute("errors", errors);

        //初めてページに飛んだ時に別のメッセージを出力するための条件分岐
        if (entYearStr != null && classNum != null && subjectCd != null && testNumStr != null) {
            req.setAttribute("searched", true);
        }

        return "/scoremanager/main/test_list.jsp";
    }
}