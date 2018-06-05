package com.bytewheels.rentcar.vo;

public class RequestVO {
	
	private String startDate;
	private String endDate;
	private String carType;
	private String userName;
	private String rentedCarName;
	private String emailId;
	
	/**
	 * @return the startDate
	 */
	public String getStartDate() {
		return startDate;
	}
	
	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	
	/**
	 * @return the endDate
	 */
	public String getEndDate() {
		return endDate;
	}
	
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return the rentedCarName
	 */
	public String getRentedCarName() {
		return rentedCarName;
	}

	/**
	 * @param rentedCarName the rentedCarName to set
	 */
	public void setRentedCarName(String rentedCarName) {
		this.rentedCarName = rentedCarName;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId() {
		return emailId;
	}
	
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	
	
	
}
