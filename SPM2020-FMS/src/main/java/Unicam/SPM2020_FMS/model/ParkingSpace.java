package Unicam.SPM2020_FMS.model;

public class ParkingSpace {

	private Integer idParkingSpace;
	private String name;
	private String address;
	private String coordinates;
	private Integer spotsCapacity;
	private Integer coveredSpots;
	private Integer handicapSpots;
	private boolean guarded = false;
	
	public ParkingSpace() {
		super();
	}
	
	public ParkingSpace(Integer idParkingSpace, String name, String address, String coordinates,
			Integer spotsCapacity, Integer coveredSpots, Integer handicapSpots, boolean isGuarded ) {
		super();
		this.idParkingSpace = idParkingSpace;
		this.name = name;
		this.address = address;
		this.coordinates = coordinates;
		this.spotsCapacity = spotsCapacity;
		this.coveredSpots = coveredSpots;
		this.handicapSpots = handicapSpots;
		this.guarded = isGuarded;
	}

	public Integer getIdParkingSpace() {
		return idParkingSpace;
	}

	public void setIdParkingSpace(Integer idParkingSpace) {
		this.idParkingSpace = idParkingSpace;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinates) {
		this.coordinates = coordinates;
	}

	public Integer getSpotsCapacity() {
		return spotsCapacity;
	}

	public void setSpotsCapacity(Integer spotsCapacity) {
		this.spotsCapacity = spotsCapacity;
	}
	
	public Integer getCoveredSpots() {
		return coveredSpots;
	}

	public void setCoveredSpots(Integer coveredSpots) {
		this.coveredSpots = coveredSpots;
	}

	public Integer getHandicapSpots() {
		return handicapSpots;
	}

	public void setHandicapSpots(Integer handicapSpots) {
		this.handicapSpots = handicapSpots;
	}



	
	public boolean isGuarded() {
		return guarded;
	}

	public void setGuarded(boolean guarded) {
		this.guarded = guarded;
	}

	@Override
	public String toString() {
		return "ParkingSpace [idParkingSpace=" + idParkingSpace + ", name=" + name + ", address=" + address
				+ ", coordinates=" + coordinates + ", spotsCapacity=" + spotsCapacity + ", coveredSpots=" + coveredSpots
				+ ", handicapSpots=" + handicapSpots + ", isGuarded=" + guarded + "]";
	}
	
}
