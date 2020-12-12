**players-quickstart-template**

This project is an application skeleton to perform CRUD operation in an in-memory h2 database.
You can use it to quickly setup a Player information and perform CRUD operation in minutes, it also includes timer 
which watches a player who is ideal and doesn't update there playing status resulting in removing them from DB.

**Getting Started**

**Prerequisites**
.Git
.JDK 8 or later
.Maven 3.0 or later !!!

**Configuration**
The configuration for H2 database and SQL statements has been added and located in
src/resources/application.properties

**Build and Run an executable JAR**
Run mvn clean install to build the project.
You can run the application from the command line using: mvn spring-boot:run
Or you can build a single executable JAR file that contains all the necessary dependencies, classes, and resources with:
mvn clean package
Then you can run the JAR file with:
java -jar target/*.jar

**Test**
.Database can be viewed and tested using(I've also included a snapshot for reference purpose):
URL - http://localhost:8080/h2/
JDBC URL - jdbc:h2:file:C:/temp/test
.Swagger has been implemented to test the application
 http://localhost:8080/swagger-ui.html#!/
.JUNIT has also been written for the application to test its behaviour.

**Test your new Player application**
.Open http://localhost:8080/swagger-ui.html#/player-controller to test CRUD operation for Players.
.Execute (GET /employees/playing) or  fetchPlayingStatusAfter1minute method to test the active player.
**please note the method has been implemented in such a way that the counter/timer will wait for 60 seconds to check if
  the player has updated there playing status ? if yes then it'll reset the counter to 60 seconds else it'll remove them
  from the database.
  
**Improvements**
.UI can be added to perform CRUD operation and updating player status.
.More test case can be included for testing.
.A proper deployment channel can be added to this.