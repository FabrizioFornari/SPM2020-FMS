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

import Unicam.SPM2020_FMS.model.Login;
import Unicam.SPM2020_FMS.model.User;

public class UserDaoImpl implements UserDao {

  @Autowired
  DataSource datasource;

  @Autowired
  JdbcTemplate jdbcTemplate;
  
  public int register(User user) {
	  
    String sql = "INSERT INTO user(Name, Surname, Email, Password, Tax_code, Phone_number, User_type, Id_number, Auth_number) VALUES (?,?,?,?,?,?,?,?,?)";
    KeyHolder userKeyHolder = new GeneratedKeyHolder();
	
    //try {
	    jdbcTemplate.update( 
	    	connection -> {
		    	PreparedStatement ps = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		    	ps.setString(1,user.getName());
		    	ps.setString(2,user.getSurname());
		    	ps.setString(3,user.getEmail());
		    	ps.setString(4,user.getPassword());
		    	ps.setString(5,user.getTaxCode());
		    	ps.setObject(6,user.getPhoneNumber());
		    	ps.setString(7,user.getUserType());
		    	ps.setObject(8,user.getIdNumber());
		    	ps.setObject(9,user.getAuthNumber());
		        return ps;
	    	}, 
	    	userKeyHolder
	    );
    //} catch (SQLException e) {
		//TODO
		//trovare unique key violation e se possibile quale chiave
	//}
    
    return  userKeyHolder.getKey().intValue();
    
  }
  
  public User validateUser(Login login) {
    String sql = "SELECT * FROM user WHERE email='" + login.getUsername() + "' and password='" + login.getPassword() + "'";
    
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
