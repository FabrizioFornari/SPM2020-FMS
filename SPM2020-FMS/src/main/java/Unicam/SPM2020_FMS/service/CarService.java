package Unicam.SPM2020_FMS.service;

import org.springframework.beans.factory.annotation.Autowired;

import Unicam.SPM2020_FMS.dao.CarDao;
import Unicam.SPM2020_FMS.model.Car;

public class CarService {

  @Autowired
  public CarDao carDao;

  public int register(Car car) {
    return carDao.register(car);
  }

}
