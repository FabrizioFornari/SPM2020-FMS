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

	public List<ParkingSpot> showCoveredSpots(Integer parkingSpace) {

		String sql = "SELECT * FROM parkingspot where ParkingSpace = '" + parkingSpace + "' and isCovered = 1";

		List<ParkingSpot> coveredSpotsList = jdbcTemplate.query(sql, new ParkSpotMapper());

		return coveredSpotsList;
	}

	public List<ParkingSpot> showHandicapSpots(Integer parkingSpace) {

		String sql = "SELECT * FROM parkingspot where ParkingSpace = '" + parkingSpace + "' and isRestricted = 1";

		List<ParkingSpot> handicapSpotsList = jdbcTemplate.query(sql, new ParkSpotMapper());

		return handicapSpotsList;
	}

	class ParkSpotMapper implements RowMapper<ParkingSpot> {

		public ParkingSpot mapRow(ResultSet rs, int arg1) throws SQLException {

			ParkingSpot parkSpot = new ParkingSpot(rs.getInt("SpotNumber"), rs.getInt("ParkingSpace"),
					rs.getInt("IsOccupied"), rs.getInt("IsRestricted"), rs.getInt("IsCovered")

			);

			return parkSpot;
		}
	}

	public int updateSpots(List<ParkingSpot> spots) {
		// Take the spots already present on database to check what values (if any) are
		// changed
		String sql = "SELECT * FROM parkingspot where ParkingSpace = '" + spots.get(0).getParkingSpace() + "' ";
		List<ParkingSpot> spotsList = jdbcTemplate.query(sql, new ParkSpotMapper());
		int res = 0;
		// Take the difference between the capacity of the parking space already present
		// and the modified one
		int diff = spotsList.size() - spots.size();
		// If the number of the parking spots already present are more than the modified
		// one, delete those in excess
		int index = spotsList.size()+1;
		while ( diff != 0) {
			
			if (diff > 0) {

				String sql2 = "DELETE FROM parkingspot where ParkingSpace = '" + spots.get(0).getParkingSpace()
						+ "' order by SpotNumber desc limit 1 ";
				
				try {
					jdbcTemplate.update(sql2);
					res++;
				} catch (Exception e) {
					System.out.println(e.getMessage());
					return res *= -1;
				}
				
				diff--;
				// If the number of the parking spots already present are less than the modified
				// one, insert them
			}else {
				
				String sql2 = "INSERT INTO parkingspot VALUES (?,?,?,?,?)";
				
						try {
							jdbcTemplate.update(sql2, new Object[] { spots.get(index-1).getSpotNumber(), spots.get(index-1).getParkingSpace(),
									spots.get(index-1).getOccupied(), spots.get(index-1).getIsRestricted(), spots.get(index-1).getIsCovered() });
							res++;
						} catch (Exception e) {
							System.out.println(e.getMessage());
							return res *= -1;
						}
			 diff++;			
			 index++;	
			}
		}
		

		return 0;
	}

}
