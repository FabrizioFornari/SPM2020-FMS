package Unicam.SPM2020_FMS.service;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import Unicam.SPM2020_FMS.controller.PolicemanWebSocketController;

import Unicam.SPM2020_FMS.model.SpotIllegallyOccupied;

public class SchedulerService implements ApplicationContextAware {

	@Autowired
	public TaskScheduler myScheduler;

	@Autowired
	public ParkSpotService spotService;

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SchedulerService.context = applicationContext;
	}

	public static <T> T getBean(Class<T> beanClass) {
		return context.getBean(beanClass);
	}

	public void schedulePoliceChecking() {
		myScheduler.scheduleAtFixedRate(new checkNonConformities(), 30 * 1000);
	}

	public void scheduleReservationCheck(String triggerString, Integer reservation) {
		myScheduler.schedule(new checkReservedSpot(reservation), new CronTrigger(triggerString));
	}

	class checkNonConformities implements Runnable {

		@Override
		public void run() {
			List<SpotIllegallyOccupied> illegallyOccupiedList = spotService.getIllegallyOccupied();
			//String message = "";
			String illegallyOccupiedString = "";

			for (SpotIllegallyOccupied spotIllegallyOccupied : illegallyOccupiedList) {
				illegallyOccupiedString = illegallyOccupiedString.concat("Parking space: "+spotIllegallyOccupied.getParkingSpaceName()
						+ " - Address: "+spotIllegallyOccupied.getParkingSpaceAddress() + " - Spot: " + spotIllegallyOccupied.getParkingSpot()+";");
			}

			 PolicemanWebSocketController.sendAll(illegallyOccupiedString);
			System.out.println("Scheduled check " + (System.currentTimeMillis() / 1000) + ": " + illegallyOccupiedString);
		}

	}

	class checkReservedSpot implements Runnable {

		public checkReservedSpot(Integer reservation) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}

	}

}
