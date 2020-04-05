# planeticket


### 1.PROJECT_DESCRIPTION
### 2.GENERAL_NOTES
### 3.LOCAL_DEVELOPMENT_SETUP

##### 1.PROJECT_DESCRIPTION

planeticket is a  plane/ticket backend application.
Used technologies: maven, Spring Boot, Hibernate, junit, mariadb (for local development)


##### 2.GENERAL_NOTES

package structure:  
    **com.eg.planeticket.controller:** rest endpoint classes are stored here  
    **com.eg.planeticket.dto:** DTO classes are here, controller classes receive DTO return DTO, for seperation of concerns  
    **com.eg.planeticket.entity:** @Entity annotated classes are stored here, ORM mapped classes  
    **com.eg.planeticket.repo:** JPA/Hibernate query methods are stored here, each @Entity class has its own Repo such as Company and CompanyRepo  
    **com.eg.planeticket.service:** service classes are stored here, actual operations are done inside these classes  

**src/main/resources/application.properties**
    project configuration file, important features:
        spring.jpa.hibernate.ddl-auto:
            create: drop existing tables create new tables
            none: don't change existing db tables

        spring.datasource.url:
            set db connection url

        spring.jpa.properties.hibernate.dialect:
            set db dialect

**doc/ :** sample postman requests 

##### 3.LOCAL_DEVELOPMENT_SETUP

mysql or mariadb required
    create a schema called "planeticket"
    src/main/resources/application.properties set "spring.jpa.hibernate.ddl-auto=" to "create"
    start application from IDE:
        open Application.java, click play icon
        empty db tables will be created
        in console you should see:
            "2019-09-24 21:58:05.233  INFO 7235 --- [ main] com.eg.planeticket.Application : Started Application in 3.264 seconds (JVM running for 3.568)"
        program ready to use

    sample postman requests are stored in doc/ folder, import from postman and use
    
    request order: beacuse of db constraint integrity, data creation requests should be sent in order,
      request names are from doc/planeticket.postman_collection.json

    1- create_city
    2- create_airport
    3- create_company
    4- create_route
    5- create_companyflight
    6- buy_ticket
    7- delete_ticket



