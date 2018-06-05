Rest APIs for A Car Rental Website

This project is using spring boot and H2 database and Java 1.8.

It has 4 rest APIs as below -

1.  API to fetch cars details which are available for given date range

Method - GET

Sample URL - http://localhost:8080/bytewheels/fetchAvailableCars?startDate=2018-05-24%2015:05:12&endDate=2018-05-27%2015:05:12

Comments - URL has two request parameters startDate and endDate
Dates should be in "yyyy-MM-dd%20HH:mm:ss" format.

Sample Output -

[{
  "carType": "Compact",
  "carName": "Ford Fiesta",
  "rentPerDay": "20"
}, {
  "carType": "Full",
  "carName": "Chevrolet Malibu",
  "rentPerDay": "30"
},  {
  "carType": "Large",
  "carName": "Ford Egde",
  "rentPerDay": "40"
}, {
  "carType": "Luxury",
  "carName": "BMW 328i",
  "rentPerDay": "50"
}, {
  "carType": "Luxury",
  "carName": "BMW X5",
  "rentPerDay": "50"
}]



2. API to fetch the specific category cars details which are available for given date range.

Method - GET

Sample URL -  http://localhost:8080/bytewheels/fetchAvailableCarsByCategaory?startDate=2018-05-24%2015:05:12&endDate=2018-05-27%2015:05:12&carType=Luxury

Comments - URL has three request parameters startDate, endDate and carType
Dates should be in "yyyy-MM-dd%20HH:mm:ss" format and Valid Car Types are - Compact, Full, Large, Luxury.

Sample Output - 

[{
  "carType": "Luxury",
  "carName": "BMW 328i",
  "rentPerDay": "50"
}, {
  "carType": "Luxury",
  "carName": "BMW X5",
  "rentPerDay": "50"
}]


3.  API to fetch booking details of a user based on email id provided.

Method - GET

Sample URL - http://localhost:8080/bytewheels/fetchBookingDetails?email=abc@gmail.com 

Comments - URL has One request parameters email

Sample Output - 

[{
  "bookingId": "1",
  "userName": "ABC",
  "emailId": "abc@gmail.com",
  "carName": "Ford Focus",
  "startDate": "2018-05-26 15:05:12.0",
  "endDate": "2018-05-24 15:05:12.0",
  "totalCost": "$40"
}, {
  "bookingId": "4",
  "userName": "JKL",
  "emailId": "abc@gmail.com",
  "carName": "Ford Escape",
  "startDate": "2018-05-26 15:05:12.0",
  "endDate": "2018-05-24 15:05:12.0",
  "totalCost": "$80"
}]
 

4. API to book the selected car.

Method - POST 

URL - http://localhost:8080/bytewheels/bookSelectedCar 

Sample Request Body - { "startDate":"2018-05-24 15:05:12", "endDate":"2018-05-27 15:05:12", "userName":"JYOTI", "rentedCarName":"BMW 328i", "emailId":"sample@gmail.com" }

Comments - returns the booking details with unique booking id.

Sample Output - 
{
  "bookingId": "5",
  "userName": "JYOTI",
  "emailId": "sample@gmail.com",
  "carName": "BMW 328i",
  "startDate": "Sun May 27 15:05:12 IST 2018",
  "endDate": "Thu May 24 15:05:12 IST 2018",
  "totalCost": "$150"
}