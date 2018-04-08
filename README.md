# zipstream-folders
An example Spring Boot Application providing an end-point, which streams arbitrary content repository trees as a ZIP file.

## Development
### Run the application
```bash
./gradlew bootRun
```
### Build runnable jar (in build//libs/zipstream-folders-*.jar)
```bash
./gradlew clean build
```
### Run runnable jar
```bash
java -jar build/libs/zipstream-folders-*.jar
```
## API

### End-Points
[Info](http://localhost:8080/info)  
[List](http://localhost:8080/list)  
[Zip](http://localhost:8080/zip)  

#### Actuator
e.g.:  
[Actuator](http://localhost:8080/actuator)  
[Health](http://localhost:8080/actuator/health)

for a complete list see:   
[Spring IO/Production Ready Endpoints](https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html)
