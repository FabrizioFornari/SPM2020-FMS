package Unicam.SPM2020_FMS.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import Unicam.SPM2020_FMS.model.Payment;

public class PaymentDao {

	@Autowired
	DataSource datasource;

	@Autowired
	JdbcTemplate jdbcTemplate;
	
	public List<Payment> showPymentList() {		
		String sql = "SELECT * FROM payment";

		List<Payment> paymentsList = jdbcTemplate.query(sql, new PaymentMapper());

		return paymentsList;	
	}
	
	class PaymentMapper implements RowMapper<Payment> {
		public Payment mapRow(ResultSet rs, int arg1) throws SQLException {
			Payment payment = new Payment(
				rs.getInt("ID"),
				rs.getString("Payment_type")
			);
			return payment;
		}
	 }
	
}
