package bean;

public class Test implements java.io.Serializable {
	private int no,point;
	private String classNum;
	private Student student;
	private Subject subject;
	private School school;

	public Student getStudent() {
		return student;
	}

	public String getClassNum() {
		return classNum;
	}

	public Subject getSubject() {
		return subject;
	}

	public School getSchool() {
		return school;
	}

	public int getNo() {
		return no;
	}

	public int getPoint() {
		return point;
	}

	public void setStudent(Student student) {
		this.student=student;
	}

	public void setClassNum(String classNum) {
		this.classNum=classNum;
	}

	public void setSubject(Subject subject) {
		this.subject=subject;
	}

	public void setSchool(School school) {
		this.school=school;
	}

	public void setNo(int no) {
		this.no=no;
	}

	public void setPoint(int point) {
		this.point=point;
	}
}