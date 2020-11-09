package jbr.springmvc.model;

public class User {
	
	private int idUser;
	private String password;
	private String userType;
	
	public User(int idUser, String password, String userType) {
		super();
		this.idUser = idUser;
		this.password = password;
		this.userType = userType;
	}
	
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUserType() {
		return userType;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", password=" + password + ", userType=" + userType + "]";
	}

}
