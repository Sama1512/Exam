package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;

public class TestDAO extends DAO {

    //基本条件
    private String baseSql = "select * from test where school_cd = ?";

    //テストデータを取得
    public Test get(Student student, Subject subject, School school, int no) throws Exception {
        Test test = null;
        Connection connection = getConnection();
        PreparedStatement statement = null;

        try {
            statement = connection.prepareStatement(
            	baseSql + "and student_no = ? and subject_cd = ? and no = ?"
            );
            statement.setString(1, school.getCd());
            statement.setString(2, student.getNo());
            statement.setString(3, subject.getCd());
            statement.setInt(4, no);

            ResultSet rSet = statement.executeQuery();

            if (rSet.next()) {
                test = new Test();
                test.setNo(rSet.getInt("no"));
                test.setPoint(rSet.getInt("point"));
                test.setClassNum(rSet.getString("class_num"));
                test.setStudent(student);
                test.setSubject(subject);
                test.setSchool(school);
            }
        } finally {
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return test;
    }

    private List<Test> postFilter(ResultSet rSet, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        StudentDAO studentDAO = new StudentDAO();
        SubjectDAO subjectDAO = new SubjectDAO();

        while (rSet.next()) {
            Test test = new Test();
            Student student = studentDAO.get(rSet.getString("student_no"));
            Subject subject = subjectDAO.get(rSet.getString("subject_cd"), school);

            test.setStudent(student);
            test.setSubject(subject);
            test.setSchool(school);
            test.setNo(rSet.getInt("no"));
            test.setPoint(rSet.getInt("point"));
            test.setClassNum(rSet.getString("class_num"));

            list.add(test);
        }

        return list;
    }

    public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
        List<Test> list = new ArrayList<>();
        Connection connection = getConnection();
        PreparedStatement statement = null;
        ResultSet rSet = null;

        String sql =
            "select t.* from test t " +
            "join student s on t.student_no = s.no " +
            "where s.ent_year = ? and s.class_num = ? and s.school_cd = ? " +
            "and t.subject_cd = ? and t.no = ? " +
            "order by t.student_no asc";

        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, entYear);
            statement.setString(2, classNum);
            statement.setString(3, school.getCd());
            statement.setString(4, subject.getCd());
            statement.setInt(5, num);

            rSet = statement.executeQuery();
            list = postFilter(rSet, school);
        } finally {
            if (rSet != null) rSet.close();
            if (statement != null) statement.close();
            if (connection != null) connection.close();
        }

        return list;
    }

    public boolean save(List<Test> list) throws Exception {
        Connection connection = null;
        boolean result = true;

        try {
            connection = getConnection();
            connection.setAutoCommit(false);

            for (Test test : list) {
                boolean r = save(test, connection);
                if (!r) result = false;
            }

            connection.commit();
        } catch (Exception e) {
            if (connection != null) connection.rollback();
            throw e;
        } finally {
            if (connection != null) connection.close();
        }

        return result;
    }

    public boolean save(Test test, Connection connection) throws Exception {
        PreparedStatement statement = null;
        int count = 0;

        try {
            // 既存チェック
            Test old = get(test.getStudent(), test.getSubject(), test.getSchool(), test.getNo());

            if (old == null) {
                statement = connection.prepareStatement(
                    "insert into test values (?, ?, ?, ?, ?, ?)"
                );
                statement.setString(1, test.getStudent().getNo());
                statement.setString(2, test.getSubject().getCd());
                statement.setString(3, test.getSchool().getCd());
                statement.setInt(4, test.getNo());
                statement.setInt(5, test.getPoint());
                statement.setString(6, test.getClassNum());
            } else {
                statement = connection.prepareStatement(
                    "update test set point = ?, class_num = ? where no = ? and student_no = ? and subject_cd = ? and school_cd = ?"
                );
                statement.setInt(1, test.getPoint());
                statement.setString(2, test.getClassNum());
                statement.setInt(3, test.getNo());
                statement.setString(4, test.getStudent().getNo());
                statement.setString(5, test.getSubject().getCd());
                statement.setString(6, test.getSchool().getCd());
            }

            count = statement.executeUpdate();
        } finally {
            if (statement != null) statement.close();
        }

        return count > 0;
    }
}
