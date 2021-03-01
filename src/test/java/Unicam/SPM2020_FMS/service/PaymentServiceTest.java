package Unicam.SPM2020_FMS.service;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import Unicam.SPM2020_FMS.model.Payment;

@SpringJUnitConfig(locations = "classpath:/user-beans.xml")
@TestMethodOrder(OrderAnnotation.class)
public class PaymentServiceTest {

  @Autowired
  private PaymentService paymentService;
  
  @Autowired
  JdbcTemplate jdbcTemplate;
  
  @Test
  public void testShowPaymentList() {
	List<Payment> result = paymentService.showPaymentsList();
	int tableRows=JdbcTestUtils.countRowsInTable(jdbcTemplate, "payment");
    Assert.assertEquals(tableRows,result.size());
  }

}
