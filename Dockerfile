FROM openjdk:8
EXPOSE 8080
ADD target/weather-data-docker.jar weather-data-docker.jar
ENTRYPOINT ["java" ,"-jar","/weather-data-docker.jar"]