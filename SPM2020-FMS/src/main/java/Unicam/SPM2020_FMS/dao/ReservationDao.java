package Unicam.SPM2020_FMS.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import Unicam.SPM2020_FMS.model.Reservation;

public class ReservationDao {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Reservation> showReservationsToCheck() {

		String sql = "SELECT LicensePlateNumber, ParkingSpot, parkingspace.Name as ParkingSpace, Parking_end FROM reservation,parkingspace WHERE reservation.ParkingSpace = parkingspace.ID and Parking_start <= NOW() and Parking_end >= NOW()";

		List<Reservation> reservationsToCheck = jdbcTemplate.query(sql, new ReservationsMapper());

		return reservationsToCheck;
	}

	class ReservationsMapper implements RowMapper<Reservation> {

		public Reservation mapRow(ResultSet rs, int arg1) throws SQLException {
			Reservation reservation = new Reservation(rs.getString("LicensePlateNumber"), rs.getString("ParkingSpot"),
					rs.getString("ParkingSpace"), rs.getString("Parking_end"));

			return reservation;
		}
	}

	
}
