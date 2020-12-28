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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

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
		int err=0;

		try {
			jdbcTemplate.update(connection -> {
				PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, user.getName());
				ps.setString(2, user.getSurname());
				ps.setString(3, user.getEmail());
				ps.setString(4, user.getPassword());
				ps.setString(5, user.getTaxCode());
				ps.setObject(6, user.getPhoneNumber());
				ps.setString(7, user.getUserType());
				ps.setObject(8, user.getIdNumber());
				ps.setObject(9, user.getAuthNumber());
				return ps;
			}, userKeyHolder);
		} catch (org.springframework.dao.DuplicateKeyException e) {
			String msg=e.getMessage();
			if (msg.contains("user.Email")) {
				err=-1;
			} else if (msg.contains("user.Tax_code")) {
				err=-2;
			} else if (msg.contains("user.Id_number")) {
				err=-3;
			} else if (msg.contains("user.Auth_number")) {
				err=-4;
			}
			return err;
		} catch (Exception e) {
			return err;
		}
		
		return userKeyHolder.getKey().intValue();
	}

	/** Check if the user has inserted the correct credentials */

	public User validateUser(Login login) {
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String sql = "SELECT * FROM user WHERE email='" + login.getUsername() + "'";

		/** Fetch the data retrieved from the database */
		List<User> users = jdbcTemplate.query(sql, new UserMapper());
		/**
		 * If the user has inserted the right email the system will check if the
		 * password is correct else if the user is not present or has inserted the wrong
		 * credentials it will give null
		 */
		if (users.size() > 0 && passwordEncoder.matches(login.getPassword(), users.get(0).getPassword())) {
			return users.get(0);
		} else {
			return null;
		}

	}

	@Override
	public int update(User user) {
		
		String sql = "UPDATE user SET Name = ?, Surname = ?, Email =? , Tax_code = ? ,Phone_number = ?,Id_number = ? ,Auth_number = ? WHERE ID = ? ";
		int updated;
		
		try {
		updated=jdbcTemplate.update(sql,new Object[] { user.getName(),user.getSurname(), user.getEmail(), user.getTaxCode(),
				user.getPhoneNumber(), user.getIdNumber(), user.getAuthNumber(), user.getIdUser() });
		} catch (Exception e) {
			return 0;
		}
		
		return updated;
	}

	class UserMapper implements RowMapper<User> {

		public User mapRow(ResultSet rs, int arg1) throws SQLException {
			User user = new User(rs.getInt("ID"), rs.getString("Name"), rs.getString("Surname"), rs.getString("Email"),
					rs.getString("Password"), rs.getString("Tax_code"), rs.getString("Phone_number"),
					rs.getString("User_type"), rs.getString("Id_number"), rs.getString("Auth_number"));

			return user;
		}
	}

}
