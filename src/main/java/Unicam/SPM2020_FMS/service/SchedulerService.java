package Unicam.SPM2020_FMS.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.TaskScheduler;

import Unicam.SPM2020_FMS.controller.DriverWebSocketController;
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
	
	public void scheduleReservationExpiring(Reservation reservation) {
		myScheduler.schedule(new ReservationExpiring(reservation), Instant.now().plus(1,ChronoUnit.MINUTES));
	}
	
	public void scheduleReservationCheck(Reservation reservation) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		try {
			Instant startTime = dateFormat.parse(reservation.getParkingStart()).toInstant();
			startTime=startTime.minus(5, ChronoUnit.MINUTES);
			myScheduler.schedule(new CheckReservedSpot(reservation), startTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	public void scheduleReservationClosing(Reservation reservation) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm");
		try {
			Instant startTime = dateFormat.parse(reservation.getParkingStart()).toInstant();
			startTime=startTime.plus(30, ChronoUnit.MINUTES);
			myScheduler.schedule(new CloseUnusedBooking(reservation), startTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
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
				reservationService.deleteReservation(reservation.getId());
				ParkingWebSocketController.sendExpiredMessage(spot,space);
			}
		}

	}

	class CheckReservedSpot implements Runnable {
		
		private Reservation reservation;

		public CheckReservedSpot(Reservation reservation) {
			this.reservation=reservation;
		}

		@Override
		public void run() {
			int spot=reservation.getParkingSpot();
			int space=reservation.getParkingSpaceId();
			if(spotService.isBusy(spot, space)) {
				int newSpot= spotService.getFreeSpot(reservation.getParkingSpaceId(), reservation.isAskedCovered(), reservation.isAskedHandicap());
				if(newSpot==0) {
					reservationService.deleteReservation(reservation.getId());
				} else {
					reservationService.changeSpot(reservation.getId(), newSpot);
				}
				DriverWebSocketController.sendBookChangedMessage(reservation.getDriver(),newSpot);
			}
		}

	}

	class CloseUnusedBooking implements Runnable {
		
		private Reservation reservation;

		public CloseUnusedBooking(Reservation reservation) {
			this.reservation=reservation;
		}

		@Override
		public void run() {
			int spot=reservation.getParkingSpot();
			int space=reservation.getParkingSpaceId();
			if(!spotService.isBusy(spot, space)) {
				reservationService.closeReservation(reservation.getId());
				DriverWebSocketController.sendBookClosedMessage(reservation.getDriver());
			}
		}

	}
}
