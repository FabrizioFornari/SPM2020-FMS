package Unicam.SPM2020_FMS.model;

public class SpotIllegallyOccupied {

	
	private String parkingSpaceName;
	private String parkingSpaceAddress;
	private Integer parkingSpot;
	
	
	
	
	public SpotIllegallyOccupied(String parkingSpaceName, String parkingSpaceAddress, Integer parkingSpot) {
		super();
		this.parkingSpaceName = parkingSpaceName;
		this.parkingSpaceAddress = parkingSpaceAddress;
		this.parkingSpot = parkingSpot;
		
	}
	
	
	public String getParkingSpaceName() {
		return parkingSpaceName;
	}
	public void setParkingSpaceName(String parkingSpaceName) {
		this.parkingSpaceName = parkingSpaceName;
	}
	public String getParkingSpaceAddress() {
		return parkingSpaceAddress;
	}
	public void setParkingSpaceAddress(String parkingSpaceAddress) {
		this.parkingSpaceAddress = parkingSpaceAddress;
	}
	public Integer getParkingSpot() {
		return parkingSpot;
	}
	public void setParkingSpot(Integer parkingSpot) {
		this.parkingSpot = parkingSpot;
	}
	


	@Override
	public String toString() {
		return "SpotIllegallyOccupied [parkingSpaceName=" + parkingSpaceName + ", parkingSpaceAddress="
				+ parkingSpaceAddress + ", parkingSpot=" + parkingSpot +"]";
	}
}
