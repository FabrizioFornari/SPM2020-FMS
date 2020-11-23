package Unicam.SPM2020_FMS.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;


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

public List<Car> showCars(Integer idUser) {
	
	String sql = "SELECT * FROM car WHERE Driver = '"+idUser+"'";
	
	List<Car> cars = jdbcTemplate.query(sql, new CarMapper());
	
	return cars;
}

class CarMapper implements RowMapper<Car> {

	public Car mapRow(ResultSet rs, int arg1) throws SQLException {
		Car car = new Car(rs.getString("LicensePlateNumber"), rs.getInt("Driver"), rs.getString("Model"));

		return car;
	}
}

}
