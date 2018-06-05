package com.bytewheels.rentcar.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bytewheels.rentcar.entity.CarDetails;
import com.bytewheels.rentcar.entity.UserDetails;
import com.bytewheels.rentcar.exception.DetailsNotFoundException;
import com.bytewheels.rentcar.exception.IncorrectRequestException;
import com.bytewheels.rentcar.repository.CarRepository;
import com.bytewheels.rentcar.repository.UserRepository;
import com.bytewheels.rentcar.utility.Mappers;
import com.bytewheels.rentcar.utility.UtilityClass;
import com.bytewheels.rentcar.vo.BookingResponseVO;
import com.bytewheels.rentcar.vo.RequestVO;
import com.bytewheels.rentcar.vo.ResponseVO;

@Service
public class RestManager {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private UtilityClass utility;
	
	@Autowired
	private Mappers mapper;
	
	
	public List<BookingResponseVO> fetchUserDetails(String emailId) {
		Iterable<UserDetails> it =  userRepository.findUserData(emailId);
		Iterator<UserDetails> i = it.iterator();
		if(!i.hasNext()) {
			throw new DetailsNotFoundException("No booking found with given email Id - " + emailId);
		}
		log.info("returning response of getUserBookingDetails method");
		List<BookingResponseVO> list = getBookingDetails(i); 
		return list;
	}



	public List<ResponseVO> fetchAvailableCars(String startDate, String endDate, String carType) {
		List<CarDetails> availableCars = getAvailableCarsForSelectedCategory(startDate, endDate, carType);
		List<ResponseVO> response = this.mapper.mapCarDetailsToResponse(availableCars);
		log.info("returning response of getAvailableCarsBasedOnCategory method");
		return response;
	}
	
	
	public List<ResponseVO> fetchAvailableCars(String startDate, String endDate) {
		List<CarDetails> availableCars = getAvailableCarsToDisplay(startDate, endDate);
		List<ResponseVO> response = this.mapper.mapCarDetailsToResponse(availableCars);
		log.info("returning response of getAvailableCars method");
		return response;
	}
	
	public BookingResponseVO bookselectedCar(RequestVO request) {
		
		if(!validRequest(request)) {
			throw new IncorrectRequestException("startDate, endDate, carType, userName, rentedCarName, emailId are mandatory fields.");
		}
		
		String carName = request.getRentedCarName();
	    Date startDate = this.utility.dateformatter(request.getStartDate());
	    Date endDate = this.utility.dateformatter(request.getEndDate());;
    	
		Iterable<CarDetails> cars = carRepository.findAll();
		
		List<CarDetails> availableCars = repondWithAvailableCars(startDate, endDate, cars);
		
		if(!this.utility.carIsAvailableForRenting(availableCars, carName)) {
			throw new DetailsNotFoundException("Car "  + carName +  " is not available for booking !!!");
		}
		
		Long carId = this.utility.getCarId(cars, carName);
		
		int numberOfDays = this.utility.findNoOfDaysInBetweenDates(startDate, endDate);
		log.info("car rented for " + numberOfDays + " days");
		String totalCost = this.utility.calculateCost(carId, numberOfDays);
		
		UserDetails u = new UserDetails();
		u.setEmailId(request.getEmailId());
	    u.setName(request.getUserName());
	    u.setStartDate(startDate);
		u.setEndDate(endDate);
		u.setCarId(carId);
		u.setTotalCost(totalCost);
	    u = userRepository.save(u);
	    
	    BookingResponseVO response = this.mapper.mapUserDetailsToResponse(u, carName);
	    log.info("returning response of bookSelectedCar method");
		return response;
	}

	private boolean validRequest(RequestVO request) {
		if(StringUtils.isEmpty(request.getEmailId()) || StringUtils.isEmpty(request.getEndDate())
				|| StringUtils.isEmpty(request.getRentedCarName()) || StringUtils.isEmpty(request.getStartDate()) || StringUtils.isEmpty(request.getUserName())) {
			return false;
		}
		return true;
	}



	private List<BookingResponseVO> getBookingDetails(Iterator<UserDetails> i) {
		List<BookingResponseVO> list = new ArrayList<BookingResponseVO>();
		do{
			UserDetails u = i.next();
			Optional<CarDetails> car = carRepository.findById(u.getCarId());
			BookingResponseVO response  = this.mapper.mapUserDetailsToResponse(u, car.get().getName());
			list.add(response);
		}while(i.hasNext());
		return list;
	}
	
	
	/*
	 *  method to fetch available cars for the given date range.
	 */
	private List<CarDetails> getAvailableCarsToDisplay(String startDateString, String endDateString) {
		
		Date startDate = this.utility.dateformatter(startDateString);
		Date endDate = this.utility.dateformatter(endDateString);
		
		Iterable<CarDetails> cars = carRepository.findAll();
		
		return repondWithAvailableCars(startDate, endDate, cars);
		
	}
	
	private List<CarDetails> repondWithAvailableCars(Date startDate, Date endDate, Iterable<CarDetails> carsInDB) {
		
		Iterable<UserDetails> userDetails = userRepository.findAll();
		
		List<Long> rentedCarsIds = getRentedCarIds(userDetails, startDate, endDate);

		HashMap<Long, Integer> mapOfRentedCarAndCount = this.utility.createAMapOfCount(rentedCarsIds);
		
		return getListOfAvailableCars(rentedCarsIds, mapOfRentedCarAndCount, carsInDB);
	}

	private List<CarDetails> getAvailableCarsForSelectedCategory(String startDateString, String endDateString, String carType) {
			
		Date startDate = this.utility.dateformatter(startDateString);
		Date endDate = this.utility.dateformatter(endDateString);
			
		Iterable<CarDetails> cars = carRepository.findAll();
		
		List<CarDetails> suitableCars = this.utility.filterCarsBasedOnCategory(cars, carType);
				
		return repondWithAvailableCars(startDate, endDate, suitableCars);
	}

	private List<CarDetails> getListOfAvailableCars(List<Long> rentedCarsIds, HashMap<Long, Integer> mapOfRentedCarAndCount, Iterable<CarDetails> cars) {
		List<CarDetails> availableCars = new ArrayList<CarDetails>();
		for(CarDetails car : cars) {
			if(rentedCarsIds.contains(car.getCarId())) {
				if(car.getTotalQuantity() !=  mapOfRentedCarAndCount.get(car.getCarId())) {
					availableCars.add(car);
				}
			}else {
				availableCars.add(car);
			}
		}
		return availableCars;
	}

	private List<Long> getRentedCarIds(Iterable<UserDetails> userDetails, Date startDate, Date endDate){
		List<Long> rentedCarsIds = new ArrayList<Long>();
		for(UserDetails user : userDetails) {
			if((user.getStartDate().after(startDate) && user.getStartDate().before(endDate)
					|| user.getEndDate().after(startDate) && user.getEndDate().before(endDate))) {
				rentedCarsIds.add(user.getCarId());
			}
		}
		return rentedCarsIds;
	}
	
	
	
}
