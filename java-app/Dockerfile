# --- Stage 1: The Builder (Full JDK + Maven) ---
    FROM maven:3.8.5-openjdk-17-slim AS builder
    WORKDIR /app
    
    # Copy the pom and the whole src directory
    COPY pom.xml .
    COPY src ./src
    
    # Run the Maven package command to create the JAR file
    # This will create a file in /app/target/java-service-1.0.jar
    RUN mvn clean package -DskipTests
    
    # --- Stage 2: The Runtime (JRE Distroless) ---
    FROM gcr.io/distroless/java17-debian12:nonroot
    WORKDIR /app
    
    # Copy ONLY the finished JAR file from the builder stage
    COPY --from=builder /app/target/java-service-1.0.jar app.jar
    
    EXPOSE 8000
    # Run the JAR using the java -jar command
    ENTRYPOINT ["java", "-jar", "app.jar"]