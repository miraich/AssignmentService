FROM eclipse-temurin:23-jdk AS build
RUN apt-get update && apt-get install -y maven
COPY . /build
WORKDIR /build
RUN mvn clean package -Dspring.profiles.active=test

FROM eclipse-temurin:23-jdk
WORKDIR /app
COPY --from=build /build/target/AssignmentService-0.0.1-SNAPSHOT.jar ./target/AssignmentService-0.0.1-SNAPSHOT.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "target/AssignmentService-0.0.1-SNAPSHOT.jar"]