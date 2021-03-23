# Boat-App Sample Application 

## Context
In this Spring boot application , I try to develop following architecture Hexagonal 

## Domain

`domain` is implemented as a standalone Java module which has no dependencies to any framework (neither spring).

Actually it has only one dependency so we can use `Lombok` as library to make data manipulation easier.
 
## Infrastructure

Here we will define the interactions over the domain so it implements these *ports* to implements the way to :
- define where the domain gets its resources (persistence)

To do so, there are multiple sub-modules (like repository) under infrastructure.

### API 

It describes all the ports for everything that needs to query the domain. 

These interfaces are implemented by the domain.

#### Rest client

This module aims to expose some *rest* entry points to interact with  boats.

## Database configuration

In its default configuration, Boat-app uses an in-memory database (H2).

### This example application has been built with:
  JDK11
  
  [Spring Boot 2](https://spring.io/guides/gs/spring-boot)

### Steps:

1) On the command line
    ```
    git clone https://github.com/Benhamadi-Saad/Boat-App.git
    ```
2) Inside Eclipse or STS
    ```
    File -> Import -> Maven -> Existing Maven project
    ```

    Then either build on the command line `./mvnw generate-resources` or using the Eclipse launcher (right click on project and `Run As -> Maven install`) to generate the css. Run the application main method by right clicking on it and choosing `Run As -> Java Application`.

3) Inside IntelliJ IDEA
    In the main menu, choose `File -> Open` and select the Boat-App [pom.xml](pom.xml). Click on the `Open` button.

    A run configuration named `BoatApplication` should have been created for you if you're using a recent Ultimate version. Otherwise, run the application by right clicking on the `BoatApplication` main class and choosing `Run 'BoatApplication'`.

4) Navigate to Boat-App

    Visit [http://localhost:8089](http://localhost:8089) in your browser.
    
### Open Api:

If you would like to access to Open Api,follow next step: 

1) You should active profile "swagger" in Spring-boot Runner

2) Visit [http://localhost:8089/swagger-ui.html](http://localhost:8089/swagger-ui.html) in your browser.

3) To access OpenApi-doc you must sign-in as a user with admin role only like : `username:admin / password:admin`
