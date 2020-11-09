package jbr.springmvc.model;

public class Policeman extends User{
	
	private String name;
	private String surname;
	private int phoneNumbeer;
	private int idNumber;
	
	public Policeman(int idUser, String password, String userType) {
		super(idUser, password, userType);
		// TODO Auto-generated constructor stub
	}

	public Policeman(int idUser, String password, String userType, String name, String surname, int phoneNumbeer,
			int idNumber) {
		super(idUser, password, userType);
		this.name = name;
		this.surname = surname;
		this.phoneNumbeer = phoneNumbeer;
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

	public int getPhoneNumbeer() {
		return phoneNumbeer;
	}

	public void setPhoneNumbeer(int phoneNumbeer) {
		this.phoneNumbeer = phoneNumbeer;
	}

	public int getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(int idNumber) {
		this.idNumber = idNumber;
	}

	@Override
	public String toString() {
		return "Policeman [name=" + name + ", surname=" + surname + ", phoneNumbeer=" + phoneNumbeer + ", idNumber="
				+ idNumber + ", getIdUser()=" + getIdUser() + ", getPassword()=" + getPassword() + ", getUserType()="
				+ getUserType() + "]";
	}
	
}
