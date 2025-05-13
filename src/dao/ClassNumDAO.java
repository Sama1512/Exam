package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.ClassNum;
import bean.School;

public class ClassNumDAO extends DAO {

	//受け取ったクラス番号(class_num)を用いてクラス情報を表示する
	public ClassNum get(String class_num, School school) throws Exception {
		// クラス番号インスタンスを初期化
		ClassNum classNum = new ClassNum();

		// データベースのコネクションを確立
		Connection connection = getConnection();

		// プリペアドステートメント
		PreparedStatement statement = null;

		try {
			// プリペアドステートメントにSQL文をセット
			statement = connection.prepareStatement(
				"select * from class_num where class_num = ? and school_cd = ?"
			);

			// プリペアドステートメントに値をバインド
			statement.setString(1, class_num);
			statement.setString(2, school.getCd());

			// プリペアドステートメントを実行
			ResultSet rSet = statement.executeQuery();


			// 学校DAOを初期化
			SchoolDAO sDAO = new SchoolDAO();

			if (rSet.next()) {
				// リザルトセットが存在する場合 → 結果をセット
				classNum.setClassNum(rSet.getString("class_num"));
				classNum.setSchool(sDAO.get(rSet.getString("school_cd")));
			} else {
				// リザルトセットが存在しない場合
				classNum = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアドステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return classNum;
	}

	//受け取った学校コード(cd)を用いて指定された学校のクラス番号をリストとして受け取る
	public List<String> filter(School school) throws Exception {
		// リストを初期化
		List<String> list = new ArrayList<>();

		// データベースのコネクションを確立
		Connection connection = getConnection();

		// プリペアドステートメント
		PreparedStatement statement = null;

		try {
			// プリペアドステートメントにSQL文をセット
			statement = connection.prepareStatement(
				"select class_num from class_num where school_cd=? order by class_num"
			);

			// プリペアドステートメントに学校コードをバインド
			statement.setString(1, school.getCd());

			// プリペアドステートメントを実行
			ResultSet rSet = statement.executeQuery();

			// リザルトセットを全件走査
			while (rSet.next()) {
				// リストにクラス番号を追加
				list.add(rSet.getString("class_num"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアドステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		return list;
	}

	//クラス情報を追加する
	public boolean save(ClassNum classNum) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
			statement = connection.prepareStatement(
				"insert into class_num values(?, ?)"
			);
			statement.setString(1, classNum.getSchool().getCd());
			statement.setString(2, classNum.getClassNum());

			// プリペアドステートメントを実行
			count = statement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアドステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}

		// 実行件数が1件以上ある場合 → true
		if (count > 0) {
			return true;
		} else {
			// 実行件数0件 → false
			return false;
		}
	}

	//クラス情報を変更する
	public void updateClassNum(ClassNum classNum, String newClassNum, Connection con) throws Exception {
		PreparedStatement st = con.prepareStatement(
			"update class_num set class_num = ? where class_num = ? and school_cd = ?"
		);
		st.setString(1, newClassNum);
		st.setString(2, classNum.getClassNum());
		st.setString(3, classNum.getSchool().getCd());
		st.executeUpdate();
		st.close();
	}
}