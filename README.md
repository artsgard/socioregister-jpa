# 3) socioregister-jpa
A Springboot REST application to register and add Socios (third step with jpa/postgres socioregister-CRUDS )

1) General Info About the Socio-Micro-Service-Demo

2) Specific Info Concerning Each Single Application



## General Info ====

The Socio Micro Services Project will consist of about 10 small (backend) Springboot applications, deployed in a Docker Container/ Linux Oracle Virtual Box. SocioRegister is the principal part of a series of four applications called: starter, mock, jpa, socioregister. Together they show a stepwise buildup to a Springboot REST application, which contains use-cases for registering and adding Socios (similar to Facebook). This line of applications goes from an almost empty Springboot shell (starter: one controller method only) to a small but full-fledged REST application: SocioRegister which will be used as a component of our micro-services.

Next you`ll find four other serving applications. The simple SocioWeather, provides a weather-report by city by consulting an external REST-service called Open Weather. SocioBank, permits money transaction between Socios alse consulting an external service for exchange rates. The SocioSecurity, a Cookie/ Token based SpringSecurity (OAUTH2), still has to be written. Finally the SocioDbBatch application is interesting because it will update, on a daily bases, the databases of SocioRegister (socio_db) and SocioBank (soicio_bank_db). The DBs run on MySQL or Postgres.

From SocioRegister-jpa one finds backend-Validation (javax) and REST-Exception Handeling of Spring (RestControllerAdvice).

Testing, in general, will have an important focus and since we are dealing with Spring(boot) there will systematically testing based on five mayor strategies:

	-@ExtendWith(MockitoExtension.class)

	-@ExtendWith(SpringExtension.class) standalone setup (two ways)

	-@ExtendWith(SpringExtension.class) server tests (@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)

	-@DataJpaTest wich is database testing on H2

	-Spring Batch testing

Testing is still "work in progress"



## Specific Info SocioRegister-jpa ====

The jpa-version gets closer to the final application SocioRegister, to be used in the Docker-Container. The basic architecture of Controllers/ Services/ Repositories is already present at the previous SocioRegsiter-mock. Now, at SocioRegsiter-jpa, three new important layers are added: 1) Spring-Data (spring-boot-starter-data-jpa) connecting to a postgres DB (see pom postgresql); 2) Validation Messages javax.validation 3) Spring REST Exception Handeling.

### Spring-Data

Revising the pom note the dependencies added, and take a good look at the SocioModel Entity (there is still only one Pojo present). There are some fields added, password and more interestingly one finds a list of associatedSocios, where the added associated-socios will be stored (a many-to-many field at socio-table). Note also the annotations for Hibernate/JPA (javax.persistence) and the DB-field validation anottations. These subjects you should study and read about Spring-Data and JPA.

### Backend Validation

The javax.validation lib is part of the Springboot web-starter dependency. Springboot auto-config will do the rest. What you have to do is the following: 

1) add validation-annoations at the Pojo-classes (SocioDTO, field email, add @NotEmpty, @NotNull, @Email); 

2) in case you name the external error message file error-messages.properties (which is not the standard name of Spring auto-config messages), insert the next line at the resources/application.properties spring.messages.basename=error-messages

3) create a error-messages.properties file at the resources directory and add e.g. the line: NotNull.socioDTO.email=email is Mandetory!

	-NotNull is the annotation name;

	-socioDTO is the class name;

	-email is the field with the annotation NotNull of the above class socioDTO;

	-next write the text to appear at the error json object: email is Mandetory!

4) add the @Valid annotation at the beginning of the controller arguments, indicating that its RequestBody demands validation;

5) In order for the error-message to appear at the json object that will be sent to the front-end-page, one needs to touch the RestExceptionHandler extends ResponseEntityExceptionHandler class. First inject (@Autowired) MessageSource messageSource; and next add each error to a list which forms part of the ErrorDetail class (com.artsgard.socioregister.exception):


		validationError.setMessage(messageSource.getMessage(fe, null));
        validationErrorList.add(validationError);  
		errorDetail.getErrors().put(fe.getField(), validationErrorList);


### Spring Exception Handeling

See the exception directory (com.artsgard.socioregister.exception) concerning the following comments. 

There are many ways to implement exception handeling in Java. I picked the global exception handeling based on the @ControllerAdvice annotation and the class extention of RestResponseEntityExceptionHandler (see pls the RestExceptionHandler at the exception dir).

At socioregister-jpa I am interested in only two type of exceptions: ValidationError and ResourceNotFoundException. For listing those errors I use a helper class called ErrorDetail.

If you like to add more declared error classes (which extends RuntimeException), don't forget to add the @ExceptionHandler annotation at each single method concerning you error class:

	@ExceptionHandler(ResourceNotFoundException.class)
	
With the validation massages in place, and the throw ResourceNotFoundException clause present, the cross-cutting @RestControllerAdvice will help to communicate the error, in the form of a json object, to the front-end!

### Use-cases

The use-cases are very similar to the previous socioregister-mock:

	-http://localhost:8081/socio

	-http://localhost:8081/socio/2

	-http://localhost:8081/socio/username/js

And by using Postman:

	-post http://localhost:8081/socio  
		{
			"username": "pb",
			"password": "secret",
			"firstName": "Pierre",
			"lastName": "Boulez",
			"email": "boulez@gmail.com",
			"active": true 
		} 
  
	-put http://localhost:8081/socio/4
		{
			"id": 4,
			"username": "pbxxx",
			"password": "secret",
			"firstName": "Pierre",
			"lastName": "Boulez",
			"email": "boulez@gmail.com",
			"active": true 
		} 
		
    -add a associated socio: post http://localhost:8081/socio/3/4    (means: add socio (associated)
	with id 4 to socio with id 3)
	
	-delete http://localhost:8081/socio/4

	

At the next readme of the final socioregister application I will go more into Testing and DB initialization.


