package Unicam.SPM2020_FMS.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import Unicam.SPM2020_FMS.model.ParkingSpace;

public class ParkSpaceDao {

  @Autowired
  DataSource datasource;

  @Autowired
  JdbcTemplate jdbcTemplate;
	  
  public int add(ParkingSpace newParkSpace) {
    String sql = "INSERT INTO parkingspace (`City`,`Name`, `Address`, `Coordinates`, `Spots_capacity`, `Covered_spots`, `Handicap_spots`, `IsGuarded`,`Image`) VALUES (?,?,?,?,?,?,?,?,?)";

	KeyHolder parkSpaceKeyHolder = new GeneratedKeyHolder();
	int err=0;

	try {
		jdbcTemplate.update(connection -> {
			PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			ps.setString(1, newParkSpace.getCity());
			ps.setString(2, newParkSpace.getName());
			ps.setString(3, newParkSpace.getAddress());
			ps.setString(4, newParkSpace.getCoordinates());
			ps.setObject(5, newParkSpace.getSpotsCapacity());
			ps.setObject(6, newParkSpace.getCoveredSpots());
			ps.setObject(7, newParkSpace.getHandicapSpots());
			ps.setObject(8, newParkSpace.isGuarded());
			ps.setObject(9, newParkSpace.getImageName());
			return ps;
		}, parkSpaceKeyHolder);
	} catch (org.springframework.dao.DuplicateKeyException e) {
		String msg=e.getMessage();
		if (msg.contains("parkingspace.Coordinates")) {
			err=-1;
		}
		return err;
	} catch (Exception e) {
		System.out.println(e.getMessage());
		return err;
	}
	
	return parkSpaceKeyHolder.getKey().intValue();
  }
  
  public List<ParkingSpace> showParkSpaceList() {

	String sql = "SELECT * FROM parkingspace";

	List<ParkingSpace> parkSpaceList = jdbcTemplate.query(sql, new ParkSpaceMapper());

	return parkSpaceList;
  }
  
  
  public int edit(ParkingSpace parkingSpace) {
		
	  
	    String sql = "UPDATE parkingspace SET City = ?, Name = ?, Address = ?, Coordinates = ?, Spots_capacity = ?, Covered_spots = ?, Handicap_spots = ?, IsGuarded = ?, Image = ? WHERE ID = ? ";

		int parkSpaceKeyHolder;
		int err=0;

		try {
			 parkSpaceKeyHolder = jdbcTemplate.update(sql,new Object[] { parkingSpace.getCity(),parkingSpace.getName(),parkingSpace.getAddress(), parkingSpace.getCoordinates(), parkingSpace.getSpotsCapacity(),
					parkingSpace.getCoveredSpots(), parkingSpace.getHandicapSpots(), parkingSpace.isGuarded(), parkingSpace.getImageName(),parkingSpace.getIdParkingSpace() });
		} catch (org.springframework.dao.DuplicateKeyException e) {
			String msg=e.getMessage();
			if (msg.contains("parkingspace.Coordinates")) {
				err=-1;
			}
			return err;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return err;
		}
		
		return parkSpaceKeyHolder;
	}

  class ParkSpaceMapper implements RowMapper<ParkingSpace> {

	public ParkingSpace mapRow(ResultSet rs, int arg1) throws SQLException {
		
		ParkingSpace parkSpace = new ParkingSpace(
			rs.getInt("ID"),
			rs.getString("City"),
			rs.getString("Name"),
			rs.getString("Address"),
			rs.getString("Coordinates"),
			rs.getInt("Spots_capacity"),
			rs.getInt("Covered_spots"),
			rs.getInt("Handicap_spots"),
			rs.getBoolean("IsGuarded"),
			null,
			null,
			rs.getString("Image")

		);

		return parkSpace;
	}
  }


  
}
