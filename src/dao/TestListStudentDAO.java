package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDAO extends DAO {

	// 学生のテスト一覧を取得するSQL(TestListStudentというデータベースは存在しないので、Test・Subjectのデータベースから仮想で作る)
	private String baseSql =
		"select t.no, t.point, su.cd as subject_cd, su.name as subject_name " +
		"from test t " +
		"join subject su on t.subject_cd = su.cd " +
		"join student st on t.student_no = st.no " +
		"where t.student_no = ? " +
		"and su.delete_flag = false and st.delete_flag = false " +
		"order by su.cd asc, t.no asc";

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

	public Map<String, Integer> categorizePoints(List<TestListStudent> testList) {
		Map<String, Integer> categories = new LinkedHashMap<>();
		categories.put("0-59", 0);
		categories.put("60-69", 0);
		categories.put("70-79", 0);
		categories.put("80-89", 0);
		categories.put("90-100", 0);

		for (TestListStudent t : testList) {
			int point = t.getPoint();
			if (point < 60) categories.put("0-59", categories.get("0-59") + 1);
			else if (point < 70) categories.put("60-69", categories.get("60-69") + 1);
			else if (point < 80) categories.put("70-79", categories.get("70-79") + 1);
			else if (point < 90) categories.put("80-89", categories.get("80-89") + 1);
			else categories.put("90-100", categories.get("90-100") + 1);
		}

	return categories;
	}
}