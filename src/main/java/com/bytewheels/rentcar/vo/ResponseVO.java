package com.bytewheels.rentcar.vo;

public class ResponseVO {
	
	
	private String carType;
	private String carName;
	private String rentPerDay;
	
	/**
	 * @return the carType
	 */
	public String getCarType() {
		return carType;
	}
	/**
	 * @param carType the carType to set
	 */
	public void setCarType(String carType) {
		this.carType = carType;
	}
	/**
	 * @return the carName
	 */
	public String getCarName() {
		return carName;
	}
	/**
	 * @param carName the carName to set
	 */
	public void setCarName(String carName) {
		this.carName = carName;
	}
	/**
	 * @return the rentPerDay
	 */
	public String getRentPerDay() {
		return rentPerDay;
	}
	/**
	 * @param rentPerDay the rentPerDay to set
	 */
	public void setRentPerDay(String rentPerDay) {
		this.rentPerDay = rentPerDay;
	}
	
}
