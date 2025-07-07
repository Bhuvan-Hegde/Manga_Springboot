# Use OpenJDK 17 base image
FROM eclipse-temurin:17-jdk

# Set working directory
WORKDIR /app

# Copy all files to the container
COPY . .

# Build the app using Maven Wrapper
RUN ./mvnw clean package -DskipTests

# Expose the default port
EXPOSE 8080

# Run the JAR file
CMD ["java", "-jar", "target/Manga_Springboot-0.0.1-SNAPSHOT.jar"]
