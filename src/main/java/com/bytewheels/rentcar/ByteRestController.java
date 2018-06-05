package com.bytewheels.rentcar;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bytewheels.rentcar.exception.IncorrectRequestException;
import com.bytewheels.rentcar.service.RestManager;
import com.bytewheels.rentcar.vo.BookingResponseVO;
import com.bytewheels.rentcar.vo.RequestVO;
import com.bytewheels.rentcar.vo.ResponseVO;
import com.google.gson.Gson;


@RestController
@RequestMapping(value = "/bytewheels")
public class ByteRestController {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestManager manager;
	
	@RequestMapping(value = "/fetchAvailableCars", method = RequestMethod.GET, produces = "application/json")
	public List<ResponseVO> getAvailableCars(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate) {
		
		log.info("inside getAvailableCars method with start date - " + startDate + " and end Ddate - " + endDate);
		if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate)) {
			throw new IncorrectRequestException("Start Date and End Date fields are mandatory!!!");
		}
		List<ResponseVO> responseList = this.manager.fetchAvailableCars(startDate, endDate);
		return responseList;
	}
	

	@RequestMapping(value = "/fetchAvailableCarsByCategaory", method = RequestMethod.GET, produces = "application/json")
	public List<ResponseVO> getAvailableCarsBasedOnCategory(@RequestParam("startDate") String startDate, @RequestParam("endDate") String endDate, @RequestParam("carType") String carType){
		
		log.info("inside getAvailableCarsBasedOnCategory method  with start date - " + startDate + " and end Ddate - " + endDate + " and car type is " + carType);
		if(StringUtils.isEmpty(startDate) || StringUtils.isEmpty(endDate) || StringUtils.isEmpty(carType)) {
			throw new IncorrectRequestException("Start Date, End Date and Car Type fields are mandatory!!!");
		}
		List<ResponseVO> responseList = this.manager.fetchAvailableCars(startDate, endDate, carType);
		return responseList;	
	}
	
	
	@RequestMapping(value = "/fetchBookingDetails", method = RequestMethod.GET, produces = "application/json")
	public List<BookingResponseVO> getUserBookingDetails(@RequestParam("email") String emailId) {

		log.info("inside getUserBookingDetails method with - " + emailId);
		if(StringUtils.isEmpty(emailId)) {
			throw new IncorrectRequestException("Email Id is mandatory!!!");
		}
		List<BookingResponseVO> list = this.manager.fetchUserDetails(emailId);
		return list;
	}
	
	
	@RequestMapping(value = "/bookSelectedCar", method = RequestMethod.POST, produces = "application/json")
	public BookingResponseVO bookSelectedCar(@RequestBody String bookingRequest) {
		
		log.info("inside bookSelectedCar method - " + bookingRequest);
		RequestVO request =  new Gson().fromJson(bookingRequest, RequestVO.class);
		BookingResponseVO response = this.manager.bookselectedCar(request);
		return response;	
	}
	
}

