package Unicam.SPM2020_FMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import Unicam.SPM2020_FMS.dao.ParkSpotDao;
import Unicam.SPM2020_FMS.model.ParkingSpot;

public class ParkSpotService {

  @Autowired
  public ParkSpotDao parkSpotDao;

  public int generateSpots (List<ParkingSpot> spots) {
    return parkSpotDao.generateSpots(spots);
  }

}
