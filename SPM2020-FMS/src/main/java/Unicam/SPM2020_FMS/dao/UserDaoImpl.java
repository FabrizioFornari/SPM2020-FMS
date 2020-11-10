package Unicam.SPM2020_FMS.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.User;

public class UserDaoImpl implements UserDao {

  @Autowired
  DataSource datasource;

  @Autowired
  JdbcTemplate jdbcTemplate;
  
  public int register(User user) {
    String sql = "insert into user values(?,?,?,?,?,?,?,?,?,?)";

    return jdbcTemplate.update(sql, new Object[] {1, user.getName(), user.getSurname(), user.getEmail(), user.getPassword(),  "taxcode", user.getPhoneNumber(), "driver",
		        user.getIdNumber(), user.getAuthNumber() });
  }
  
  public User validateUser(Login login) {
    String sql = "select * from user where email='" + login.getUsername() + "' and password='" + login.getPassword() + "'";
    
    List<User> users = jdbcTemplate.query(sql, new UserMapper());

    return users.size() > 0 ? users.get(0) : null;
  }
  
 class UserMapper implements RowMapper<User> {

  public User mapRow(ResultSet rs, int arg1) throws SQLException {
    User user = new User(
    		rs.getInt("ID"),
    	    rs.getString("Name"),
    	    rs.getString("Surname"),
    	    rs.getString("Email"),
    	    rs.getString("Password"),
    	    rs.getString("Tax_code"),
    	    rs.getInt("Phone_number"),
    	    rs.getString("User_type"),
    	    rs.getInt("Id_number"),
    	    rs.getInt("Auth_number")
    	    );

    return user;
  }
}
  
}
