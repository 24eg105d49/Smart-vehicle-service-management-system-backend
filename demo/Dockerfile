# ==============================================================================
# Build Stage
# ==============================================================================
FROM maven:3.9.6-eclipse-temurin-17 AS builder
WORKDIR /app

# In official maven:* container images, MAVEN_CONFIG is set to "/root/.m2".
# When ./mvnw executes, it expands "$MAVEN_CONFIG" as a command-line argument,
# which causes Maven to treat "/root/.m2" as a lifecycle phase and fail with:
# "Unknown lifecycle phase /root/.m2".
# Unsetting MAVEN_CONFIG or using the pre-installed 'mvn' binary fixes this completely.
ENV MAVEN_CONFIG=""

# Cache dependencies
COPY pom.xml .
RUN mvn dependency:go-offline -B || true

# Copy source code and build executable jar
COPY src ./src
RUN mvn clean package -DskipTests

# ==============================================================================
# Runtime Stage
# ==============================================================================
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Run as non-root user for security
RUN addgroup -S appgroup && adduser -S appuser -G appgroup
USER appuser

# Copy jar from build stage
COPY --from=builder /app/target/*.jar app.jar

# Render exposes PORT dynamically
ENV PORT=8080
ENV SPRING_PROFILES_ACTIVE=prod
EXPOSE 8080

ENTRYPOINT ["sh", "-c", "java -Djava.security.egd=file:/dev/./urandom -jar app.jar"]
