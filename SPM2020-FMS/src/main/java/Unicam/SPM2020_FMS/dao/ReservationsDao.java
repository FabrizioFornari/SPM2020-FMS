package Unicam.SPM2020_FMS.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import Unicam.SPM2020_FMS.model.PolicemanUsers;

public class ReservationsDao {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<PolicemanUsers> showReservationsToCheck() {

		String sql = "SELECT LicensePlateNumber, ParkingSpot, parkingspace.Name as ParkingSpace, Parking_end FROM reservation,parkingspace WHERE reservation.ParkingSpace = parkingSpace.ID and Parking_start <= NOW() and Parking_end >= NOW()";

		List<PolicemanUsers> reservationsToCheck = jdbcTemplate.query(sql, new CarAndUserMapper());

		return reservationsToCheck;
	}

	class CarAndUserMapper implements RowMapper<PolicemanUsers> {

		public PolicemanUsers mapRow(ResultSet rs, int arg1) throws SQLException {
			PolicemanUsers car = new PolicemanUsers(rs.getString("LicensePlateNumber"), rs.getString("ParkingSpot"),
					rs.getString("ParkingSpace"), rs.getString("Parking_end"));

			return car;
		}
	}

	
}
