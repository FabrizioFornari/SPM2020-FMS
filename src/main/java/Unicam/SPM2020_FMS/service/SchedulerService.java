package Unicam.SPM2020_FMS.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import Unicam.SPM2020_FMS.controller.ParkingWebSocketController;
import Unicam.SPM2020_FMS.controller.PolicemanWebSocketController;
import Unicam.SPM2020_FMS.model.Reservation;
import Unicam.SPM2020_FMS.model.SpotIllegallyOccupied;

public class SchedulerService implements ApplicationContextAware {

	@Autowired
	public TaskScheduler myScheduler;

	@Autowired
	public ParkSpotService spotService;
	
	@Autowired
	public ReservationService reservationService;

	private static ApplicationContext context;

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SchedulerService.context = applicationContext;
	}

	public static <T> T getBean(Class<T> beanClass) {
		return context.getBean(beanClass);
	}

	public void schedulePoliceChecking() {
		myScheduler.scheduleAtFixedRate(new CheckNonConformities(), 30 * 1000);
	}

	public void scheduleReservationCheck(String triggerString, Integer reservation) {
		myScheduler.schedule(new CheckReservedSpot(reservation), new CronTrigger(triggerString));
	}
	
	public void scheduleReservationExpiring(Reservation reservation) {
		myScheduler.schedule(new ReservationExpiring(reservation), Instant.now().plus(1,ChronoUnit.MINUTES));
	}

	class CheckNonConformities implements Runnable {

		@Override
		public void run() {
			List<SpotIllegallyOccupied> illegallyOccupiedList = spotService.getIllegallyOccupied();
			String illegallyOccupiedString = "";

			for (SpotIllegallyOccupied spotIllegallyOccupied : illegallyOccupiedList) {
				illegallyOccupiedString = illegallyOccupiedString.concat("Parking space: "+spotIllegallyOccupied.getParkingSpaceName()
						+ " - Address: "+spotIllegallyOccupied.getParkingSpaceAddress() + " - Spot: " + spotIllegallyOccupied.getParkingSpot()+";");
			}
			PolicemanWebSocketController.sendAll(illegallyOccupiedString);
			System.out.println("Scheduled check " + (System.currentTimeMillis() / 1000) + ": " + illegallyOccupiedString);
		}

	}
	
	class ReservationExpiring implements Runnable {
		
		private Reservation reservation;

		public ReservationExpiring(Reservation reservation) {
			this.reservation=reservation;
		}

		@Override
		public void run() {
			int spot=reservation.getParkingSpot();
			int space=reservation.getParkingSpaceId();
			if(!spotService.isBusy(spot, space)) {
				reservationService.deleteReservation(this.reservation.getId());
				ParkingWebSocketController.sendExpiredMessage(spot,space);
			}
		}

	}

	class CheckReservedSpot implements Runnable {

		public CheckReservedSpot(Integer reservation) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public void run() {
			// TODO Auto-generated method stub

		}

	}

}
