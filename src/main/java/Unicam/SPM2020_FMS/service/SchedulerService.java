package Unicam.SPM2020_FMS.service;

import java.util.List;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

import Unicam.SPM2020_FMS.controller.PolicemanWebSocketController;
import Unicam.SPM2020_FMS.model.ParkingSpot;

public class SchedulerService implements ApplicationContextAware {
	
	@Autowired
	public TaskScheduler myScheduler;
	
	@Autowired
	public ParkSpotService spotService;
	
	private static ApplicationContext context;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SchedulerService.context=applicationContext;		
	}
	
    public static <T> T getBean(Class<T> beanClass)
    {
        return context.getBean(beanClass);
    }
	
	public void schedulePoliceChecking() {
		myScheduler.scheduleAtFixedRate(new checkNonConformities(), 15*60*1000 );
	}
	
	public void scheduleReservationCheck(String triggerString, Integer reservation ) {
		myScheduler.schedule(new checkReservedSpot(reservation), new CronTrigger(triggerString));
	}
	
	class checkNonConformities implements Runnable {

		@Override
		public void run() {
			List<ParkingSpot> wronglyOccupied = spotService.getWronglyOccupied();
			String message="";
			for (ParkingSpot spot : wronglyOccupied) {
				message=message.concat(spot.getParkingSpace()+":"+spot.getSpotNumber()+";");
			}
			PolicemanWebSocketController.sendAll(message);
			System.out.println("Police checking");
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


