package Unicam.SPM2020_FMS.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import Unicam.SPM2020_FMS.model.ParkingSpot;

public class ParkSpotDao {

  @Autowired
  DataSource datasource;

  @Autowired
  JdbcTemplate jdbcTemplate;
	  
  public int generateSpots (List<ParkingSpot> spots) {
	  	
	  String sql = "INSERT INTO parkingspot VALUES (?,?,?,?,?)";
	  int res=0;
	  
	  for (ParkingSpot spot : spots) {
		try {
			jdbcTemplate.update(
				sql, 
				new Object[] {
					spot.getSpotNumber(),
					spot.getParkingSpace(),
					spot.getOccupied(),
					spot.getIsRestricted(),
					spot.getIsCovered()
				}
			);
			res++;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return res*=-1;
		}
	  }
	  return res;
  }
  
}
