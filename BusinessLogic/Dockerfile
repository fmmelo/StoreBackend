FROM maven:3.8.6-amazoncorretto-19 AS build

# Set the working directory inside the container
WORKDIR /usr/src/app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

# Copy the built jar file into the container
FROM amazoncorretto:19
COPY --from=build /usr/src/app/target/storeapp-SNAPSHOT.jar app.jar

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]