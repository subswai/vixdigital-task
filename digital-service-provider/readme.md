# VIX DIGITAL Programming Test
# Subhasis Swain

## Requirement

#### Start by polling vix.digital and storing any information that will help us understand how well this services are performing.
    ● Implement a HTTP poller to check the status of a service at regular intervals
    ● Persist the information to an database
    ● Log a warning to console when the service does not respond properly
#### Backend
    ● We want to be able to manage (add/remove/update) services via an API call.
    ● Protect the poller against cases where a service takes a very long time to respond
#### Frontend
    ● Show the latest status of each service
    ● Allow users to manage the list of services
## Solution

I have developed a simple application "**digital-service-provider**" in Java, Angular and Spring Boot.
It contains 2 modules:
##### 1. digital-service-api (Backend)
- It provides capability to
    * Implement a HTTP poller to check the status of a service at regular intervals
    * Persist the information to an database.
    * Log a warning to console when the service does not respond properly.
    * be able to manage (add/remove/update) services via an API call.
    * Protect the poller against cases where a service takes a very long time to respond using timeout
##### 2. digital-service-web (Frontend)
- It provides UI Frontend to
     * Show the latest status of each service
     * Allow users to manage the list of services

###### 1. DummyServiceController
I have created a RESTful web service **DummyServiceController** for **vix.digital** which is polled by a scheduler and
stores its information in database. Ideally this service should be available in a different application and being called
by Digital-service-provider. DummyService can be easily replace when actual **vix.digital** is available. This can be 
done by changing the value of **_service.api.url_** in application.properties file.

Currently it runs on the same port as "**digital-service-provider**" application.

GET http://localhost:8282/api/vix/digital/v1/dummy/services

###### 3. ScheduledTasks
ScheduleTasks leverages scheduler functionality to poll a service at regular intervals. Currently it is configured to 
20 seconds. This is configured and can be changed by changing the value of **_fixedRate.in.milliseconds_** in 
application.properties file.

###### 4. Timeout 
Webservice call to **DummyServiceController** is managed by **_service.api.connection.timeout_** in 
application.properties file and protects the poller against cases where a service takes a very long time to respond. 
Currently it is set to 5s

###### 5. DigitalServiceController
RESTful web service to manage (add/remove/update) services via an API call.

###### 6. ServiceRepository
provides CRUD functionality for the entity class that is being managed and connects to database

###### 7. H2 Database 
I have used H2 relational Database in order to manage the database seamlessly inside spring application context. 

#### Prerequisites
- Java 8
- Maven
- IntelliJ
- Angular
 
#### Building and Running

To build the solution run the following commands from the root folder:

        mvn clean install

#### How to run Backend API using Maven

Please run the following command in a terminal window from the **_digital-service-api_** folder:

        mvn spring-boot:run

## Documentation
Once application running, Swagger API Documentation will be available for this application at

http://localhost:8282/swagger-ui.html

#### How to access API
You can use either SoapUI or Postman or Swagger Console to test the following APIs. 

* **GET**   http://localhost:8282/api/vix/digital/v1/services 
    - Returns all available Services
* **GET**   http://localhost:8282/api/vix/digital/v1/services/{id}  
    - Returns service for a specific ID
* **POST**  http://localhost:8282/api/vix/digital/v1/services  
    - Add a new service
* **PUT**   http://localhost:8282/api/vix/digital/v1/services 
    - Update an existing service for a specific ID             
* **DELETE** http://localhost:8282/api/vix/digital/v1/services/{id}
    - Delete an existing service for a specific ID

#### How to run Frontend UI using ng
please run the following command in another terminal window from the **_digital-service-web_** folder:

        ng serve --open

#### How to access Frontend
The frontend application will be running on http://localhost:4200  

If you do not want to use _ng serve --open_, It can be accessed through _http://localhost:8282_ as well

#### How to run using java
If you do not have maven installed, please run the following command in a terminal window from the root folder:

        java -jar digital-service-api\target\digital-service-api-0.0.1-SNAPSHOT.jar
        
## Advance stage of Development
Due to time constraint, only MVP items are scoped as part of this development. There are few additional tasks which can 
be considered for advance development.
1. Secure Rest APIs
2. Additional validation for ENUM Fields
3. Additional custom exception handling
5. Stop polling the service after number of failures
4. Error messages in UI Frontend
5. Add Dropdown elements in stead of input text for ENUM fields in frontend
6. Add Sorting functionality in Manage screen in frontend
7. Add Unit testing for Frontend
8. Add BDD for Frontend
9. Create docker image to deploy on a docker container
