package Unicam.SPM2020_FMS.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import Unicam.SPM2020_FMS.dao.PaymentDao;

import Unicam.SPM2020_FMS.model.Payment;

public class PaymentService {

	@Autowired PaymentDao paymentDao;
	
	
	public List<Payment> showPaymentsList() {
		return paymentDao.showPymentList();	
	  }
}
