*************************************************************************************************
<h1 align="center">  Loan Management System </h1>
<h3 align="center"> This RESTful back-end application  allow users to do operations with a logic based of managing loans.
Log in is a must to use every enpoint. Certain endpoints are restricted to managers and some other ones to normal users.</h3>

*************************************************************************************************
<h4 align="center"> Tools used:</h4>
   <p align="center">
   <img src="https://img.shields.io/badge/MAVEN-23-green">
   <img src="https://img.shields.io/badge/JAVALIN-6.4.0-green">
   <img src="https://img.shields.io/badge/POSTGRE SQL-42.7.5-green">
   <img src="https://img.shields.io/badge/MOCKITO-4.0.0-green">
   <img src="https://img.shields.io/badge/JUNIT-4.13.2-green">
<h2 ALIGN="CENTER">STARTING 🚀</h2>

***********************************************************************************************

<h3>PRE-REQUISITES </h3>


------------------

IMPORT THE DEPENDENCIES :

    Javalin
    slf4j-simple
    PostgreSQL
    jackson-databind
    bcrypt
    jackson-datatype-jsr310

***********************************************************************************************

<h3>EXCECUTING THE TESTS </h3>


------------------

-RUNNING THE TESTS


MAKE SURE TO EXCECUTE THIS COMMAND <h5>NOTE:THIS WILL RUN ALL YOUR TEST</h2>

    mvn test

IF YOU WANT TO RUN AN SPECIFIC TEST YOU COULD USE THIS COMMAND

    mvn -Dtest=UserServiceTest#testGetUserById test

SINTAXIS

    mvn-> ITS A NEED 
    -Dtest=UserServiceTest#testGetUserById->WE SAY HERE THAT WE ONLY WANT TO EXCETUE THIS TEST
    test->USED TO SAY TO ONLY EXCECUTE THE PHASE OF TEST


***********************************************************************************************

<h3>DATABASE CONFIGURATION </h3>


------------------

FOR SETTING UP THE DATABASE YOU NEED TO ENTER TO THIS DIRECTORY:

    --PROJECT-0
    ------------SRC
    ---------------MAIN
    -------------------JAVA
    -----------------------COM.DEPI
    -------------------------------UTIL
    -----------------------------------CONNECTION UTIL
THERE YOU WILL HAVE CON CONFIGURE THE VARIABLES CALLED WITH YOUR OWN DATA :

 -   URL
 
 -   USERNAME
-   PASSWORD 
