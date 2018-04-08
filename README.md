# zipstream-folders

## Development

### Run the application
./gradlew bootRun

### Build runnable jar (in build//libs/zipstream-folders-*.jar)
./gradlew clean build

### Run runnable jar
java -jar build/libs/zipstream-folders-*.jar

## API

### Endpoints

http://localhost:8080/info

http://localhost:8080/list

http://localhost:8080/zip

#### Actuator
e.g.
http://localhost:8080/actuator

http://localhost:8080/actuator/health

for a complete list see: 
https://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html
