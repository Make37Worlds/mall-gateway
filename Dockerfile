# Use the official OpenJDK base image
FROM openjdk:17-ea-jdk-slim

# Set the working directory in the container
WORKDIR /app

# Copy the jar file built by Maven into the container
COPY target/mall-gateway-0.0.1-SNAPSHOT.jar app.jar

# Expose the port the app runs on
EXPOSE 3737

# Command to run the application
CMD ["java", "-jar", "app.jar"]
