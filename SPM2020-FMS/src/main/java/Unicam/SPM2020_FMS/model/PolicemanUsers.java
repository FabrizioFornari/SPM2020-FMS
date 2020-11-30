package Unicam.SPM2020_FMS.model;

public class PolicemanUsers {

	private String licensePlateNumber;
	private Integer driver;
	private String model;
	private String name;
	private String surname;
	private String email;
	private String phoneNumber;
	
	
	
	public PolicemanUsers(String licensePlateNumber, Integer driver, String model, String name, String surname,
			String email, String phoneNumber) {
		super();
		this.licensePlateNumber = licensePlateNumber;
		this.driver = driver;
		this.model = model;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.phoneNumber = phoneNumber;
	}
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	public Integer getDriver() {
		return driver;
	}
	public void setDriver(Integer driver) {
		this.driver = driver;
	}
	public String getModel() {
		return model;
	}
	public void setModel(String model) {
		this.model = model;
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
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
}
