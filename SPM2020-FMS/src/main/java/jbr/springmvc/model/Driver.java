package jbr.springmvc.model;

public class Driver extends User{
	
	private String name;
	private String surname;
	private String taxCode;
	private int phoneNumber;
	private int idNumber;
	
	public Driver(int idUser, String password, String userType) {
		super(idUser, password, userType);
		// TODO Auto-generated constructor stub
	}

	public Driver(int idUser, String password, String userType, String name, String surname, String taxCode,
			int phoneNumber, int idNumber) {
		super(idUser, password, userType);
		this.name = name;
		this.surname = surname;
		this.taxCode = taxCode;
		this.phoneNumber = phoneNumber;
		this.idNumber = idNumber;
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

	public int getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}

	@Override
	public String toString() {
		return "Driver [name=" + name + ", surname=" + surname + ", taxCode=" + taxCode + ", phoneNumber=" + phoneNumber
				+ ", idNumber=" + idNumber + ", getIdUser()=" + getIdUser() + ", getPassword()=" + getPassword()
				+ ", getUserType()=" + getUserType() + "]";
	}
	
}
