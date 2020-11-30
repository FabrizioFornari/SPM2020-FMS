package Unicam.SPM2020_FMS.model;

public class PolicemanUsers {

	private String licensePlateNumber;

	private String parkingSpot;
	private String parkingSpace;
	private String parkingEnd;

	
	
	

	public PolicemanUsers(String licensePlateNumber,
			String parkingSpot, String parkingSpace, String parkingEnd) {
		super();
		this.licensePlateNumber = licensePlateNumber;

		this.parkingSpot = parkingSpot;
		this.parkingSpace = parkingSpace;
		this.parkingEnd = parkingEnd;
	
	}
	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	
	
	public String getParkingSpot() {
		return parkingSpot;
	}
	public void setParkingSpot(String parkingSpot) {
		this.parkingSpot = parkingSpot;
	}
	public String getParkingSpace() {
		return parkingSpace;
	}
	public void setParkingSpace(String parkingSpace) {
		this.parkingSpace = parkingSpace;
	}
	public String getParkingEnd() {
		return parkingEnd;
	}
	public void setParkingEnd(String parkingEnd) {
		this.parkingEnd = parkingEnd;
	}
	
	
}
