package Unicam.SPM2020_FMS.model;

public class User {
	
	private int idUser;
	private String name;
	private String surname;
	private String password;
	private String taxCode;
	private int phoneNumber;
	private String userType;
	private int idNumber;
	private int authNumber;	
	
	
	public User(int idUser, String name, String surname, String password, String taxCode, int phoneNumber,
			String userType, int idNumber, int authNumber) {
		this.idUser = idUser;
		this.name = name;
		this.surname = surname;
		this.password = password;
		this.taxCode = taxCode;
		this.phoneNumber = phoneNumber;
		this.userType = userType;
		this.idNumber = idNumber;
		this.authNumber = authNumber;
	}

	public int getIdUser() {
		return idUser;
	}


	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getSurname() {
		return surname;
	}


	public void setSurname(String surname) {
		this.surname = surname;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public String getTaxCode() {
		return taxCode;
	}


	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}


	public int getPhoneNumber() {
		return phoneNumber;
	}


	public void setPhoneNumber(int phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	public String getUserType() {
		return userType;
	}


	public void setUserType(String userType) {
		this.userType = userType;
	}


	public int getIdNumber() {
		return idNumber;
	}


	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}


	public int getAuthNumber() {
		return authNumber;
	}


	public void setAuthNumber(int authNumber) {
		this.authNumber = authNumber;
	}

}
