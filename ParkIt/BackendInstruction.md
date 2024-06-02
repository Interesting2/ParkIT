# ParkIt Backend

## Run the backend application

Please ensure that there is a maven environment on your computer. 

``` shell
mvn clean package
mvn exec:java -Dexec.mainClass="com.G23.ParkIt.ParkItApplication"
```

## APIs usage

The root url of the backend: **http://localhost:8080/parkit**

### Register

#### STEP1: Submit information

request ``` root/user/register ``` with following parameters as json data: 

``` java
private Integer userId;
// "password"
private String password;
// "user_name"
private String userName;
// "contact_phone_number"
private Integer contactPhoneNumber;
// "contact_email"
private String contactEmail;
// "bank_card_num"
private String bankCardNum;
// "balance"
private Integer balance;
// "verify_code"
private String verifyCode;
```

Specifically, ``` userName, password, contactEmail``` are required parameters to create an account. 
Specially, the json value of password can be an encoded or encrypted string. 

Example for basic registration: 

``` json
{
    "userName": "TestUser1", 
    "password": "testPassword", 
    "contactEmail": "yourEmail@gmail.com"
}
```

After finishing the step1, an account is added to the database and the verification code is sent to the contact email. But the balance of the account is set to -1, referring the account has not been activated. 

In development versions, the API returns a string with the verification code in it so that it is not necessary to wait for the email. But the email will always be sent to the recepient. 

Note that if there is the same user name with existing user, the backend will respond duplicate user name. 

#### STEP2: Complete registration with verification code

request ``` root/user/registerVerify ``` with following parameters as json data: 

``` java
private String userName;
private String code;
```

Both of the parameters are required. 

Example: 

``` json
{
    "userName": "TestUser1", 
    "code": "165325"
}
```

### Forgot password

#### STEP1: Submit the username

request ``` root/user/forgotPassword ``` with following parameters as json data: 

``` java
private String userName;
private String code;
```
Specifically, the parameter ```code``` does not matter and can be empty because this process just need the username of the account. 

Example: 

``` json
{
    "userName": "TestUser1"
}
```

#### STEP2: Complete resetting password

request ``` root/user/resetPassword ``` with following parameters as json data: 

``` java
// User name
private String userName; 

// Former password
private String formerPass; 
// New password
private String newPass; 

// Email verification code
private String code;

```

If the parameter ```code``` is contained in the json data and it is correct, the new password will be set directly without comparing the former password with the saved one. 

Both of the examples will set new password directly: 

``` json
{
    "userName": "TestUser1", 
    "formerPass": "123456", 
    "newPass": "QWErty", 
    "code": "962513"
}
```

``` json
{
    "userName": "TestUser1", 
    "newPass": "QWErty", 
    "code": "962513"
}
```

### Reset Password

request ``` root/user/resetPassword ``` with following parameters as json data: 

``` java
// User name
private String userName; 

// Former password
private String formerPass; 
// New password
private String newPass; 

// Email verification code
private String code;

```

If the parameter ```code``` is not contained, the former password is required to be compared with the saved one. 

Example of resetting the password with the former password: 

``` json
{
    "userName": "TestUser1", 
    "formerPass": "123456", 
    "newPass": "QWErty"
}
```

### Get and set listing and booking from and to the database

Listing and booking have the same usage that all of their methods are familiar. 

#### Listing

``` java
Listing getListingsById(Integer listingId);
List<Listing> getAllListings();
void insertListing(Listing listing);
void updateListing(Listing listing);
void deleteListing(Integer listingId);
```

which can be used on the following apis: 

``` bash
root/getBooking/{bookingId}
root/getAllBookings
root/insertBooking
root/updateBooking
root/deleteBooking/{bookingId}
```

##### Get a listing

GET ``` root/getListing/{listingId} ``` with replacing the ```{listingId}``` with the listing id. 

The api returns a json string contains the following key-value pairs: 

``` json
{
    "listingId": 1,
    "hourlyRate": 300,
    "status": "Available",
    "spotId": 1,
    "lesseeId": null
}
```

##### Get all listings

GET ``` root/getAllListings ``` with no attached data. 

The api returns a json string contains the following key-value pairs: 

``` json
{
    "data": [
        {
            "listingId": 1,
            "hourlyRate": 300,
            "status": "Available",
            "spotId": 1,
            "lesseeId": null
        },
        {
            "listingId": 2,
            "hourlyRate": 100,
            "status": "Available",
            "spotId": 2,
            "lesseeId": null
        }
    ]
}
```

##### Insert a listing

POST ``` root/insertListing ``` with a json data contains the following information: 

``` java
private Integer listingId; 
private Integer hourlyRate; 
private String status; 
private Integer spotId; 
private Integer lesseeId; 
```

Json example: 
``` json
{
    "listingId": 1, 
    "hourlyRate": 10, 
    "status": "Available", 
    "spotId": 1, 
    "lesseeId": 1
}
```

The api does not return any response. 

##### Update a listing

PUT ``` root/updateListing ``` with a json data contains the following information: 

``` java
private Integer hourlyRate; 
private String status; 
private Integer spotId; 
private Integer lesseeId; 
```

Json example: 
``` json
{
    "hourlyRate": 10, 
    "status": "Available", 
    "spotId": 1, 
    "lesseeId": 1
}
```

The api does not return any response. 

##### Delete a listing

DELETE ``` root/deleteListing/{listingId} ``` with replacing the ```{listingId}``` with the listing id. 

The api returns the result of deleting, a string "Listing deleted. Good!"

#### Booking

Methods in backend: 
``` java
Booking getBookingsById(Integer bookingId);
List<Booking> getAllBookings();
void insertBooking(Booking booking);
void updateBooking(Booking booking);
void deleteBooking(Integer bookingId);
```

##### Get a booking

GET ``` root/getBooking/{bookingId} ``` with replacing the ```{bookingId}``` with the booking id. 

The api returns a json string contains the following key-value pairs: 

``` json
{
    "bookingId": 1,
    "timestamp": null,
    "transactionPrice": 200,
    "startTime": null,
    "endTime": null,
    "status": "Confirmed",
    "listingId": 1,
    "lessorId": null
}
```

##### Get all bookings

GET ``` root/getAllBookings ``` with no attached data. 

The api returns a json string contains the following key-value pairs: 

``` json
[
    {
        "bookingId": 1,
        "timestamp": null,
        "transactionPrice": 200,
        "startTime": null,
        "endTime": null,
        "status": "Confirmed",
        "listingId": 1,
        "lessorId": null
    },
    {
        "bookingId": 2,
        "timestamp": null,
        "transactionPrice": 168,
        "startTime": null,
        "endTime": null,
        "status": "Confirmed",
        "listingId": 2,
        "lessorId": null
    }
]
```

##### Insert a booking

POST ``` root/insertBooking ``` with a json data contains the following information: 

``` java
private Integer bookingId; 
private Timestamp timestamp; 
private Integer transactionPrice; 
private Timestamp startTime; 
private Timestamp endTime; 
private String status; 
private Integer listingId; 
private Integer lessorId; 
```

Json example: 
``` json
{
    "bookingId": 1,
    "timestamp": null,
    "transactionPrice": 200,
    "startTime": null,
    "endTime": null,
    "status": "Confirmed",
    "listingId": 1,
    "lessorId": null
}
```

The api does not return any response. 

##### Update a booking

PUT ``` root/updateBooking ``` with a json data contains the following information: 

``` java
private Timestamp timestamp; 
private Integer transactionPrice; 
private Timestamp startTime; 
private Timestamp endTime; 
private String status; 
private Integer listingId; 
private Integer lessorId; 
```

Json example: 
``` json
{
    "timestamp": null,
    "transactionPrice": 200,
    "startTime": null,
    "endTime": null,
    "status": "Confirmed",
    "listingId": 1,
    "lessorId": null
}
```

The api does not return any response. 

##### Delete a booking

DELETE ``` root/deleteBooking/{bookingId} ``` with replacing the ```{bookingId}``` with the booking id. 

The api returns the result of deleting, a string "Booking deleted. Good!" 