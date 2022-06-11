FROM maven:3.6.3-java-17-openjdk as build
WORKDIR /home/app
COPY src src
COPY pom.xml .
RUN mvn clean package

FROM amazoncorretto:17
COPY --from=build /home/app/target/*.jar /usr/app/xmanager.jar
EXPOSE 8080
CMD java -jar /usr/app/xmanager.jar
