package Unicam.SPM2020_FMS.model;

public class Car {
	
	private String licensePlateNumber;
	private int driver;
	private String model;
	
	public Car(String licensePlateNumber, int driver, String model) {
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

	public int getDriver() {
		return driver;
	}

	public void setDriver(int driver) {
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
