package Unicam.SPM2020_FMS.model;

public class ParkingSpace {
	
	private Integer idParkingSpace;
	private String name;
	private String address;
	private String coordinates;
	private Integer spotsCapacity;
	
	public ParkingSpace(Integer idParkingSpace, String name, String address, String coordinates,
			Integer spotsCapacity) {
		super();
		this.idParkingSpace = idParkingSpace;
		this.name = name;
		this.address = address;
		this.coordinates = coordinates;
		this.spotsCapacity = spotsCapacity;
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

	@Override
	public String toString() {
		return "ParkingSpace [idParkingSpace=" + idParkingSpace + ", name=" + name + ", address=" + address
				+ ", coordinates=" + coordinates + ", spotsCapacity=" + spotsCapacity + "]";
	}
	
}
