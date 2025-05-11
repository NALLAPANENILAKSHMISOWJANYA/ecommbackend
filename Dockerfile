FROM openjdk:17-jdk-alpine

# Set working directory
WORKDIR /app

# Copy Maven wrapper and project files
COPY mvnw mvnw
COPY mvnw.cmd mvnw.cmd
COPY .mvn .mvn
COPY pom.xml pom.xml
COPY src src

# Give executable permission and build without tests
RUN chmod +x mvnw && \
    ./mvnw clean package -DskipTests && \
    cp target/*.jar app.jar

# Expose port
EXPOSE 8080


# Run the app
ENTRYPOINT ["java", "-jar", "app.jar"]
