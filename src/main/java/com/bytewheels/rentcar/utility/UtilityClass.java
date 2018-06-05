package com.bytewheels.rentcar.utility;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.bytewheels.rentcar.entity.CarDetails;
import com.bytewheels.rentcar.exception.DetailsNotFoundException;
import com.bytewheels.rentcar.repository.CarRepository;

@Component
public class UtilityClass {
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
	
	@Autowired
	private CarRepository carRepository;
	
	@Autowired
	private DateValidatorAndFormatter dateFormatterBean;

	public Long getCarId(Iterable<CarDetails> cars, String carName) {
		for(CarDetails car : cars) {
			if(car.getName().equalsIgnoreCase(carName)) {
				return car.getCarId();
			}
		}
		return null;
	}
	
	public boolean carIsAvailableForRenting(List<CarDetails> cars, String carName) {
		for(CarDetails car : cars) {
			if(car.getName().equalsIgnoreCase(carName)) {
				return true;
			}
		}
		return false;
	}
	
	/*
	 * Utility method to convert String into Date object
	 */
	public Date dateformatter(String stringDate) {
		   
		return this.dateFormatterBean.parseTheStringToDate(stringDate, DATE_FORMAT);
	}
	
	/*
	 * Utility method to calculate cost based on number of days its been rented
	 */
	public String calculateCost(Long carId, int numberOfDays) {
		Optional<CarDetails> cars = carRepository.findById(carId);
		if (!cars.isPresent())
		      throw new DetailsNotFoundException("Car not present in DataBase-" + carId);

		CarDetails car = cars.get();
		int cost = Integer.parseInt(car.getRentPerDay()) * numberOfDays;
		log.info("cost of rent is " + cost);
		return String.valueOf(cost);
	}
	
	/*
	 * Utility method to calculate number of days in between given dates
	 */
	public Integer findNoOfDaysInBetweenDates(Date dateOne, Date dateTwo){

		Integer returnValue = null;
		returnValue = Days.daysBetween(new DateTime(dateOne), new DateTime(dateTwo)).getDays();
		return returnValue;

	}
	
	public List<CarDetails> filterCarsBasedOnCategory(Iterable<CarDetails> cars, String category) {
		List<CarDetails> suitableCars = new ArrayList<CarDetails>();
		for(CarDetails car : cars) {
			if(car.getType().equalsIgnoreCase(category)) {
				suitableCars.add(car);
			}
		}
		return suitableCars;
	}
	
	public HashMap<Long, Integer> createAMapOfCount(Iterable<Long> rentedCarsIds) {
		HashMap<Long, Integer> map = new HashMap<Long, Integer>();
		for(Long carId : rentedCarsIds) {
			if(map.containsKey(carId)) {
				map.put(carId, map.get(carId) + 1);
			}else {
				map.put(carId, 1);
			}
		}
		return map;
	}
	
}
