package Unicam.SPM2020_FMS.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import Unicam.SPM2020_FMS.model.ParkingSpot;

public class ParkSpotDao {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate jdbcTemplate;

	public int generateSpots(List<ParkingSpot> spots) {

		String sql = "INSERT INTO parkingspot VALUES (?,?,?,?,?)";

		int res = 0;

		for (ParkingSpot spot : spots) {

			try {
				jdbcTemplate.update(sql, new Object[] { spot.getSpotNumber(), spot.getParkingSpace(),
						spot.getOccupied(), spot.getIsRestricted(), spot.getIsCovered() });
				res++;
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return res *= -1;
			}
		}
		return res;
	}

	public List<Integer> getCoveredSpotsNs(Integer parkingSpace) {

		String sql ="SELECT spotnumber FROM parkingspot where ParkingSpace = '" + parkingSpace + "' and isCovered = 1 order by 1";

		List<Integer> coveredSpotsNumList = jdbcTemplate.queryForList(sql, Integer.class);

		return coveredSpotsNumList;
	}

	public List<Integer> getHandicapSpotsNs(Integer parkingSpace) {

		String sql = "SELECT spotnumber FROM parkingspot where ParkingSpace = '" + parkingSpace + "' and isRestricted = 1 order by 1";

		List<Integer> handicapSpotsNumList = jdbcTemplate.queryForList(sql, Integer.class);

		return handicapSpotsNumList;
	}
	
	public int getAvailable (Integer parkingSpace) {

		String sql ="SELECT COUNT(*) FROM parkingspot where ParkingSpace = '" + parkingSpace + "' and isOccupied = 0";

		int res = jdbcTemplate.queryForObject(sql, Integer.class);

		return res;
	}
	
	public Integer getCoveredAvailable (Integer parkingSpace) {

		String sql ="SELECT COUNT(*) FROM parkingspot where ParkingSpace = '" + parkingSpace + "' and isCovered = 1 and isOccupied = 0";

		int res = jdbcTemplate.queryForObject(sql, Integer.class);

		return res;
	}

	public Integer getHandicapAvailable (Integer parkingSpace) {

		String sql ="SELECT COUNT(*) FROM parkingspot where ParkingSpace = '" + parkingSpace + "' and isRestricted = 1 and isOccupied = 0";

		int res = jdbcTemplate.queryForObject(sql, Integer.class);

		return res;
	}

	class ParkSpotMapper implements RowMapper<ParkingSpot> {

		public ParkingSpot mapRow(ResultSet rs, int arg1) throws SQLException {

			ParkingSpot parkSpot = new ParkingSpot(
					rs.getInt("SpotNumber"),
					rs.getInt("ParkingSpace"),
					rs.getInt("IsOccupied"),
					rs.getInt("IsRestricted"),
					rs.getInt("IsCovered")
			);

			return parkSpot;
		}
	}

	
	/**
	 * @param spots
	 * @return number affected rows: if positive is the sum of all rows affected (deleted/added + updated) if negative number of affected rows (added) before error
	 */
	public int updateSpots(List<ParkingSpot> spots) {
		
		// Take the spots already existing in the DB
		String sql = "SELECT * FROM parkingspot where ParkingSpace = '" + spots.get(0).getParkingSpace() + "' order by spotNumber ";
		List<ParkingSpot> DBspotsList = jdbcTemplate.query(sql, new ParkSpotMapper());
		
		List<ParkingSpot> toUpdateList;
		
		int res;
		
		// Take the difference between the capacity of the parking space already present and the modified one
		int diff = DBspotsList.size() - spots.size();
		
		// If the number of the parking spots already existing are higher than the new one, delete spot in excess
		// and update remaining ones
		if (diff > 0) {
			sql = "DELETE FROM parkingspot WHERE ParkingSpace = '"
						+ spots.get(0).getParkingSpace() + 
						"' and spotNumber> '"
						+ spots.size() +
						"'";
			try {
				res=jdbcTemplate.update(sql);
			} catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
			toUpdateList = spots.subList(0, spots.size());
			
		// If the number of the parking spots already existing are less than the modified one, insert the ones missing 
		// and update only the others
		} else { 
			res=generateSpots(spots.subList(DBspotsList.size(), spots.size()));
			
			if (res<0) {
				return res;
			}
			
			toUpdateList = spots.subList(0, DBspotsList.size());
		}		
		
		for (ParkingSpot parkingSpot : toUpdateList) {
			
			sql = "UPDATE parkingspot SET isRestricted = ?, isCovered = ?  WHERE ParkingSpace = ? and SpotNumber = ? ";
			try {
				 res += jdbcTemplate.update(sql,new Object[] { 
						 parkingSpot.getIsRestricted(),
						 parkingSpot.getIsCovered(),
						 parkingSpot.getParkingSpace(),
						 parkingSpot.getSpotNumber() 
						});
			}  catch (Exception e) {
				System.out.println(e.getMessage());
				return 0;
			}
		}
		
		return res;
	}

}
