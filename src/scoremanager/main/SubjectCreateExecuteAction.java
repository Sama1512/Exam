package scoremanager.main;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDAO;
import tool.Action;

public class SubjectCreateExecuteAction extends Action {

    @Override
    public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("user");

        // 入力値取得
        String cd = req.getParameter("cd");
        String name = req.getParameter("name");

        Map<String, String> errors = new HashMap<>();

        //入力された科目コードが既に使われているときのエラーを入れる処理
        if (cd.length() != 3) {
            errors.put("cd", "科目コードは3文字で入力してください");
        }

        //入力された科目コードが既に使われているときのエラーを入れる処理
        SubjectDAO dao = new SubjectDAO();
        if (dao.get(cd,teacher.getSchool()) != null) {
            errors.put("cd", "科目コードが重複しています");
        }

        // エラーがある場合は再表示
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);

            return "/scoremanager/main/subject_create.jsp";
        }

        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(teacher.getSchool());
        dao.save(subject);

        return "/scoremanager/main/subject_create_done.jsp";
    }
}