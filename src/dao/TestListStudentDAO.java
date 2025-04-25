package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDAO extends DAO {

	// 学生のテスト一覧を取得するSQL(TestListStudentというデータベースは存在しないので、Test・Subjectのデータベースから仮想で作る)
	private String baseSql =
		"select t.no, t.point, s.cd as subject_cd, s.name as subject_name " +
		"from test t " +
		"join subject s on t.subject_cd = s.cd " +
		"where t.student_no = ? " +
		"order by s.cd asc, t.no asc";

	// ResultSet を List<TestListStudent> に変換
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		List<TestListStudent> list = new ArrayList<>();

		try {
			while (rSet.next()) {
				TestListStudent testListStudent = new TestListStudent();

				testListStudent.setNum(rSet.getInt("no"));
				testListStudent.setPoint(rSet.getInt("point"));
				testListStudent.setSubjectCd(rSet.getString("subject_cd"));
				testListStudent.setSubjectName(rSet.getString("subject_name"));

				list.add(testListStudent);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	// 学生ごとのテスト情報を取得
	public List<TestListStudent> filter(Student student) throws Exception {
		List<TestListStudent> list = new ArrayList<>();

		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		try {
			statement = connection.prepareStatement(baseSql);
			statement.setString(1, student.getNo());

			rSet = statement.executeQuery();
			list = postFilter(rSet);
		} catch (Exception e) {
			throw e;
		} finally {
			if (rSet != null) try { rSet.close(); } catch (SQLException e) { throw e; }
			if (statement != null) try { statement.close(); } catch (SQLException e) { throw e; }
			if (connection != null) try { connection.close(); } catch (SQLException e) { throw e; }
		}

		return list;
	}
}