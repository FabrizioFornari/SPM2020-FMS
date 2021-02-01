package Unicam.SPM2020_FMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import Unicam.SPM2020_FMS.dao.ParkSpotDao;
import Unicam.SPM2020_FMS.model.ParkingSpot;
import Unicam.SPM2020_FMS.model.Reservation;
import Unicam.SPM2020_FMS.model.SpotIllegallyOccupied;

public class ParkSpotService {

	@Autowired
	public ParkSpotDao parkSpotDao;

	public int generateSpots (List<ParkingSpot> spots) {
	    return parkSpotDao.generateSpots(spots);
	}

	public List<Integer> getCoveredSpotsNs(Integer idParkingSpace) {
		return parkSpotDao.getCoveredSpotsNs(idParkingSpace);
	}
	
	public List<Integer> getHandicapSpotsNs(Integer idParkingSpace) {	
		return parkSpotDao.getHandicapSpotsNs(idParkingSpace);
	}
	
	public int getAvailable(Integer idParkingSpace) {	
		return parkSpotDao.getAvailable(idParkingSpace);
	}
	
	public int getCoveredAvailable(Integer idParkingSpace) {	
		return parkSpotDao.getCoveredAvailable(idParkingSpace);
	}
	
	public int getHandicapAvailable(Integer idParkingSpace) {	
		return parkSpotDao.getHandicapAvailable(idParkingSpace);
	}
	
	public int updateSpots(List<ParkingSpot> spots) {
		return parkSpotDao.updateSpots(spots);
	}
	
	public List<SpotIllegallyOccupied> getIllegallyOccupied() {
		return parkSpotDao.getIllegallyOccupied();
	}
	
	public int getFreeSpot(Integer idParkingSpace, Boolean askedCovered, Boolean askedHandicap) {
		return parkSpotDao.getFreeSpotNumber(idParkingSpace, askedCovered, askedHandicap);
	}
	
	public Integer getFreeSpot(Reservation reservation) {
		return parkSpotDao.getFreeSpotNumber(reservation);
	}
	
	public boolean isBusy(Integer parkSpot, Integer parkSpace) {
		return parkSpotDao.isBusy(parkSpot,parkSpace);
	}

}
