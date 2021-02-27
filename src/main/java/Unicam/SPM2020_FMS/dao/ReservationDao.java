package Unicam.SPM2020_FMS.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import Unicam.SPM2020_FMS.model.Reservation;

public class ReservationDao {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Reservation> showReservationsToCheck() {

		String sql = "SELECT LicensePlateNumber, ParkingSpot, ParkingSpace as ParkingSpaceId, parkingspace.Name as ParkingSpace, Parking_start, Parking_end FROM reservation,parkingspace WHERE reservation.ParkingSpace = parkingspace.ID and Parking_start <= NOW() and Parking_end is null";

		List<Reservation> reservationsToCheck = jdbcTemplate.query(sql, new ReservationsMapper());

		return reservationsToCheck;
	}
	
	public List<Reservation> showUserReservations(Integer driver) {

		String sql = "SELECT LicensePlateNumber, ParkingSpot, ParkingSpace as ParkingSpaceId, parkingspace.Name as ParkingSpace, Parking_start, Parking_end FROM reservation,parkingspace WHERE reservation.ParkingSpace = parkingspace.ID and Id_driver='"+driver+"' and Occupancy_end is null";

		List<Reservation> userReservations = jdbcTemplate.query(sql, new ReservationsMapper());

		return userReservations;
	}
	
	class ReservationsMapper implements RowMapper<Reservation> {

		public Reservation mapRow(ResultSet rs, int arg1) throws SQLException {
			Reservation reservation = new Reservation(
					rs.getString("LicensePlateNumber"),
					rs.getInt("ParkingSpot"),
					rs.getInt("ParkingSpaceId"),
					rs.getString("ParkingSpace"),
					rs.getString("Parking_start"),
					rs.getString("Parking_end")
			);

			return reservation;
		}
	}
	
	public int addReservation(Reservation reservation) {
		
		int err=0;
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		boolean immediate=(reservation.getParkingStart()==null);
		KeyHolder reservationKeyHolder = new GeneratedKeyHolder();
	    String sql = "INSERT INTO reservation (`Id_driver`,`LicensePlateNumber`, `ParkingSpot`, `ParkingSpace`, `Parking_start`, `Parking_end`) VALUES (?,?,?,?,?,?)";

	    
		try {
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, reservation.getDriver());
				ps.setString(2, reservation.getLicensePlateNumber());
				ps.setInt(3, reservation.getParkingSpot());
				ps.setInt(4, reservation.getParkingSpaceId());				
				if(immediate){
					//start is now
					ps.setTimestamp(5, new Timestamp(System.currentTimeMillis()), Calendar.getInstance());
					//end is null
					ps.setNull(6, 93);												
				} 
				else {
					try {
						ps.setTimestamp(5, new Timestamp(dateFormat.parse(reservation.getParkingStart()).getTime()), Calendar.getInstance());
						ps.setTimestamp(6, new Timestamp(dateFormat.parse(reservation.getParkingEnd()).getTime()), Calendar.getInstance());
					} catch (ParseException e) {
						throw new IllegalArgumentException("Wrong date specification");
					}
				}
				return ps;
			}, reservationKeyHolder);
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
			return -1;
		} catch (org.springframework.dao.DuplicateKeyException e) {
			String msg=e.getMessage();
			if (msg.contains("Id_parkingSpot")) {
				//parkingstart
				err=-2;
			} else if (msg.contains("Id_parkingSpot_2")) {
				//parkingend
				err=-2;
			}
			return err;
		} catch (Exception e) {
			//e.printStackTrace();
			return -2;
		}
			
		return reservationKeyHolder.getKey().intValue();
	}
	
	public int deleteReservation(Integer reservation) {
		String sql = "DELETE FROM reservation WHERE ID = ? ";
		int deleted;
		
		try {
			deleted = jdbcTemplate.update(sql, reservation);
		} catch (Exception e) {
			//e.printStackTrace();
			return -1;
		}	
		return deleted;
	}
	
	public int changeSpot(Integer reservation, Integer newSpot) {
		String sql = "UPDATE reservation SET ParkingSpot= ? WHERE ID = ? ";
		int updated;
		
		try {
			updated = jdbcTemplate.update(sql, new Object[] {newSpot, reservation});
		} catch (Exception e) {
			//e.printStackTrace();
			return -1;
		}
		
		return updated;
	}
	
	public int closeReservation(Integer id) {
		int updated;
	    String sql = "UPDATE reservation SET Occupancy_start=Parking_start, Occupancy_end=ADDTIME(Parking_start, SEC_TO_TIME(30*60)) WHERE ID = ?";
		
		try {
			updated = jdbcTemplate.update(sql, id);
		} catch (Exception e) {
			//e.printStackTrace();
			return -1;
		}
		
		return updated;
	}

}
