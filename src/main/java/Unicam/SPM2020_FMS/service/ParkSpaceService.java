package Unicam.SPM2020_FMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import Unicam.SPM2020_FMS.dao.ParkSpaceDao;
import Unicam.SPM2020_FMS.model.ParkingSpace;

public class ParkSpaceService {

  @Autowired
  public ParkSpaceDao parkSpaceDao;

  public int add (ParkingSpace newParkSpace) {
    return parkSpaceDao.add(newParkSpace);
  }
  
  public List<ParkingSpace> showParkSpaceList() {
	return parkSpaceDao.showParkSpaceList();	
  }

public int edit(ParkingSpace parkingSpace) {
	// TODO Auto-generated method stub
	return parkSpaceDao.edit(parkingSpace);
}

}
