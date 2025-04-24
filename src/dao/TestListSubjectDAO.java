package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDAO extends DAO {

    // 学生ごとの指定科目のテスト情報を取得するSQL
    private String baseSql =
        "select stu.no as student_no, stu.name as student_name, stu.class_num, stu.ent_year, " +
        "t.num as test_num, t.point " +
        "from student stu " +
        "join test t on stu.no = t.student_no " +
        "where stu.ent_year = ? and stu.class_num = ? and stu.school_cd = ? and t.subject_cd = ? " +
        "order by stu.no asc, t.num asc";

    // ResultSet を List<TestListSubject> に変換
    private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
        List<TestListSubject> list = new ArrayList<>();

        try {
            String currentStudentNo = null;
            TestListSubject current = null;

            while (rSet.next()) {
                String studentNo = rSet.getString("student_no");

                if (!studentNo.equals(currentStudentNo)) {
                    // 新しい学生データを作成
                    current = new TestListSubject();
                    current.setStudentNo(studentNo);
                    current.setStudentName(rSet.getString("student_name"));
                    current.setClassNum(rSet.getString("class_num"));
                    current.setEntYear(rSet.getInt("ent_year"));
                    current.setPoints(new HashMap<>());

                    list.add(current);
                    currentStudentNo = studentNo;
                }

                // テスト番号と点数を追加
                int testNum = rSet.getInt("test_num");
                int point = rSet.getInt("point");

                current.getPoints().put(String.valueOf(testNum), point);
            }
        } catch (SQLException | NullPointerException e) {
            e.printStackTrace();
        }

        return list;
    }

    // クラスごとの学生に対する、1科目のテスト一覧を取得
    public List<TestListSubject> filter(int entYear, String classNum, Subject subject, School school) throws Exception {
        List<TestListSubject> list = new ArrayList<>();

        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        try {
            statement = connection.prepareStatement(baseSql);
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, school.getCd());
            statement.setString(4, subject.getCd());

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