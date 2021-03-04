package Unicam.SPM2020_FMS.model;

public class User {
	
	private Integer idUser;
	private String name;
	private String surname;
	private String email;
	private String password;
	private String taxCode;
	private String phoneNumber;
	private String userType;
	private String idNumber;
	private String authNumber;
	private Integer paymentTypeId;
	
	public User() {
		super();
	}

	public User(Integer idUser, String name, String surname, String email, String password, String taxCode, String phoneNumber,
			String userType, String idNumber, String authNumber, Integer paymentTypeId) {
		this.idUser = idUser;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		/** Tax code that identifies all type of users */
		this.taxCode = taxCode;
		this.phoneNumber = phoneNumber;
		/** Can be Driver,Policeman or Municipality*/
		this.userType = userType;
		/** Serial number that identifies exclusively a policeman*/
		this.idNumber = idNumber;
		/** Serial number that identifies exclusively a municipality*/
		this.authNumber = authNumber;
		this.paymentTypeId = paymentTypeId;
	}
	

	public Integer getIdUser() {
		return idUser;
	}

	public void setIdUser(Integer idUser) {
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	public String getAuthNumber() {
		return authNumber;
	}

	public void setAuthNumber(String authNumber) {
		this.authNumber = authNumber;
	}

	public Integer getPaymentTypeId() {
		return paymentTypeId;
	}

	public void setPaymentTypeId(Integer paymentTypeId) {
		this.paymentTypeId = paymentTypeId;
	}
	

	@Override
	public String toString() {
		return "User [idUser=" + idUser + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password="
				+ password + ", taxCode=" + taxCode + ", phoneNumber=" + phoneNumber + ", userType=" + userType
				+ ", idNumber=" + idNumber + ", authNumber=" + authNumber + ", paymentType=" + paymentTypeId + "]";
	}
}
