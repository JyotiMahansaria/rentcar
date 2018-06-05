package com.bytewheels.rentcar.utility;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bytewheels.rentcar.entity.CarDetails;
import com.bytewheels.rentcar.entity.UserDetails;
import com.bytewheels.rentcar.vo.BookingResponseVO;
import com.bytewheels.rentcar.vo.ResponseVO;

@Component
public class Mappers {

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public List<ResponseVO> mapCarDetailsToResponse(List<CarDetails> availableCars) {
		List<ResponseVO> response = new ArrayList<ResponseVO>();
		for(CarDetails car : availableCars) {
			ResponseVO responseVO = new ResponseVO();
			responseVO.setCarName(car.getName());
			responseVO.setCarType(car.getType());
			responseVO.setRentPerDay(car.getRentPerDay());
			response.add(responseVO);
		}
		log.info("successfully mapped car details in response");
		return response;
	}
	
	public BookingResponseVO mapUserDetailsToResponse(UserDetails user, String carName) {
		
		BookingResponseVO responseVO = new BookingResponseVO();
		responseVO.setBookingId(user.getUserId().toString());
		responseVO.setCarName(carName);
		responseVO.setUserName(user.getName());
		responseVO.setEmailId(user.getEmailId());
		responseVO.setStartDate(user.getEndDate().toString());
		responseVO.setEndDate(user.getStartDate().toString());
		responseVO.setTotalCost("$"+user.getTotalCost());
		
		log.info("successfully mapped user details in response");
		return responseVO;
	}
	
}
