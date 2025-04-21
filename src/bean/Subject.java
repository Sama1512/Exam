package bean;

public class Subject implements java.io.Serializable {
	private String cd,name;
	private School school;

	public String getCd() {
		return cd;
	}

	public String getName() {
		return name;
	}

	public School getSchool() {
		return school;
	}

	public void setCd(String cd) {
		this.cd=cd;
	}

	public void setName(String name) {
		this.name=name;
	}

	public void setSchool(School school) {
		this.school=school;
	}
}