FROM openjdk:8
EXPOSE 8080
ADD target/e-cinema.jar e-cinema.jar
ENTRYPOINT ["java", "-jar", "/e-cinema.jar"]