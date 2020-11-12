package Unicam.SPM2020_FMS.model;

public class Car {
	
	private String licensePlateNumber;
	private Integer driver;
	private String model;
	
	public Car() {
		super();
	}

	public Car(String licensePlateNumber, Integer driver, String model) {
		super();
		this.licensePlateNumber = licensePlateNumber;
		this.driver = driver;
		this.model = model;
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

	@Override
	public String toString() {
		return "Car [licensePlateNumber=" + licensePlateNumber + ", driver=" + driver + ", model=" + model + "]";
	}

}
