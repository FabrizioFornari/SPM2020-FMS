package Unicam.SPM2020_FMS.model;

public class ParkingSpot {
	
	private Integer spotNumber;
	private Integer parkingSpace;
	private Integer occupied;
	private Integer isRestricted;
	private Integer isCovered;
	
	public ParkingSpot(Integer spotNumber, Integer parkingSpace, Integer occupied, Integer isRestricted, Integer isCovered) {
		super();
		this.spotNumber = spotNumber;
		this.parkingSpace = parkingSpace;
		this.occupied = occupied;
		this.isRestricted = isRestricted;
		this.isCovered = isCovered;
	}

	public ParkingSpot() {
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

	public Integer getOccupied() {
		return occupied;
	}

	public void setOccupied(Integer occupied) {
		this.occupied = occupied;
	}

	public Integer getIsRestricted() {
		return isRestricted;
	}

	public void setIsRestricted(Integer isRestricted) {
		this.isRestricted = isRestricted;
	}
	
	public Integer getIsCovered() {
		return isCovered;
	}

	public void setIsCovered(Integer isCovered) {
		this.isCovered = isCovered;
	}
	
	@Override
	public String toString() {
		return "ParkingSpot [spotNumber=" + spotNumber + ", parkingSpace=" + parkingSpace + ", occupied=" + occupied
				+ ", isRestricted=" + isRestricted + ", isCovered=" + isCovered + "]";
	}

}
