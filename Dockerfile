# Use the official maven/Java 8 image to create a build artifact.
FROM maven:3.6-jdk-8 as builder

# Set the working directory.
WORKDIR /usr/src/app

# Copy the pom.xml file to download dependencies.
COPY pom.xml ./

# Download the dependencies.
RUN mvn dependency:go-offline -B

# Copy the source code.
COPY src ./src

# Build the application.
RUN mvn package

# Use OpenJDK to run the built jar.
FROM openjdk:8-jre-slim

# Copy the jar from the builder stage.
COPY --from=builder /usr/src/app/target/*.jar /app.jar

# Command to run the application.
CMD ["java", "-jar", "/app.jar"]
