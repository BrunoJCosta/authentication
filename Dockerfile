FROM amazoncorretto:21

COPY target/login-0.0.1-SNAPSHOT.jar /app/login.jar

EXPOSE 8081

CMD ["java", "-jar", "/app/login.jar"]