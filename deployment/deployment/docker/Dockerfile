# Use official OpenJDK 17 base image
FROM openjdk:17-jdk-slim

LABEL authors="Nagsasank_Reddy_K"

# Set the working directory inside the container
WORKDIR /app

# Copy the built jar file into the container
COPY target/water-management-service-1.0.0.jar app.jar

# Expose the port your Spring Boot app runs on
EXPOSE 8090:8080

# Run the jar file
ENTRYPOINT ["java", "-jar", "app.jar"]
