package Unicam.SPM2020_FMS.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import Unicam.SPM2020_FMS.model.Car;
import Unicam.SPM2020_FMS.model.PolicemanUsers;

public class CarDao {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public int register(Car car) {
		String sql = "INSERT INTO car VALUES (?,?,?)";

		// try {
		return jdbcTemplate.update(sql, new Object[] { car.getLicensePlateNumber(), car.getDriver(), car.getModel(), });
	}

	public List<Car> showCars(Integer idUser) {

		String sql = "SELECT * FROM car WHERE Driver = '" + idUser + "'";

		List<Car> cars = jdbcTemplate.query(sql, new CarMapper());

		return cars;
	}

	public int updateCar(Car newCar, Car oldCar) {

		String sql = "UPDATE car SET LicensePlateNumber = ?, Model = ? WHERE Driver = ? and LicensePlateNumber= ? ";
		int updated;

		try {
			updated = jdbcTemplate.update(sql, new Object[] { newCar.getLicensePlateNumber(), newCar.getModel(),
					oldCar.getDriver(), oldCar.getLicensePlateNumber() });
		} catch (Exception e) {
			return -1;
		}
		return updated;
	}

	public int deleteCar(Car car) {
		String sql = "DELETE FROM car WHERE Driver = ? and LicensePlateNumber= ? ";
		int deleted;

		try {
			deleted = jdbcTemplate.update(sql, new Object[] { car.getDriver(), car.getLicensePlateNumber(), });
		} catch (Exception e) {
			return -1;
		}
		return deleted;
	}

	public List<PolicemanUsers> showCars() {

		String sql = "SELECT LicensePlateNumber,Driver,Model,Name,Surname,Email,Phone_number FROM car,user WHERE car.Driver = user.ID";

		List<PolicemanUsers> cars = jdbcTemplate.query(sql, new CarAndUserMapper());

		return cars;
	}

	class CarMapper implements RowMapper<Car> {

		public Car mapRow(ResultSet rs, int arg1) throws SQLException {
			Car car = new Car(rs.getString("LicensePlateNumber"), rs.getInt("Driver"), rs.getString("Model"));

			return car;
		}
	}

	class CarAndUserMapper implements RowMapper<PolicemanUsers> {

		public PolicemanUsers mapRow(ResultSet rs, int arg1) throws SQLException {
			PolicemanUsers car = new PolicemanUsers(rs.getString("LicensePlateNumber"), rs.getInt("Driver"),
					rs.getString("Model"), rs.getString("Name"), rs.getString("Surname"), rs.getString("Email"),
					rs.getString("Phone_number"));

			return car;
		}
	}

}
