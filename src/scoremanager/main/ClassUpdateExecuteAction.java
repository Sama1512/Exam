package scoremanager.main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.Teacher;
import dao.ClassNumDAO;
import dao.DAO;
import dao.StudentDAO;
import dao.TestDAO;
import tool.Action;

public class ClassUpdateExecuteAction extends Action {

	@Override
	public String execute(HttpServletRequest req, HttpServletResponse res) throws Exception {
		try {
			HttpSession session = req.getSession();
			Teacher teacher = (Teacher) session.getAttribute("user");

			String oldClassNum = req.getParameter("old_class_num");
			String newClassNum = req.getParameter("class_num");

			Map<String, String> errors = new HashMap<>();

			ClassNumDAO classNumDAO = new ClassNumDAO();

			//入力チェック
			if (newClassNum == null || newClassNum.length() > 5) {
			    errors.put("class_num", "クラス番号は5文字以内で入力してください");
			}

			//元のクラス情報取得
			ClassNum existingClass = classNumDAO.get(oldClassNum, teacher.getSchool());
			if (existingClass == null) {
				errors.put("class_num", "元のクラス番号が存在しません");
			}

			//クラス番号が変更されている場合のみ重複チェック
			if (!oldClassNum.equals(newClassNum) &&
				classNumDAO.get(newClassNum, teacher.getSchool()) != null) {
				errors.put("class_num", "変更後のクラス番号が既に使用されています");
			}

			//エラーがある場合、入力画面に戻す
			if (!errors.isEmpty()) {
				req.setAttribute("errors", errors);
				req.setAttribute("school_cd", teacher.getSchool().getCd());
				req.setAttribute("classNum", existingClass);
				return "/scoremanager/main/class_update.jsp";
			}

			//クラス番号が変更されていない場合は処理せず終了
			if (oldClassNum.equals(newClassNum)) {
				return "/scoremanager/main/class_update_done.jsp";
			}

			Connection connection = null;
			try {
				connection = new DAO().getConnection();
				connection.setAutoCommit(false);

				TestDAO testDAO = new TestDAO();
				StudentDAO studentDAO = new StudentDAO();

				//変更後のクラスが既に存在している場合(トランザクション開始後の重複チェック)
				if (classNumDAO.get(newClassNum, teacher.getSchool()) != null) {
					connection.rollback();
					errors.put("class_num", "変更後のクラス番号が既に使用されています");
					req.setAttribute("errors", errors);
					req.setAttribute("school_cd", teacher.getSchool().getCd());
					req.setAttribute("classNum", existingClass);
					return "/scoremanager/main/class_update.jsp";
				} else {
					//新しいクラスが存在していない場合は参照性制約があるので、まず新クラス番号を仮登録
					ClassNum newClass = new ClassNum();
					newClass.setClassNum(newClassNum);
					newClass.setSchool(teacher.getSchool());

					PreparedStatement insertSt = connection.prepareStatement(
						"insert into class_num (school_cd, class_num) values (?, ?)"
					);
					insertSt.setString(1, teacher.getSchool().getCd());
					insertSt.setString(2, newClassNum);
					insertSt.executeUpdate();
					insertSt.close();

					//Testテーブル・Studentテーブルを更新
					testDAO.updateClassNum(oldClassNum, newClassNum, teacher.getSchool().getCd(), connection);
					studentDAO.updateClassNum(oldClassNum, newClassNum, teacher.getSchool().getCd(), connection);

					//最後に古いクラス番号を削除
					PreparedStatement delSt = connection.prepareStatement(
						"delete from class_num where class_num = ? and school_cd = ?"
					);
					delSt.setString(1, oldClassNum);
					delSt.setString(2, teacher.getSchool().getCd());
					delSt.executeUpdate();
					delSt.close();
				}

				connection.commit();
			} catch (Exception e) {
				if (connection != null) {
					connection.rollback();
				}
				e.printStackTrace();
				return "/error.jsp";
			} finally {
				if (connection != null) {
					connection.close();
				}
			}

			return "/scoremanager/main/class_update_done.jsp";
		} catch (Exception e) {
			e.printStackTrace();
			return "/error.jsp";
		}
	}
}