package Unicam.SPM2020_FMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import Unicam.SPM2020_FMS.dao.ReservationDao;
import Unicam.SPM2020_FMS.model.Reservation;

public class ReservationService {

	@Autowired
	public ReservationDao reservationsDao;
	 
	public List<Reservation> showReservationsToCheck() {
		return reservationsDao.showReservationsToCheck();			
	}
	
	public int addReservation(Reservation reservation) {
		return reservationsDao.addReservation(reservation);
	}
	
	public int deleteReservation(Integer reservation) {
		return reservationsDao.deleteReservation(reservation);
	}
	
	public int changeSpot(Integer reservation, Integer newSpot) {
		return reservationsDao.changeSpot(reservation, newSpot);
	}
	
}
