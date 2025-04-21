package scoremanager.main;

import java.time.LocalDate;
import java.util.ArrayList;
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

        // DAO
        ClassNumDAO classNumDAO = new ClassNumDAO();
        SubjectDAO subjectDAO = new SubjectDAO();
        TestDAO testDAO = new TestDAO();

        // パラメータ取得
        String entYearStr = req.getParameter("f1");
        String classNum = req.getParameter("f2");
        String subjectCd = req.getParameter("f3");
        String testNumStr = req.getParameter("f4");

        int entYear = 0;
        int testNum = 0;
        Map<String, String> errors = new HashMap<>();
        List<Test> testList = new ArrayList<>();

        // 入学年度
        if (entYearStr != null && !entYearStr.equals("0")) {
            try {
                entYear = Integer.parseInt(entYearStr.trim());
            } catch (NumberFormatException e) {
                errors.put("f1", "入学年度が不正です。");
            }
        }

        // クラス
        if (classNum != null && classNum.equals("0")) {
            classNum = null;
        }

        // 科目
        Subject subject = null;
        if (subjectCd != null && !subjectCd.equals("0")) {
            subject = subjectDAO.get(subjectCd, school);
        }

        // テスト番号
        if (testNumStr != null && !testNumStr.equals("0")) {
            try {
                testNum = Integer.parseInt(testNumStr.trim());
            } catch (NumberFormatException e) {
                errors.put("f4", "テスト番号が不正です。");
            }
        }

        // 条件がすべて揃っているときに検索実行
        if (entYear != 0 && classNum != null && subject != null && testNum != 0) {
            testList = testDAO.filter(entYear, classNum, subject, testNum, school);
        }

        // 年度リスト(10年前～現在)
        List<Integer> entYearSet = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear - 10; i <= currentYear; i++) {
            entYearSet.add(i);
        }

        // テスト番号リスト(1,2)
        List<Integer> noList = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            noList.add(i);
        }

        // リクエストにセット
        req.setAttribute("f1", entYear);
        req.setAttribute("f2", classNum);
        req.setAttribute("f3", subjectCd);
        req.setAttribute("f4", testNum);
        req.setAttribute("test_list", testList);
        req.setAttribute("ent_year_set", entYearSet);
        req.setAttribute("class_num_set", classNumDAO.filter(school));
        req.setAttribute("subject_list", subjectDAO.filter(school));
        req.setAttribute("no_list", noList);
        req.setAttribute("errors", errors);

        return "/scoremanager/main/test_list.jsp";
    }
}