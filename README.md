# **Retailer Rewards Program**

This project is a Spring Boot application that implements a retailer rewards program, where customers earn points based on their purchase transactions. The application provides a RESTful API that allows clients to create transactions and retrieve reward information for customers.

## **Getting Started**

To run the application, you will need Java 17 and Maven installed on your system. You can build and run the application using the following command:

```

mvn spring-boot:run
```
The application will start up on port 8181 by default. You can access the API documentation by opening the following URL in your web browser:

bash


```api
http://localhost:8181
```
Usage

Create a Customer

To create a Customer, send a POST request to the following endpoint:

bash
```api

POST /customer
```
The request body should contain a JSON object with the following properties:

```json

{

"customerName": "John",

"emailID": "John@gmail.com"

}
```
Create a Transaction

To create a transaction, send a POST request to the following endpoint:

bash

```api

POST /transactions
```
The request body should contain a JSON object with the following properties:

```json

{

"customerId": 123,

"amount": 120.0,

"date": "2023-02-17"

}
```

The customerId property is the ID of the customer who made the transaction. The amount property is the total amount of the transaction. The date property is the date of the transaction in ISO-8601 format.

The response body will contain the details of the created transaction, including the points earned:

```json

{

"id": 1,

"customerId": 123,

"amount": 120.0,

"date": "2023-02-17",

"pointsEarned": 90

}
````

### ***Get Rewards for a Customer***

To get the rewards for a customer, send a GET request to the following endpoint:

bash
```api

GET /transactions/customer/{customerId}/rewards
```
The {customerId} path variable should be replaced with the ID of the customer. The response body will contain the rewards earned by the customer in each of the past three months, as well as the total rewards:

```json

{

"rewardsByMonth": {

"1": 40,

"2": 120,

"3": 80

},

"totalRewards": 240

}
```

### **Data Set**

The application comes with a sample data set for testing purposes. The data set includes 10 customers and 50 transactions spread over the past three months. The data set is loaded automatically when the application starts up.

### **Contributing**

If you would like to contribute to the project, please fork the repository and create a pull request with your changes. All contributions are welcome!





