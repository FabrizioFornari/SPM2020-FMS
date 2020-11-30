package Unicam.SPM2020_FMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;


import Unicam.SPM2020_FMS.dao.ReservationsDao;
import Unicam.SPM2020_FMS.model.PolicemanUsers;

public class ReservationsService {

	 @Autowired
	  public ReservationsDao reservationsDao;
	 
	 public List<PolicemanUsers> showReservationsToCheck() {
			return reservationsDao.showReservationsToCheck();
			
		}
}
