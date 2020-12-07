package Unicam.SPM2020_FMS.model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class ParkingSpace {

	private Integer idParkingSpace;
	private String name;
	private String address;
	private String coordinates;
	private Integer spotsCapacity;
	private Integer coveredSpots;
	private Integer handicapSpots;
	private boolean guarded = false;
	private String specCovered;
	private String specHandicap;
	private String image;
	
	public ParkingSpace() {
		super();
	}

	public ParkingSpace(Integer idParkingSpace, String name, String address, String coordinates, Integer spotsCapacity,
			Integer coveredSpots, Integer handicapSpots, boolean guarded, String specCovered, String specHandicap, String image) {
		
		super();
		this.idParkingSpace = idParkingSpace;
		this.name = name;
		this.address = address;
		this.coordinates = coordinates;
		this.spotsCapacity = spotsCapacity;
		this.coveredSpots = coveredSpots;
		this.handicapSpots = handicapSpots;
		this.guarded = guarded;
		this.specCovered = specCovered;
		this.specHandicap = specHandicap;
		this.image = image;
		
		if (this.getCoveredSpotsNumbers().count() != this.coveredSpots) {
			throw new IllegalArgumentException("Wrong covered spots specification");
		}
		
		if (this.getHandicapSpotsNumbers().count() != this.handicapSpots) {
			throw new IllegalArgumentException("Wrong restricted spots specification");
		}
		
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
		if (specCovered!=null)
			this.getCoveredSpotsNumbers();
		if (specHandicap!=null)
			this.getCoveredSpotsNumbers();
	}
	
	public Integer getCoveredSpots() {
		return coveredSpots;
	}

	public void setCoveredSpots(Integer coveredSpots) {
		if (specCovered!=null)
			if (this.getCoveredSpotsNumbers().count() != coveredSpots) 
				throw new IllegalArgumentException("Wrong covered spots specification");
		this.coveredSpots = coveredSpots;
	}

	public Integer getHandicapSpots() {
		return handicapSpots;
	}

	public void setHandicapSpots(Integer handicapSpots) {
		if (specHandicap!=null)
			if (this.getHandicapSpotsNumbers().count() != handicapSpots)
				throw new IllegalArgumentException("Wrong restricted spots specification");
		this.handicapSpots = handicapSpots;
	}
	
	public boolean isGuarded() {
		return guarded;
	}

	public void setGuarded(boolean guarded) {
		this.guarded = guarded;
	}

	public String getSpecHandicap() {
		return specHandicap;
	}

	public void setSpecHandicap(String specHandicap) {
		String temp=this.specHandicap;
		this.specHandicap = specHandicap;
		if (handicapSpots!=null)
			if (this.getHandicapSpotsNumbers().count() != handicapSpots) {
				this.specHandicap=temp;
				throw new IllegalArgumentException("Wrong restricted spots specification");
			}
	}

	public String getSpecCovered() {
		return specCovered;
	}

	public void setSpecCovered(String specCovered) {
		String temp=this.specCovered;
		this.specCovered = specCovered;
		if (handicapSpots!=null)
			if (this.getCoveredSpotsNumbers().count() != coveredSpots) {
				this.specCovered=temp;
				throw new IllegalArgumentException("Wrong covered spots specification");
			}
	}
	
	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return "ParkingSpace [idParkingSpace=" + idParkingSpace + ", name=" + name + ", address=" + address
				+ ", coordinates=" + coordinates + ", spotsCapacity=" + spotsCapacity + ", coveredSpots=" + coveredSpots
				+ ", handicapSpots=" + handicapSpots + ", guarded=" + guarded + ", specCovered=" + specCovered
				+ ", specHandicap=" + specHandicap + ", image=" + image + "]";
	}

	public IntStream getCoveredSpotsNumbers() {
        IntStream CoveredSpotsNumbers=IntStream.of();
        
		String[] parts=specCovered.split(",");		
		for (String part : parts) {
			String[] spots = part.split("-");
			int a,b;
			try {
				a=Integer.parseInt(spots[0]);
				b=Integer.parseInt(spots[spots.length-1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Wrong covered spots specification");
			}
			if (a>b) {
				int temp=a;
				a=b;
				b=temp;
			}
			if (a<1 || (spotsCapacity!=null && b>spotsCapacity)) throw new IllegalArgumentException("Wrong covered spots specification");
			CoveredSpotsNumbers = IntStream.concat(
					CoveredSpotsNumbers, 
					IntStream.rangeClosed(Integer.parseInt(spots[0]), Integer.parseInt(spots[spots.length-1]))
			);
		}
		
		return CoveredSpotsNumbers.distinct().sorted();
	}
	
	public IntStream getHandicapSpotsNumbers() {
        IntStream handicapSpotsNumbers=IntStream.of();
		
        String[] parts=specHandicap.split(",");
		for (String part : parts) {
			String[] spots = part.split("-");
			int a,b;
			try {
				a=Integer.parseInt(spots[0]);
				b=Integer.parseInt(spots[spots.length-1]);
			} catch (NumberFormatException e) {
				throw new IllegalArgumentException("Wrong restricted spots specification");
			}
			if (a>b) {
				int temp=a;
				a=b;
				b=temp;
			}
			if (a<1 || (spotsCapacity!=null && b>spotsCapacity) ) throw new IllegalArgumentException("Wrong restricted spots specification");
			handicapSpotsNumbers = IntStream.concat(
					handicapSpotsNumbers, 
					IntStream.rangeClosed(a,b)
			);
		}
		
		return handicapSpotsNumbers.distinct().sorted();
	}
	
	public List<ParkingSpot> getSpots() {
		List<ParkingSpot> spots = new ArrayList<ParkingSpot>();
		
		IntStream.rangeClosed(1, this.spotsCapacity).forEach(
			i -> {
				ParkingSpot spot = new ParkingSpot(i, this.idParkingSpace, 0, 0, 0);
				if (this.getCoveredSpotsNumbers().anyMatch(n -> n==i)) {
					spot.setIsCovered(1);
				}
				if (this.getHandicapSpotsNumbers().anyMatch(n -> n==i)) {
					spot.setIsRestricted(1);
				}
				spots.add(spot);
			}
		);
		
		return spots;	
	}

}

