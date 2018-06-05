package com.bytewheels.rentcar.utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.bytewheels.rentcar.exception.IncorrectDateFormatException;

@Component
public class DateValidatorAndFormatter{

	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	public Date parseTheStringToDate(String dateToValidate, String dateFromat){
		
		Date date = null;
		
		if(dateToValidate == null){
			throw new IncorrectDateFormatException("Date is mandatory field");
		}
		
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		
		try {
			
			date = sdf.parse(dateToValidate);
			log.info("date is " + date);
		
		} catch (ParseException e) {
			log.error(e.getMessage(), e);
			throw new IncorrectDateFormatException("Please enter date in format - " + dateFromat);
		}
		
		return date;
	}
	
}