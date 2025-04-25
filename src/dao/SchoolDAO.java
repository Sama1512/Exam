package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.School;

public class SchoolDAO extends DAO {

	/**
	* 学校コードを指定して学校インスタンスを1件取得する
	*
	* @param cd 学校コード
	* @return 学校クラスのインスタンス（存在しない場合はnull）
	* @throws Exception
	*/

	//受け取った学校コード(cd)を用いて学校情報を表示する
	public School get(String cd) throws Exception {
		// 学校インスタンスを初期化
		School school = new School();

		// データベースのコネクションを確保
		Connection connection = getConnection();

		// プリペアドステートメント
		PreparedStatement statement = null;

		try {
			// プリペアドステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from school where cd = ?");

			// 学校コードをバインド
			statement.setString(1, cd);

			// SQL実行
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
				// レコードが存在する場合はインスタンスにセット
				school.setCd(rSet.getString("cd"));
				school.setName(rSet.getString("name"));
			} else {
				// 存在しない場合はnull
				school = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// ステートメントを閉じる
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

		return school;
	}
}