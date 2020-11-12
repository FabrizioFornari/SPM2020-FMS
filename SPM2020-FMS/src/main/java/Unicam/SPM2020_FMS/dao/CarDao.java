package Unicam.SPM2020_FMS.dao;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import Unicam.SPM2020_FMS.model.Car;

public class CarDao {

  @Autowired
  DataSource datasource;

  @Autowired
  JdbcTemplate jdbcTemplate;
	  
  public int register(Car car) {
    String sql = "INSERT INTO car VALUES (?,?,?)";

    //try {
    return jdbcTemplate.update(
    	sql,
    	new Object[]{ 
    			car.getLicensePlateNumber(),
    			car.getDriver(),
    			car.getModel(),
    	}
    );
    //} catch (SQLException e) {
    	//TODO
		//trovare unique key violation e se possibile quale chiave
	//}
  }

}
