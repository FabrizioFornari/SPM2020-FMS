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
			sql = 	"SELECT SUM((TIMESTAMPDIFF(MINUTE,Occupancy_start,Occupancy_end)/60)*parkingspace.Parking_fee) as earnedMoney,parkingspace.Name " + 
							"FROM reservation,parkingspace " + 
							"WHERE parkingspace.ID = reservation.ParkingSpace " + 
							"GROUP BY ParkingSpace ";
		} else {
			sql = 	"SELECT SUM((TIMESTAMPDIFF(MINUTE,Occupancy_start,Occupancy_end)/60)*parkingspace.Parking_fee) as earnedMoney, parkingspace.Name as parkSpace " + 
							"FROM reservation,parkingspace " + 
							"WHERE parkingspace.ID = reservation.ParkingSpace and reservation.Booking_time >= DATE_SUB( CURDATE(), INTERVAL 1 MONTH ) " + 
							"GROUP BY ParkingSpace ";
		}

		List<Statistic> result = jdbcTemplate.query(sql, new StatisticsMapper());

		return result;		
	}

	public Float totalRevenue(Boolean lastMonth) {
		
		String sql="";
		
		if (lastMonth) {
			sql = 	"SELECT SUM((TIMESTAMPDIFF(MINUTE,Occupancy_start,Occupancy_end)/60)*parkingspace.Parking_fee) as earnedMoney" + 
					"FROM reservation,parkingspace " + 
					"WHERE parkingspace.ID = reservation.ParkingSpace and  reservation.Booking_time >= DATE_SUB( CURDATE(), INTERVAL 1 MONTH )";
		} else {
			sql = 	"SELECT SUM((TIMESTAMPDIFF(MINUTE,Occupancy_start,Occupancy_end)/60)*parkingspace.Parking_fee) as earnedMoney" + 
					"FROM reservation,parkingspace " + 
					"WHERE parkingspace.ID = reservation.ParkingSpace";
		}

		Float result = jdbcTemplate.queryForObject(sql, Float.class);

		return result;
	}
	
	class StatisticsMapper implements RowMapper<Statistic> {

		public Statistic mapRow(ResultSet rs, int arg1) throws SQLException {
			Statistic statistic = new Statistic(
					rs.getString("ParkSpace"),
					rs.getFloat("earnedMoney")
			);
			return statistic;
		}
		
	}
	
}
