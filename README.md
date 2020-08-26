# socioregister-jpa
A Springboot REST application to register and add Socios (third step with jpa CRUDS )

1) General Info About the Socio-Micro-Service-Demo

2) Specific Info Concerning Each Single Application



General Info =====================================

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



Specific Info SocioRegister-jpa =====================================

The jpa-version get closer to the final application SocioRegister, to be used in the Docker-Container. The basic architecture of Controllers/ Services/ Repositories is already present at the previous SocioRegsiter-mock. Now, at SocioRegsiter-jpa, three new important layers are added: 1) Spring-Data (spring-boot-starter-data-jpa) connecting to a postgres DB (see pom postgresql); 2) Validation Messages javax.validation 3) Spring REST Exception Handeling.

Spring-Data

Revising the pom note the dependencies added, and take a look at the SocioModel Entity (there is still only one Pojo here). There are some fields added, password and more interestingly the list associatedSocios, where the added associated-socios will be stored (a many-to-many recursive field at socioModel). Note also the annotations for Hibernate/JPA (javax.persistence) and the DB-field validation. These subjects you should study at Spring-Data JPA.

Backend Validation

The javax.validation lib is part of the Springboot web-starter dependency. Springboot autoconfig will dothe rest, meaning: 1) add annoations at the Pojo-classes 2) insert this line at the resources/application.properties spring.messages.basename=messages 3) create a messages.properties at resources directory and add the validation lines e.g. like this: NotNull.socioDTO.email=email is Mandetory!

-NotNull is the annotation name;

-socioDTO is the class name

-email is the field with the annotation NotNull of the above class socioDTO

Spring Exception Handeling

See the exception directory concerning the following comments.


