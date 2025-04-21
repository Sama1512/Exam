package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDAO extends DAO {

	//受け取った科目コード(cd)を用いて科目情報を表示する
	public Subject get(String cd,School school) throws Exception {
	    // 科目インスタンスを初期化
	    Subject subject = new Subject();

	    // データベースのコネクションを確立
	    Connection connection = getConnection();
	    // プリペアドステートメント
	    PreparedStatement statement = null;

	    try {
	    	statement = connection.prepareStatement("select * from subject where school_cd = ? and cd = ?");
	    	statement.setString(1, school.getCd());
	    	statement.setString(2, cd);

	        // プリペアドステートメントを実行
	        ResultSet rSet = statement.executeQuery();

	        // 学校DAOを初期化
	        SchoolDAO schoolDAO = new SchoolDAO();

	        if (rSet.next()) {
	            // リザルトセットが存在する場合 → 科目インスタンスに検索結果をセット
	        	subject.setSchool(schoolDAO.get(rSet.getString("school_cd")));
	            subject.setCd(rSet.getString("cd"));
	            subject.setName(rSet.getString("name"));
	        } else {
	            // リザルトセットが存在しない場合
	            subject = null;
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

	    return subject;
	}

	//科目情報を学校コードで絞り込んでリストとして受け取る
	public List<Subject> filter(School school) throws Exception {
	    List<Subject> list = new ArrayList<>();
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    ResultSet rs = null;

	    try {
	        String sql = "select * from subject where school_cd = ?";
	        statement = connection.prepareStatement(sql);
	        statement.setString(1, school.getCd());

	        rs = statement.executeQuery();

	        while (rs.next()) {
	            Subject subject = new Subject();
	            subject.setCd(rs.getString("cd"));
	            subject.setName(rs.getString("name"));
	            list.add(subject);
	        }

	    } finally {
	        if (rs != null) rs.close();
	        if (statement != null) statement.close();
	        if (connection != null) connection.close();
	    }

	    return list;
	}


	//科目の追加・変更を行う
	public boolean save(Subject subject) throws Exception {
	    // コネクションを確立
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int count = 0;

	    try {
	        // データベースから科目を取得
	        Subject old = get(subject.getCd(),subject.getSchool());

	        if (old == null) {
	            // 科目が存在しなかった場合 → INSERT文をセット
	            statement = connection.prepareStatement(
	                "insert into subject values (?, ?, ?)"
	            );
	            statement.setString(1, subject.getSchool().getCd());
	            statement.setString(2, subject.getCd());
	            statement.setString(3, subject.getName());
	        } else {
	            // 科目が存在した場合 → UPDATE文をセット
	            statement = connection.prepareStatement(
	                "update subject set name=? where cd=?"
	            );
	            statement.setString(1, subject.getName());
	            statement.setString(2, subject.getCd());
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

	//科目情報を削除する
    public boolean delete(Subject subject) throws Exception {
    	// コネクションを確立
	    Connection connection = getConnection();
	    PreparedStatement statement = null;
	    int count = 0;

	    try {
	            statement = connection.prepareStatement(
	                "delete from subject where cd = ?"
	            );
	            statement.setString(1, subject.getCd());

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