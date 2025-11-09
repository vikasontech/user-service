FROM eclipse-temurin:17-jdk-jammy

# Set working directory
WORKDIR /app

# Copy the built jar file from target directory
COPY target/*.jar app.jar

# Expose application port
EXPOSE 8080

# Set active Spring profile to 'k8s'
ENV SPRING_PROFILES_ACTIVE=k8s

# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "/app/app.jar"]