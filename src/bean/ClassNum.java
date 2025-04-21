package bean;

public class ClassNum implements java.io.Serializable {
	private String classNum;
	private School school;

	public String getClassNum() {
		return classNum;
	}

	public School getSchool() {
		return school;
	}

	public void setClassNum(String classNum) {
		this.classNum=classNum;
	}

	public void setSchool(School school) {
		this.school=school;
	}
}