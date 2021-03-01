package Unicam.SPM2020_FMS.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import Unicam.SPM2020_FMS.model.Statistic;

public class StatisticsDao {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public List<Statistic> revenueByParkSpace(Boolean lastMonth) {
		
		String sql="";
		
		if (lastMonth) {
			sql = 	"SELECT ROUND(SUM((TIMESTAMPDIFF(MINUTE,Occupancy_start,Occupancy_end)/60)*parkingspace.Parking_fee),2) as quantity, parkingspace.Name as description " + 
					"FROM reservation,parkingspace " + 
					"WHERE parkingspace.ID = reservation.ParkingSpace and Occupancy_end is not null and reservation.Booking_time >= DATE_SUB( CURDATE(), INTERVAL 1 MONTH ) " + 
					"GROUP BY ParkingSpace ";
		} else {
			sql = 	"SELECT ROUND(SUM((TIMESTAMPDIFF(MINUTE,Occupancy_start,Occupancy_end)/60)*parkingspace.Parking_fee),2) as quantity,parkingspace.Name as description " + 
					"FROM reservation,parkingspace " + 
					"WHERE parkingspace.ID = reservation.ParkingSpace and Occupancy_end is not null " + 
					"GROUP BY ParkingSpace ";
		}

		List<Statistic> result = jdbcTemplate.query(sql, new StatisticsMapper());

		return result;		
	}

	public Float totalRevenue(Boolean lastMonth) {
		
		String sql="";
		
		if (lastMonth) {
			sql = 	"SELECT ROUND(SUM((TIMESTAMPDIFF(MINUTE,Occupancy_start,Occupancy_end)/60)*parkingspace.Parking_fee),2) as earnedMoney " + 
					"FROM reservation,parkingspace " + 
					"WHERE parkingspace.ID = reservation.ParkingSpace and  reservation.Booking_time >= DATE_SUB( CURDATE(), INTERVAL 1 MONTH )";
		} else {
			sql = 	"SELECT ROUND(SUM((TIMESTAMPDIFF(MINUTE,Occupancy_start,Occupancy_end)/60)*parkingspace.Parking_fee),2) as earnedMoney " + 
					"FROM reservation,parkingspace " + 
					"WHERE parkingspace.ID = reservation.ParkingSpace";
		}

		Float result = jdbcTemplate.queryForObject(sql, Float.class);

		return result;
	}
	
	public int totalDrivers() {
		
		String sql = "SELECT COUNT(*) FROM user WHERE User_type = 'Driver'";
		
		Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
		
		return result;
		
	}

	public List<Statistic> usersByPayment() {
		
		String sql = 	"SELECT DISTINCT COUNT(*) as quantity, payment.Payment_type as description " +
						"FROM payment, user " + 
						"WHERE user.Default_payment = payment.ID and user.User_type = 'Driver' " +
						"GROUP BY payment.Payment_type";
		
		List<Statistic> result = jdbcTemplate.query(sql, new StatisticsMapper());
		return result;	
	}

	public Integer totalPaymentTypes() {
		
		String sql = "SELECT count(*) FROM payment";

		Integer result = jdbcTemplate.queryForObject(sql, Integer.class);
		
		return result;
	}
	
	class StatisticsMapper implements RowMapper<Statistic> {

		public Statistic mapRow(ResultSet rs, int arg1) throws SQLException {
			Statistic statistic = new Statistic(
					rs.getString("description"),
					rs.getFloat("quantity")
			);
			return statistic;
		}
		
	}
	
}
