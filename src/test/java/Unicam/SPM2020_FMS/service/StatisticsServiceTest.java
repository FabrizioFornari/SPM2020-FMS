package Unicam.SPM2020_FMS.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.test.jdbc.JdbcTestUtils;

import Unicam.SPM2020_FMS.model.Statistic;

@SpringJUnitConfig(locations = "classpath:/user-beans.xml")
public class StatisticsServiceTest {
  
  @Autowired
  private StatisticsService statService;
  
  @Autowired
  JdbcTemplate jdbcTemplate;

  @BeforeEach
  public void createTestCase() {
  }
  
  @AfterEach
  public void deleteTestCase() {
  }
  
  @Test
  public void testRevenueByParkSpaceOverallPerc() {
	List<Statistic> revenueByParks=statService.revenueByParkSpace(false);
	Float computedPerc=(float)0;
	for (Statistic parkRevenue : revenueByParks) {
		computedPerc+=parkRevenue.getPercentage();
	}
	Assert.assertEquals(100, computedPerc, 0.01);
  }
  
  @Test
  public void testRevenueByParkSpaceOverallQty() {
	List<Statistic> revenueByParks=statService.revenueByParkSpace(false);
	Float computedRevenue=(float)0;
	for (Statistic parkRevenue : revenueByParks) {
		computedRevenue+=parkRevenue.getQuantity();
	}
	Float totalRevenue=statService.totalRevenue(false);
	Assert.assertEquals(totalRevenue, computedRevenue, 0.01);
  }
  
  @Test
  public void testRevenueByParkSpaceMonthPerc() {
	List<Statistic> revenueByParks=statService.revenueByParkSpace(true);
	Float computedPerc=(float)0;
	for (Statistic parkRevenue : revenueByParks) {
		computedPerc+=parkRevenue.getPercentage();
	}
	Assert.assertEquals(100, computedPerc, 0.02);
  }
  
  @Test
  public void testRevenueByParkSpaceMonthQty() {
	List<Statistic> revenueByParks=statService.revenueByParkSpace(true);
	Float computedRevenue=(float)0;
	for (Statistic parkRevenue : revenueByParks) {
		computedRevenue+=parkRevenue.getQuantity();
	}
	Float totalRevenue=statService.totalRevenue(true);
	Assert.assertEquals(totalRevenue, computedRevenue, 0.01);
  }
  
  @Test
  public void testTotalRevenue() {
	Float revenueLastMonth=statService.totalRevenue(true);
	Float revenueOverall=statService.totalRevenue(false);
	Assert.assertTrue(revenueOverall>=revenueLastMonth);
  }
  
  @Test
  public void testTotalDrivers() {
	  int tableRows=JdbcTestUtils.countRowsInTableWhere(jdbcTemplate,"user","User_type = 'Driver'");
	  int result=statService.totalDrivers();
	  Assert.assertEquals(tableRows, result);
  }
  
  @Test
  public void testUsersByPaymentPerc() {
	List<Statistic> usersByPayment=statService.usersByPayment();
	float computedPerc=(float)0;
	for (Statistic paymentDrivers : usersByPayment) {
		computedPerc+=paymentDrivers.getPercentage();
	}
	Assert.assertEquals(100, computedPerc, 0.02);
  }
  
  @Test
  public void testUsersByPaymentQty() {
	List<Statistic> usersByPayment=statService.usersByPayment();
	int computedDrivers=0;
	for (Statistic paymentDrivers : usersByPayment) {
		computedDrivers+=paymentDrivers.getQuantity();
	}
	int totalDrivers=statService.totalDrivers();
	Assert.assertEquals(totalDrivers, computedDrivers);
  }

}
