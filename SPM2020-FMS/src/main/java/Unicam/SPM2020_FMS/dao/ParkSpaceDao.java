package Unicam.SPM2020_FMS.dao;

import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import Unicam.SPM2020_FMS.model.ParkingSpace;

public class ParkSpaceDao {

  @Autowired
  DataSource datasource;

  @Autowired
  JdbcTemplate jdbcTemplate;
	  
  public int add(ParkingSpace newParkSpace) {
    String sql = "INSERT INTO parkingspace (`Name`, `Address`, `Coordinates`, `Spots_capacity`, `Covered_spots`, `Handicap_spots`, `IsGuarded`) VALUES (?,?,?,?,?,?,?)";

	KeyHolder parkSpaceKeyHolder = new GeneratedKeyHolder();
	int err=0;

	try {
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, newParkSpace.getName());
			ps.setString(2, newParkSpace.getAddress());
			ps.setString(3, newParkSpace.getCoordinates());
			ps.setObject(4, newParkSpace.getSpotsCapacity());
			ps.setObject(5, newParkSpace.getCoveredSpots());
			ps.setObject(6, newParkSpace.getHandicapSpots());
			ps.setObject(7, newParkSpace.isGuarded());
			return ps;
		}, parkSpaceKeyHolder);
	} catch (org.springframework.dao.DuplicateKeyException e) {
		String msg=e.getMessage();

		if (msg.contains("parkingspace.Coordinates")) {
			err=-1;
		}
		return err;
	} catch (Exception e) {
		return err;
	}
	
	return parkSpaceKeyHolder.getKey().intValue();
  }
  
}
