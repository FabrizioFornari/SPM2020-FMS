package Unicam.SPM2020_FMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import Unicam.SPM2020_FMS.dao.StatisticsDao;
import Unicam.SPM2020_FMS.model.Statistic;

public class StatisticsService {

	@Autowired
	public StatisticsDao statisticsDao;
	
	public Float totalRevenue(Boolean lastMonth) {
		return statisticsDao.totalRevenue(lastMonth);
	}
	 
	public List<Statistic> revenueByParkSpace(Boolean lastMonth) {
		List<Statistic> result = statisticsDao.revenueByParkSpace(lastMonth);
		Float totIncome = statisticsDao.totalRevenue(lastMonth);
		for (Statistic statistic : result) {
			Float perc= statistic.getQuantity()/totIncome*100;
			perc = (float) (Math.round(perc*100)/100.0);
			statistic.setPercentage(perc);
		}
		return result;
	}

	public int totalDrivers() {
		return statisticsDao.totalDrivers();
	}

	public List<Statistic> usersByPayment() {
		List<Statistic> result = statisticsDao.usersByPayment();
		Integer totDrivers = statisticsDao.totalDrivers();
		for (Statistic statistic : result) {
			Float perc= statistic.getQuantity()/totDrivers*100;
			perc = (float) (Math.round(perc*100)/100.0);
			statistic.setPercentage(perc);			
		}
		return result;
	}
	
}
