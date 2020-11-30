package Unicam.SPM2020_FMS.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import Unicam.SPM2020_FMS.dao.CarDao;
import Unicam.SPM2020_FMS.model.Car;
import Unicam.SPM2020_FMS.model.PolicemanUsers;

public class CarService {

  @Autowired
  public CarDao carDao;

  public int register(Car car) {
    return carDao.register(car);
  }

  public List<Car> showCars(Integer idUser) {
	return carDao.showCars(idUser);	
  }
  
  public int modifyCars(List<Car> newCars, List<Car> oldCars) {
	  int res=0;
	  for (int i = 0; i < newCars.size(); i++) {
		  Car newCar= newCars.get(i);
		  Car oldCar= oldCars.get(i);
		  if (!(newCar.equals(oldCar))) {
			  res=carDao.updateCar(newCar, oldCar);			  
		  }
	  }
	  return res;
  }
  
  public int deleteCar(Car car) {
	    return carDao.deleteCar(car);
  }

public List<PolicemanUsers> showCars() {
	return carDao.showCars();
	
}

}
