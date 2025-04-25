package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDAO extends DAO {

	// 学校コードを条件に学生情報を取得するSQL
	private String baseSql = "select * from student where school_cd = ?";

	//受け取った学生番号(no)を用いて学生情報を表示する
	public Student get(String no) throws Exception {
		// 学生インスタンスを初期化
		Student student = new Student();

		// データベースのコネクションを確立
		Connection connection = getConnection();
		// プリペアドステートメント
		PreparedStatement statement = null;

		try {
			// プリペアドステートメントにSQL文をセット
			statement = connection.prepareStatement("select * from student where no=?");

			// 学生番号をバインド
			statement.setString(1, no);

			// プリペアドステートメントを実行
			ResultSet rSet = statement.executeQuery();

			// 学校DAOを初期化
			SchoolDAO schoolDAO = new SchoolDAO();

			if (rSet.next()) {
				// リザルトセットが存在する場合 → 学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				student.setSchool(schoolDAO.get(rSet.getString("school_cd")));
			} else {
				// リザルトセットが存在しない場合
				student = null;
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

		return student;
	}

	// ResultSet を List<Student> に変換する
	private List<Student> postFilter(ResultSet rSet, School school) throws Exception {
		// リストを初期化
		List<Student> list = new ArrayList<>();

		try {
			// リザルトセットを全権走査
			while (rSet.next()) {
				// 学生インスタンスを初期化
				Student student = new Student();

				// 学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				student.setSchool(school);

				// リストに追加
				list.add(student);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	//全ての情報が指定された時の絞り込み
	public List<Student> filter(School school, int entYear, String classNum, boolean isAttend) throws Exception {
		// リストを初期化
		List<Student> list = new ArrayList<>();

		// コネクションを確立
		Connection connection = getConnection();

		// プリペアドステートメント
		PreparedStatement statement = null;
		ResultSet rSet = null;

		// SQL文の条件
		String condition = " and ent_year=? and class_num=?";
		String order = " order by no asc";

		// SQL文の在学フラグ条件
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = " and is_attend=true";
		}

		try {
			// プリペアドステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);

			// 学校コードをバインド
			statement.setString(1, school.getCd());

			// 入学年度をバインド
			statement.setInt(2, entYear);

			// クラス番号をバインド
			statement.setString(3, classNum);

			// SQLを実行
			rSet = statement.executeQuery();

			// リストへの格納処理を実行
			list = postFilter(rSet, school);

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

	//クラス以外が指定された時の絞り込み
	public List<Student> filter(School school, int entYear, boolean isAttend) throws Exception {
		// リストを初期化
		List<Student> list = new ArrayList<>();

		// コネクションを確立
		Connection connection = getConnection();

		// プリペアドステートメント
		PreparedStatement statement = null;
		ResultSet rSet = null;

		// SQL文の条件
		String condition = " and ent_year=?";
		String order = " order by no asc";

		// SQL文の在学フラグ
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = " and is_attend=true";
		}

		try {
			// プリペアドステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);

			// プリペアドステートメントに学校コードをバインド
			statement.setString(1, school.getCd());

			// プリペアドステートメントに入学年度をバインド
			statement.setInt(2, entYear);

			// プリペアドステートメントを実行
			rSet = statement.executeQuery();

			// リストへの格納処理を実行
			list = postFilter(rSet, school);
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

	//指定がなかった時の絞り込み
	public List<Student> filter(School school, boolean isAttend) throws Exception {
		// リストを初期化
		List<Student> list = new ArrayList<>();

		// コネクションを確立
		Connection connection = getConnection();

		// プリペアドステートメント
		PreparedStatement statement = null;
		ResultSet rSet = null;

		// SQL文の条件
		String order = " order by no asc";

		// SQL文の在学フラグ
		String conditionIsAttend = "";
		if (isAttend) {
			conditionIsAttend = " and is_attend=true";
		}

		try {
			// プリペアドステートメントにSQL文をセット
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);

			// 学校コードをバインド
			statement.setString(1, school.getCd());

			// SQLを実行
			rSet = statement.executeQuery();

			// リストへの格納処理を実行
			list = postFilter(rSet, school);

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

	//学生の追加・変更を行う
	public boolean save(Student student) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
			// データベースから学生を取得
			Student old = get(student.getNo());

			if (old == null) {
				// 学生が存在しなかった場合 → INSERT文をセット
				statement = connection.prepareStatement(
					"insert into student (no, name, ent_year, class_num, is_attend, school_cd) values (?, ?, ?, ?, ?, ?)"
				);
				statement.setString(1, student.getNo());
				statement.setString(2, student.getName());
				statement.setInt(3, student.getEntYear());
				statement.setString(4, student.getClassNum());
				statement.setBoolean(5, student.isAttend());
				statement.setString(6, student.getSchool().getCd());
			} else {
				// 学生が存在した場合 → UPDATE文をセット
				statement = connection.prepareStatement(
					"update student set ent_year=?, name=?, class_num=?, is_attend=? where no=?"
				);
				statement.setInt(1, student.getEntYear());
				statement.setString(2, student.getName());
				statement.setString(3, student.getClassNum());
				statement.setBoolean(4, student.isAttend());
				statement.setString(5, student.getNo());
			}

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

	public boolean delete(Student student) throws Exception {
		// コネクションを確立
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
			statement = connection.prepareStatement(
				"delete from student where no = ?"
			);
			statement.setString(1, student.getNo());

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
}