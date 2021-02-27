package Unicam.SPM2020_FMS.model;

public class Reservation {

	private Integer id;
	private Integer driver;
	private String licensePlateNumber;
	private Integer parkingSpot;
	private Integer parkingSpaceId;
	private String parkingSpace;
	private String parkingStart;
	private String parkingEnd;
	private Integer paymentType;
	private boolean askedCovered;
	private boolean askedHandicap;

	public Reservation(String licensePlateNumber, Integer parkingSpot, Integer parkingSpaceId, String parkingSpace, String parkingStart, String parkingEnd) {		
		this.licensePlateNumber = licensePlateNumber;
		this.parkingSpot = parkingSpot;
		this.parkingSpaceId = parkingSpaceId;
		this.parkingSpace = parkingSpace;
		this.parkingStart = parkingStart;
		this.parkingEnd = parkingEnd;
	}
	
	public Reservation() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDriver() {
		return driver;
	}

	public void setDriver(Integer driver) {
		this.driver = driver;
	}

	public String getLicensePlateNumber() {
		return licensePlateNumber;
	}
	
	public void setLicensePlateNumber(String licensePlateNumber) {
		this.licensePlateNumber = licensePlateNumber;
	}
	
	public Integer getParkingSpot() {
		return parkingSpot;
	}
	
	public void setParkingSpot(Integer parkingSpot) {
		this.parkingSpot = parkingSpot;
	}
	
	public Integer getParkingSpaceId() {
		return parkingSpaceId;
	}

	public void setParkingSpaceId(Integer parkingSpaceId) {
		this.parkingSpaceId = parkingSpaceId;
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

	public Integer getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(Integer paymentType) {
		this.paymentType = paymentType;
	}

	public boolean isAskedCovered() {
		return askedCovered;
	}

	public void setAskedCovered(boolean askCovered) {
		this.askedCovered = askCovered;
	}

	public boolean isAskedHandicap() {
		return askedHandicap;
	}

	public void setAskedHandicap(boolean askHandicap) {
		this.askedHandicap = askHandicap;
	}
		
}
