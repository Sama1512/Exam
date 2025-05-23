package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDAO extends DAO {

	private String baseSql =
		"select stu.no as student_no, stu.name as student_name, stu.class_num, stu.ent_year, " +
		"t.no as test_no, t.point " +
		"from student stu " +
		"join test t on stu.no = t.student_no " +
		"join subject sub on t.subject_cd = sub.cd and t.school_cd = sub.school_cd " +
		"where stu.ent_year = ? and stu.class_num = ? and stu.school_cd = ? and t.subject_cd = ? " +
		"and stu.delete_flag = false and sub.delete_flag = false " +
		"order by stu.no asc, t.no asc";

	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		try {
			String currentStudentNo = null;
			TestListSubject current = null;

			while (rSet.next()) {
				String studentNo = rSet.getString("student_no");

				if (!studentNo.equals(currentStudentNo)) {
					current = new TestListSubject();
					current.setStudentNo(studentNo);
					current.setStudentName(rSet.getString("student_name"));
					current.setClassNum(rSet.getString("class_num"));
					current.setEntYear(rSet.getInt("ent_year"));
					current.setPoints(new HashMap<>());
					list.add(current);
					currentStudentNo = studentNo;
				}

				int testNo = rSet.getInt("test_no");
				int point = rSet.getInt("point");

				current.getPoints().put(String.valueOf(testNo), point);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}

		return list;
	}

	public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
		List<TestListSubject> list = new ArrayList<>();

		try (Connection connection = getConnection();
			PreparedStatement statement = connection.prepareStatement(baseSql)) {

			statement.setInt(1, entYear);
			statement.setString(2, classNum);
			statement.setString(3, school.getCd());
			statement.setString(4, subject.getCd());

			try (ResultSet rSet = statement.executeQuery()) {
				list = postFilter(rSet);
			}
		}

		return list;
	}

	public Map<String, Integer> categorizePoints(List<TestListSubject> list) {
		Map<String, Integer> categories = new LinkedHashMap<>();
		categories.put("0-59", 0);
		categories.put("60-69", 0);
		categories.put("70-79", 0);
		categories.put("80-89", 0);
		categories.put("90-100", 0);

		for (TestListSubject t : list) {
			for (int point : t.getPoints().values()) {
				if (point < 60) categories.put("0-59", categories.get("0-59") + 1);
				else if (point < 70) categories.put("60-69", categories.get("60-69") + 1);
				else if (point < 80) categories.put("70-79", categories.get("70-79") + 1);
				else if (point < 90) categories.put("80-89", categories.get("80-89") + 1);
				else categories.put("90-100", categories.get("90-100") + 1);
			}
		}

	return categories;
	}
}