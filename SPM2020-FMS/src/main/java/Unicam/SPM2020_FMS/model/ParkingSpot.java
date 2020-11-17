package Unicam.SPM2020_FMS.model;

public class ParkingSpot {
	
	private Integer spotNumber;
	private Integer parkingSpace;
	private Integer isReserved;
	private String restriction;
	
	public ParkingSpot(Integer spotNumber, Integer parkingSpace, Integer isReserved, String restriction) {
		super();
		this.spotNumber = spotNumber;
		this.parkingSpace = parkingSpace;
		this.isReserved = isReserved;
		this.restriction = restriction;
	}

	public Integer getSpotNumber() {
		return spotNumber;
	}

	public void setSpotNumber(Integer spotNumber) {
		this.spotNumber = spotNumber;
	}

	public Integer getParkingSpace() {
		return parkingSpace;
	}

	public void setParkingSpace(Integer parkingSpace) {
		this.parkingSpace = parkingSpace;
	}

	public Integer getIsReserved() {
		return isReserved;
	}

	public void setIsReserved(Integer isReserved) {
		this.isReserved = isReserved;
	}

	public String getRestriction() {
		return restriction;
	}

	public void setRestriction(String restriction) {
		this.restriction = restriction;
	}

	@Override
	public String toString() {
		return "ParkingSpot [spotNumber=" + spotNumber + ", parkingSpace=" + parkingSpace + ", isReserved=" + isReserved
				+ ", restriction=" + restriction + "]";
	}
	
}
