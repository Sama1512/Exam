package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import bean.School;
import bean.Teacher;

public class TeacherDAO extends DAO {

	//受け取った教員ID(id)を用いて教員情報を表示するメソッド
	public Teacher get(String id) throws Exception {
		Connection con = getConnection();
		PreparedStatement st = con.prepareStatement("select * from teacher where id = ?");
		st.setString(1, id);
		ResultSet rs = st.executeQuery();

		Teacher tc = null;
		School sc = null;
		if (rs.next()) {
			tc = new Teacher();
			tc.setId(rs.getString("id"));
//			tc.setPassword(rs.getString("password")); パスワードを安易に表示するのはセキュリティの問題があると思う
			tc.setName(rs.getString("name"));

			sc = new School();
			sc.setCd(rs.getString("cd")); //データベースには学校コードとあるのでSchoolのcdを持ってくる
			tc.setSchool(sc);
		}

		st.close();
		con.close();
		return tc;
	}

	public Teacher login(String id, String password) throws Exception {
	    Connection con = getConnection();
	    PreparedStatement st = con.prepareStatement(
	        "select * from teacher where id = ? and password = ?"
	    );
	    st.setString(1, id);
	    st.setString(2, password);
	    ResultSet rs = st.executeQuery();

	    Teacher teacher = null;
	    if (rs.next()) {
	        teacher = new Teacher();
	        teacher.setId(rs.getString("id"));
	        teacher.setPassword(rs.getString("password"));
	        teacher.setName(rs.getString("name"));
	        School sc = new School();
			sc.setCd(rs.getString("school_cd"));
	        teacher.setSchool(sc);
	    }

	    rs.close();
	    st.close();
	    con.close();

	    return teacher;
	}
}