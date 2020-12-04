package Unicam.SPM2020_FMS.model;

public class Reservation {

	private String licensePlateNumber;
	private String parkingSpot;
	private String parkingSpace;
	private String parkingStart;
	private String parkingEnd;

	public Reservation(String licensePlateNumber,
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
	
	public String getParkingStart() {
		return parkingStart;
	}

	public void setParkingStart(String parkingStart) {
		this.parkingStart = parkingStart;
	}
	
	public String getParkingEnd() {
		return parkingEnd;
	}
	
	public void setParkingEnd(String parkingEnd) {
		this.parkingEnd = parkingEnd;
	}
		
}
