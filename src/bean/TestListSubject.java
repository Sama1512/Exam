package bean;

import java.util.Map;

public class TestListSubject implements java.io.Serializable {
	private int entYear;
	private String studentNo,studentName,classNum;
	private Map<String,Integer> points; //クラス図ではMap<Integer,Integer>となっているが、row.points.get(1)などが正常に作動しないため片方をString型にしている

	public int getEntYear() {
		return entYear;
	}

	public String getStudentNo() {
		return studentNo;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getClassNum() {
		return classNum;
	}

	public Map<String,Integer> getPoints() {
		return points;
	}

	public void setEntYear(int entYear) {
		this.entYear=entYear;
	}

	public void setStudentNo(String studentNo) {
		this.studentNo=studentNo;
	}

	public void setStudentName(String studentName) {
		this.studentName=studentName;
	}

	public void setClassNum(String classNum) {
		this.classNum=classNum;
	}

	public void setPoints(Map<String,Integer> points) {
		this.points=points;
	}
}