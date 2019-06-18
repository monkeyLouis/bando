package hello.domain.form;

public class RegisterForm {

	private String userName;
	private String name;
	private String formPassword;
	private String rePassword;

	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFormPassword() {
		return formPassword;
	}
	public void setFormPassword(String formPassword) {
		this.formPassword = formPassword;
	}
	public String getRePassword() {
		return rePassword;
	}
	public void setRePassword(String rePassword) {
		this.rePassword = rePassword;
	}

}
