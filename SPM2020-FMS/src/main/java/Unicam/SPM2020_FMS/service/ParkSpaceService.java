package Unicam.SPM2020_FMS.service;

import org.springframework.beans.factory.annotation.Autowired;

import Unicam.SPM2020_FMS.dao.ParkSpaceDao;
import Unicam.SPM2020_FMS.model.ParkingSpace;

public class ParkSpaceService {

  @Autowired
  public ParkSpaceDao parkSpaceDao;

  public int add (ParkingSpace newParkSpace) {
    return parkSpaceDao.add(newParkSpace);
  }

}
